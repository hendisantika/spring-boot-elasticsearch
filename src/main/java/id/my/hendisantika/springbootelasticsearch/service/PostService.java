package id.my.hendisantika.springbootelasticsearch.service;

import id.my.hendisantika.springbootelasticsearch.model.entity.Post;
import id.my.hendisantika.springbootelasticsearch.repository.jpa.AuthorRepository;
import id.my.hendisantika.springbootelasticsearch.repository.jpa.PostRepository;
import id.my.hendisantika.springbootelasticsearch.repository.jpa.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/12/24
 * Time: 07:36
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final ModelMapper modelMapper;

    private final PostRepository postRepository;

    private final AuthorRepository authorRepository;

    private final TagRepository tagRepository;

    @Cacheable(value = "posts")
    public List<Post> getAllPosts(String title) {
        List<Post> postList;
        if (title == null) {
            postList = postRepository.findAll();
        } else {
            postList = postRepository.findByTitleContaining(title);
        }
        return postList;
    }

}
