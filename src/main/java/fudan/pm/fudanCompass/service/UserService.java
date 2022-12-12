package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.RatingOutputDto;
import fudan.pm.fudanCompass.dto.request.SetUserMajorRequest;
import fudan.pm.fudanCompass.entity.*;
import fudan.pm.fudanCompass.repository.*;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    LikeInfoRepository likeInfoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;

    public List<ArticleOutputDto> getFavourArticles(Long id) throws Exception {
        List<ArticleOutputDto> articleOutputDtos = new LinkedList<>();
        List<LikeInfo> likeInfos = likeInfoRepository.findAll();
        for (LikeInfo likeinfo: likeInfos){
            if ((likeinfo.getUserId() == id)
                    && (likeinfo.getLikeType() == LikeInfo.ARTICLE_TYPE)
                    && ((likeinfo.getLikeOrFavor() == LikeInfo.FAVOR) || (likeinfo.getLikeOrFavor() == LikeInfo.LIKE_AND_FAVOR))) {
                Article a = articleRepository.findById(likeinfo.getLikeId()).orElseThrow(Exception::new);
                ArticleOutputDto ao = mapperFacade.map(a, ArticleOutputDto.class);
                articleOutputDtos.add(ao);
            }
        }
        return articleOutputDtos;
    }

    public List<RatingOutputDto> getFavourRatings(Long id) throws Exception {
        List<RatingOutputDto> ratingOutputDtos = new LinkedList<>();
        List<LikeInfo> likeInfos = likeInfoRepository.findAll();
        for (LikeInfo likeinfo: likeInfos){
            if ((likeinfo.getUserId() == id)
                    && (likeinfo.getLikeType() == LikeInfo.RATING_TYPE)
                    && ((likeinfo.getLikeOrFavor() == LikeInfo.FAVOR) || (likeinfo.getLikeOrFavor() == LikeInfo.LIKE_AND_FAVOR))) {
                Rating r = ratingRepository.findById(likeinfo.getLikeId()).orElseThrow(Exception::new);
                RatingOutputDto ro = mapperFacade.map(r, RatingOutputDto.class);
                ratingOutputDtos.add(ro);
            }
        }
        return ratingOutputDtos;
    }

    public void setUserMajor(SetUserMajorRequest request) throws Exception {
        User user = userRepository.findById(request.getUserId()).orElseThrow(Exception::new);
        user.setMajor(request.getMajor());
        userRepository.save(user);
    }

    public String getUserMajor(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(Exception::new);
        return user.getMajor();
    }

    public HashMap getInfo(Long userId){
        HashMap hashMap = new HashMap();
        int postCount = 0;
        int commentCount = 0;
        int likeCount = 0;
        List<Article> articles = articleRepository.findArticlesByUserId(userId);
        List<Comment> comments = commentRepository.findCommentsByUserId(userId);
        List<Rating> ratings = ratingRepository.findRatingsByUserId(userId);

        int articleCount = articles==null? 0:articles.size();
        int ratingCount = ratings == null? 0: ratings.size();

        postCount = articleCount+ ratingCount;
        hashMap.put("postCount", postCount);

        commentCount = comments.size();
        hashMap.put("commentCount",commentCount);

        if (articles !=null) {
            for (Article article : articles) {
                likeCount += article.getLikeNum();
            }
        }
        if (ratings!=null) {
            for (Rating rating : ratings) {
                likeCount += rating.getLikeNum();
            }
        }
        hashMap.put("likeCount", likeCount);
        return hashMap;


    }

    public HashMap getUserTimeTableByDay(String day, Long userId){
        HashMap hashMap1 = new HashMap();
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Sun",0);
        hashMap.put("Mon",1);
        hashMap.put("Tue",2);
        hashMap.put("Wed",3);
        hashMap.put("Thu",4);
        hashMap.put("Fri",5);
        hashMap.put("Sat",6);
        int index = hashMap.get(day);
        User user = userRepository.findUserById(userId);
        if (user.getCourseTable() == null){
            hashMap1.put("message","您未上传课程表");
            return hashMap1;
        }
        String[] dayCourse = user.getCourseTable().split("&");
        hashMap1.put("timeTable",dayCourse[index].split("#"));
        hashMap1.put("message","success");
        return hashMap1;
    }

    public void setUserTimeTableByDay(String day, Long userId,List<String> courses){
        HashMap hashMap1 = new HashMap();
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Sun",0);
        hashMap.put("Mon",1);
        hashMap.put("Tue",2);
        hashMap.put("Wed",3);
        hashMap.put("Thu",4);
        hashMap.put("Fri",5);
        hashMap.put("Sat",6);
        int index = hashMap.get(day);
        User user = userRepository.findUserById(userId);
        userRepository.delete(user);
        String[] coursesDay = user.getCourseTable().split("&");
        coursesDay[index] = String.join("," ,courses.toArray());
        String course = "";
        for (String course1:coursesDay){
            course += "&"+course1;
        }
        user.setCourseTable(course);
        userRepository.save(user);

    }
}
