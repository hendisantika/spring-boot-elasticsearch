package id.my.hendisantika.springbootelasticsearch.controller;

import id.my.hendisantika.springbootelasticsearch.model.entity.Tag;
import id.my.hendisantika.springbootelasticsearch.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/12/24
 * Time: 07:45
 * To change this template use File | Settings | File Templates.
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "Post Tag", description = "Endpoint for managing Post Tag")
public class PostTagController {

    private final PostService service;

    @GetMapping("/v1/posts/{id}/tags")
    @Operation(
            summary = "Get All Tag Post Data",
            description = "Get All Tag Post Data.",
            tags = {"Post"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            id.my.hendisantika.springbootelasticsearch.model.entity.Author.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public ResponseEntity<List<Tag>> getAllTagsByPostId(@PathVariable(value = "id") Long id) {
        List<Tag> tagList = service.getAllTagsByPostId(id);
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

    @PostMapping("/v1/posts/{id}/tags")
    @Operation(
            summary = "Add New Post Tag Data",
            description = "Add New Post Tag Data.",
            tags = {"Post"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            id.my.hendisantika.springbootelasticsearch.model.entity.Author.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public ResponseEntity<Tag> addTag(@PathVariable("id") Long id, @RequestBody Tag tagRequest) {
        Tag updated = service.addTag(id, tagRequest);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/v1/posts/{id}/tags/{tagId}")
    @Operation(
            summary = "Delete Tag From Post Data",
            description = "Delete Tag From Post Data.",
            tags = {"Post"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            id.my.hendisantika.springbootelasticsearch.model.entity.Author.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public void deleteTagFromPost(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId) {
        service.deleteTagFromPost(id, tagId);
    }
}
