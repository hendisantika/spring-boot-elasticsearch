package id.my.hendisantika.springbootelasticsearch.service;

import id.my.hendisantika.springbootelasticsearch.model.es.Author;
import id.my.hendisantika.springbootelasticsearch.repository.es.AuthorEsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/11/24
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class AuthorEsService {

    private final AuthorEsRepository authorEsRepository;

    public List<Author> getAllAuthors() {
        return IterableUtils.toList(authorEsRepository.findAll());
    }
}
