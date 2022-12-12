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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
public class RatingService {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    LikeInfoRepository likeInfoRepository;

    public Page<RatingOutputDto> search(SearchRatingsRequest request) {
        Specification<Rating> specification = (root, query, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (!ObjectUtils.isEmpty(request.getSearchContent())) {
                Predicate[] arr = new Predicate[]{
                        cb.like(root.get("title"), "%" + request.getSearchContent() + "%"),
                        cb.like(root.get("content"), "%" + request.getSearchContent() + "%")
                };
                predicates.add(cb.or(arr));
            }
            if (!ObjectUtils.isEmpty(request.getCourseName())) {
                predicates.add(cb.like(root.get("courseName"), "%" + request.getCourseName() + "%"));
            }
            if (!ObjectUtils.isEmpty(request.getCourseType())) {
                predicates.add(cb.like(root.get("courseType"), "%" + request.getCourseType() + "%"));
            }
            if (!ObjectUtils.isEmpty(request.getRelatedMajor())) {
                predicates.add(cb.like(root.get("relatedMajor"), "%" + request.getRelatedMajor() + "%"));
            }

            Predicate[] arr = new Predicate[predicates.size()];
            query.where(cb.and(predicates.toArray(arr)));
            if (!ObjectUtils.isEmpty(request.getOrderBy()) && request.getOrderBy() == 1) {
                query.orderBy(cb.desc(root.get("likeNum")), cb.desc(root.get("updateTime")));
            } else {
                query.orderBy(cb.desc(root.get("updateTime")));
            }
            return query.getRestriction();
        };

        return ratingRepository.findAll(specification, PageRequest.of(request.getPageNo(), request.getPageSize()))
                .map(a -> mapperFacade.map(a, RatingOutputDto.class));
    }


    public RatingDetailsDto getDetails(RatingDetailsRequest request) {
        return ratingRepository.findById(request.getRatingId()).map(a ->
        {
            RatingDetailsDto detailsDto = mapperFacade.map(a, RatingDetailsDto.class);
            if (ObjectUtils.isEmpty(request.getUserId())) {
                return detailsDto;
            }
            LikeInfo likeInfo = likeInfoRepository.findFirstByLikeIdAndLikeTypeAndUserId(
                    request.getRatingId(), LikeInfo.RATING_TYPE, request.getUserId());
            if (!ObjectUtils.isEmpty(likeInfo)) {
                switch (likeInfo.getLikeOrFavor()) {
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

    public void update(Long id, RatingRequest request) {
        ratingRepository.findById(id).ifPresent(a -> {
            a.update(request);
            ratingRepository.save(a);
        });
    }

    public HashMap getPopCourseTypes(){
        HashMap hashMap = new HashMap();
        List<Rating> ratings = ratingRepository.findAll();
        if (ratings == null){
            hashMap.put("message","暂时无推荐课程");
            return hashMap;
        }
        HashMap hashMap1 = getRatingCourse(ratings);
        hashMap1.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(hashMap1.entrySet());
        Collections.sort(list,(o1, o2) -> (o2.getValue() - o1.getValue()));
        String[] popCourses = new String[2];
        popCourses[0] = list.get(0).getKey();
        popCourses[1] = list.get(1).getKey();
        hashMap.put("popCourseTypes",popCourses);
        hashMap.put("message","success");
        return hashMap;
    }
    public HashMap getRatingCourse(List<Rating> ratings){
        HashMap<String, Integer> hashMap = new HashMap();
        for (Rating rating:ratings){
            String courseType = rating.getCourseName();
            if (hashMap.containsKey(courseType)){
                hashMap.replace(courseType,hashMap.get(courseType)+1);
            }
            else {
                hashMap.put(courseType, 1);
            }
        }
        return hashMap;
    }

    public void delete(Long id) {
        ratingRepository.deleteById(id);
    }

}
