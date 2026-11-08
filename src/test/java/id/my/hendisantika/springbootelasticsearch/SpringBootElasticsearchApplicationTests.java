package id.my.hendisantika.springbootelasticsearch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class SpringBootElasticsearchApplicationTests {

    @Test
    void contextLoads() {
    }

}
