package id.my.hendisantika.springbootelasticsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.springbootelasticsearch.model.dto.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/12/24
 * Time: 05.56
 * To change this template use File | Settings | File Templates.
 */
public class ProductTests {
    private final String json = "{" +
            "\"name\":\"Best name ever\"," +
            "\"description\":\"This is a wonderful description\"," +
            "\"price\":123.32," +
            "\"stock_available\":123" +
            "}";

    @Test
    public void testObjectMapperToProduct() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
//        mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Product product = mapper.readValue(json, Product.class);
        assertThat(product.getId()).isNull();
        assertThat(product.getName()).isEqualTo("Best name ever");
        assertThat(product.getDescription()).isEqualTo("This is a wonderful description");
        assertThat(product.getPrice()).isEqualTo(123.32);
        assertThat(product.getStockAvailable()).isEqualTo(123);

        // now vice versa. serialize out again
        final String serializedJson = mapper.writeValueAsString(product);
        assertThat(serializedJson).isEqualTo(json);
    }
}
