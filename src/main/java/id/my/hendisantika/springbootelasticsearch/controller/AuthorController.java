package id.my.hendisantika.springbootelasticsearch.controller;

import id.my.hendisantika.springbootelasticsearch.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/12/24
 * Time: 07:40
 * To change this template use File | Settings | File Templates.
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;
}
