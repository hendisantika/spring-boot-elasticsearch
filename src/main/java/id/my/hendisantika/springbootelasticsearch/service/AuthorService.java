package id.my.hendisantika.springbootelasticsearch.service;

import id.my.hendisantika.springbootelasticsearch.repository.jpa.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/12/24
 * Time: 07:35
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
}
