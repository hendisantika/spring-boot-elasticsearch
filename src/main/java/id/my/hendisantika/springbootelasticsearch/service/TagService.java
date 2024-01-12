package id.my.hendisantika.springbootelasticsearch.service;

import id.my.hendisantika.springbootelasticsearch.exception.BadRequestException;
import id.my.hendisantika.springbootelasticsearch.exception.DataNotFoundException;
import id.my.hendisantika.springbootelasticsearch.model.entity.Tag;
import id.my.hendisantika.springbootelasticsearch.repository.jpa.TagRepository;
import id.my.hendisantika.springbootelasticsearch.util.Translator;
import lombok.RequiredArgsConstructor;
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
 * Time: 07:39
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        List<Tag> tagList = tagRepository.findAll();
        return tagList;
    }

    public Tag getById(Long id) {
        return tagRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new DataNotFoundException(
                                        MessageFormat.format("Tag id {0} not found", String.valueOf(id))));
    }

    public Tag createOrUpdate(Tag tagRequest) {
        Optional<Tag> existingTag = tagRepository.findById(tagRequest.getId());

        if (existingTag.isPresent()) {
            Tag tagUpdate = existingTag.get();

            tagUpdate.setName(tagRequest.getName());

            return tagRepository.save(tagUpdate);
        } else {
            return tagRepository.save(tagRequest);
        }
    }

    public void deleteById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            tagRepository.deleteById(id);
        } else {
            throw new BadRequestException(Translator.toLocale("DELETE_ERROR_PLEASE_CHECK_ID"));
        }
    }
}
