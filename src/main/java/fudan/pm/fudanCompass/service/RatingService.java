package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.RatingDetailsDto;
import fudan.pm.fudanCompass.dto.RatingOutputDto;
import fudan.pm.fudanCompass.dto.request.RatingDetailsRequest;
import fudan.pm.fudanCompass.dto.request.RatingRequest;
import fudan.pm.fudanCompass.dto.request.SearchRatingsRequest;
import fudan.pm.fudanCompass.entity.LikeInfo;
import fudan.pm.fudanCompass.entity.Rating;
import fudan.pm.fudanCompass.repository.LikeInfoRepository;
import fudan.pm.fudanCompass.repository.RatingRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    LikeInfoRepository likeInfoRepository;

    public Page<RatingOutputDto> search(SearchRatingsRequest request){
        Specification<Rating> specification = (root, query, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (!ObjectUtils.isEmpty(request.getSearchContent())){
                Predicate[] arr = new Predicate[]{
                        cb.like(root.get("title"), "%"+request.getSearchContent()+"%"),
                        cb.like(root.get("content"), "%"+request.getSearchContent()+"%")
                };
                predicates.add(cb.or(arr));
            }
            if (!ObjectUtils.isEmpty(request.getCourseName())){
                Predicate[] arr = new Predicate[]{
                        cb.like(root.get("courseName"), "%"+request.getCourseName()+"%")
                };
                predicates.add(cb.or(arr));
            }
            if (!ObjectUtils.isEmpty(request.getCourseType())){
                Predicate[] arr = new Predicate[]{
                        cb.like(root.get("courseType"), "%"+request.getCourseType()+"%")
                };
                predicates.add(cb.or(arr));
            }
            if (!ObjectUtils.isEmpty(request.getRelatedMajor())){
                Predicate[] arr = new Predicate[]{
                        cb.like(root.get("relatedMajor"), "%"+request.getRelatedMajor()+"%")
                };
                predicates.add(cb.or(arr));
            }

            Predicate[] arr = new Predicate[predicates.size()];
            query.where(cb.and(predicates.toArray(arr)));
            if (!ObjectUtils.isEmpty(request.getOrderBy()) && request.getOrderBy() == 1){
                query.orderBy(cb.desc(root.get("likeNum")), cb.desc(root.get("updateTime")));
            }else {
                query.orderBy(cb.desc(root.get("updateTime")));
            }
            return query.getRestriction();
        };

        return ratingRepository.findAll(specification, PageRequest.of(request.getPageNo(), request.getPageSize()))
                .map(a -> mapperFacade.map(a, RatingOutputDto.class));
    }


    public RatingDetailsDto getDetails(RatingDetailsRequest request){
        return ratingRepository.findById(request.getRatingId()).map(a ->
        {
            RatingDetailsDto detailsDto = mapperFacade.map(a, RatingDetailsDto.class);
            if (ObjectUtils.isEmpty(request.getUserId())){
                return detailsDto;
            }
            LikeInfo likeInfo = likeInfoRepository.findFirstByLikeIdAndLikeTypeAndUserId(
                    request.getRatingId(), LikeInfo.RATING_TYPE, request.getUserId());
            if (!ObjectUtils.isEmpty(likeInfo)){
                switch (likeInfo.getLikeOrFavor()){
                    case LikeInfo.LIKE:
                        detailsDto.setIsLikedByUser(true);
                        break;
                    case LikeInfo.FAVOR:
                        detailsDto.setIsFavouredByUser(true);
                        break;
                    case LikeInfo.LIKE_AND_FAVOR:
                        detailsDto.setIsLikedByUser(true);
                        detailsDto.setIsFavouredByUser(true);
                }
            }
            return detailsDto;
        }).orElse(null);
    }

    public void post(RatingRequest request) {
        Rating rating = mapperFacade.map(request, Rating.class);
        ratingRepository.save(rating);
    }

    public void update(Long id, RatingRequest request){
        ratingRepository.findById(id).ifPresent(a -> {
            a.update(request);
            ratingRepository.save(a);
        });
    }

}
