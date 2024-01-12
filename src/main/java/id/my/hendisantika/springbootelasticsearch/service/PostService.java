package id.my.hendisantika.springbootelasticsearch.service;

import id.my.hendisantika.springbootelasticsearch.exception.DataNotFoundException;
import id.my.hendisantika.springbootelasticsearch.model.dto.PostDTO;
import id.my.hendisantika.springbootelasticsearch.model.entity.Author;
import id.my.hendisantika.springbootelasticsearch.model.entity.Post;
import id.my.hendisantika.springbootelasticsearch.model.entity.Tag;
import id.my.hendisantika.springbootelasticsearch.repository.jpa.AuthorRepository;
import id.my.hendisantika.springbootelasticsearch.repository.jpa.PostRepository;
import id.my.hendisantika.springbootelasticsearch.repository.jpa.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

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


    public Post getById(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new DataNotFoundException(
                                        MessageFormat.format("Post id {0} not found", String.valueOf(id))));
    }

    public Post createOrUpdate(PostDTO postRequest) {
        Optional<Post> existingPost = postRepository.findById(postRequest.getId());

        if (existingPost.isPresent()) {
            Post postUpdate = existingPost.get();

            postUpdate.setTitle(postRequest.getTitle());
            postUpdate.setBody(postRequest.getBody());
            if (postRequest.getAuthorId() != 0) {
                Optional<Author> author = authorRepository.findById(postRequest.getAuthorId());
                author.ifPresent(postUpdate::setAuthor);
            }

            return postRepository.save(postUpdate);
        } else {
            return postRepository.save(modelMapper.map(postRequest, Post.class));
        }
    }

    public List<Tag> getAllTagsByPostId(Long id) {
        if (!postRepository.existsById(id)) {
            throw new DataNotFoundException(
                    MessageFormat.format("Post id {0} not found", String.valueOf(id)));
        }

        List<Tag> tagList = postRepository.findById(id).get().getTagList();
        return tagList;
    }

    public Tag addTag(Long postId, Tag tagRequest) {
        return postRepository
                .findById(postId)
                .map(
                        post -> {
                            Optional<Tag> existingTag = tagRepository.findById(tagRequest.getId());
                            if (tagRequest.getId() != 0) {
                                if (existingTag.isPresent()) {
                                    post.addTag(existingTag.get());
                                    postRepository.save(post);
                                    return existingTag.get();
                                } else {
                                    throw new DataNotFoundException(
                                            MessageFormat.format(
                                                    "Tag id {0} not found", String.valueOf(tagRequest.getId())));
                                }
                            } else {
                                // create new tag
                                post.addTag(tagRequest);
                                return tagRepository.save(tagRequest);
                            }
                        })
                .orElseThrow(
                        () ->
                                new DataNotFoundException(
                                        MessageFormat.format("Post id {0} not found", String.valueOf(postId))));
    }
}
