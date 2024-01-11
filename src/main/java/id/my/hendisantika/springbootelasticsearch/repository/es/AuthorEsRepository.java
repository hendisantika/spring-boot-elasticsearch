package id.my.hendisantika.springbootelasticsearch.repository.es;

import id.my.hendisantika.springbootelasticsearch.model.es.Author;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/11/24
 * Time: 09:56
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface AuthorEsRepository extends ElasticsearchRepository<Author, Long> {
}
