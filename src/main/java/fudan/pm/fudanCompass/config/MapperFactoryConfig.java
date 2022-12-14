package fudan.pm.fudanCompass.config;

import fudan.pm.fudanCompass.dto.*;
import fudan.pm.fudanCompass.dto.request.ArticleRequest;
import fudan.pm.fudanCompass.dto.request.FavourRequest;
import fudan.pm.fudanCompass.dto.request.LikeRequest;
import fudan.pm.fudanCompass.dto.request.RatingRequest;
import fudan.pm.fudanCompass.entity.*;
import fudan.pm.fudanCompass.repository.UserRepository;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MapperFactoryConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    //统一配置映射关系
    public MapperFacade mapperFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter("stringListConverter", new StringListConverter());
        mapperFactory.getConverterFactory().registerConverter("commentsTrimConverter", new CommentListConverter());
        mapperFactory.getConverterFactory().registerConverter("userIdNameConverter", new UserIdNameConverter());

        mapperFactory.classMap(Article.class, ArticleOutputDto.class)
                .field("userId", "userId")
                .fieldMap("userId", "username").converter("userIdNameConverter").add()
                .fieldMap("tags", "tags").converter("stringListConverter").add()
                .fieldMap("comments", "comments").converter("commentsTrimConverter").add()
                .byDefault()
                .register();

        mapperFactory.classMap(Rating.class, RatingOutputDto.class)
                .field("userId", "userId")
                .fieldMap("userId", "username").converter("userIdNameConverter").add()
                .fieldMap("comments", "comments").converter("commentsTrimConverter").add()
                .byDefault()
                .register();

        mapperFactory.classMap(Article.class, ArticleDetailsDto.class)
                .field("userId", "userId")
                .fieldMap("userId", "username").converter("userIdNameConverter").add()
                .fieldMap("tags", "tags").converter("stringListConverter").add()
                .byDefault()
                .register();

        mapperFactory.classMap(Rating.class, RatingDetailsDto.class)
                .field("userId", "userId")
                .fieldMap("userId", "username").converter("userIdNameConverter").add()
                .byDefault()
                .register();

        mapperFactory.classMap(ArticleRequest.class, Article.class)
                .fieldMap("tags", "tags").converter("stringListConverter").add()
                .byDefault()
                .register();

        mapperFactory.classMap(LikeRequest.class, LikeInfo.class)
                .field("id", "likeId")
                .byDefault()
                .register();

        mapperFactory.classMap(FavourRequest.class, LikeInfo.class)
                .field("id", "likeId")
                .field("favourType", "likeType")
                .byDefault()
                .register();

        mapperFactory.classMap(User.class, UserDto.class)
                .field("id", "userId")
                .byDefault()
                .register();

        return mapperFactory.getMapperFacade();
    }

    private static class StringListConverter extends BidirectionalConverter<List<String>, String> {

        @Override
        public String convertTo(List<String> strings, Type<String> type, MappingContext mappingContext) {
            return String.join(",", strings);
        }

        @Override
        public List<String> convertFrom(String s, Type<List<String>> type, MappingContext mappingContext) {
            return Arrays.stream(s.split(",")).collect(Collectors.toList());
        }
    }

    private static class CommentListConverter extends BidirectionalConverter<List<Comment>, List<Comment>> {

        @Override
        public List<Comment> convertTo(List<Comment> comments, Type<List<Comment>> type, MappingContext mappingContext) {
            return comments.size() > 3 ? comments.subList(0, 3) : comments;
        }

        @Override
        public List<Comment> convertFrom(List<Comment> comments, Type<List<Comment>> type, MappingContext mappingContext) {
            return comments;
        }
    }

    private class UserIdNameConverter extends BidirectionalConverter<Long, String> {
        @Override
        public String convertTo(Long aLong, Type<String> type, MappingContext mappingContext) {
            return userRepository.findById(aLong).map(User::getUsername).orElse(null);
        }

        @Override
        public Long convertFrom(String s, Type<Long> type, MappingContext mappingContext) {
            return null;
        }
    }

}
