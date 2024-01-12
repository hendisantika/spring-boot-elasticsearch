package id.my.hendisantika.springbootelasticsearch.controller;

import id.my.hendisantika.springbootelasticsearch.model.dto.PostDTO;
import id.my.hendisantika.springbootelasticsearch.model.entity.Post;
import id.my.hendisantika.springbootelasticsearch.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/12/24
 * Time: 07:43
 * To change this template use File | Settings | File Templates.
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/v1/posts")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String title) {
        List<Post> list = service.getAllPosts(title);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/v1/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        Post entity = service.getById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping("/v1/posts")
    public ResponseEntity<Post> createOrUpdate(@Valid @RequestBody PostDTO postDTO) {
        Post updated = service.createOrUpdate(postDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
