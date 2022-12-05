package fudan.pm.fudanCompass.config;

import fudan.pm.fudanCompass.dto.ArticleDetailsDto;
import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.request.ArticleRequest;
import fudan.pm.fudanCompass.entity.Article;
import fudan.pm.fudanCompass.entity.Comment;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class MapperFactoryConfig {

    @Bean
    //统一配置映射关系
    public MapperFacade mapperFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter("stringListConverter", new StringListConverter());
        mapperFactory.getConverterFactory().registerConverter("commentsTrimConverter", new CommentListConverter());

        mapperFactory.classMap(Article.class, ArticleOutputDto.class)
                .fieldMap("tags", "tags").converter("stringListConverter").add()
                .fieldMap("comments", "comments").converter("commentsTrimConverter").add()
                .byDefault()
                .register();

        mapperFactory.classMap(Article.class, ArticleDetailsDto.class)
                .fieldMap("tags", "tags").converter("stringListConverter").add()
                .byDefault()
                .register();

        mapperFactory.classMap(ArticleRequest.class, Article.class)
                .fieldMap("tags", "tags").converter("stringListConverter").add()
                .byDefault()
                .register();

        return mapperFactory.getMapperFacade();
    }

    private static class StringListConverter extends BidirectionalConverter<List<String>, String> {

        @Override
        public String convertTo(List<String> strings, Type<String> type, MappingContext mappingContext) {
            return strings.stream().filter(Objects::nonNull).collect(Collectors.joining(","));
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

}
