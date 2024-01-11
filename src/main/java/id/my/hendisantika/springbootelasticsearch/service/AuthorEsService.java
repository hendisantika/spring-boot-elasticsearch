package id.my.hendisantika.springbootelasticsearch.service;

import id.my.hendisantika.springbootelasticsearch.exception.BadRequestException;
import id.my.hendisantika.springbootelasticsearch.exception.DataNotFoundException;
import id.my.hendisantika.springbootelasticsearch.model.es.Author;
import id.my.hendisantika.springbootelasticsearch.repository.es.AuthorEsRepository;
import id.my.hendisantika.springbootelasticsearch.util.Translator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
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

    public Author getById(Long id) {
        return authorEsRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new DataNotFoundException(
                                        MessageFormat.format("Author id {0} not found", String.valueOf(id))));
    }

    public Author createOrUpdate(Author authorRequest) {
        Optional<Author> existingAuthor = authorEsRepository.findById(authorRequest.getId());

        if (existingAuthor.isPresent()) {
            Author authorUpdate = existingAuthor.get();

            authorUpdate.setName(authorRequest.getName());

            return authorEsRepository.save(authorUpdate);
        } else {
            return authorEsRepository.save(authorRequest);
        }
    }

    public void deleteById(Long id) {
        Optional<Author> author = authorEsRepository.findById(id);
        if (author.isPresent()) {
            authorEsRepository.deleteById(id);
        } else {
            throw new BadRequestException(Translator.toLocale("DELETE_ERROR_PLEASE_CHECK_ID"));
        }
    }
}
