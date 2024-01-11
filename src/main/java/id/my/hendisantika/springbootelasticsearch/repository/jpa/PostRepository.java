package id.my.hendisantika.springbootelasticsearch.repository.jpa;

import id.my.hendisantika.springbootelasticsearch.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/11/24
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String title);

    @Modifying
    @Query(value = "SELECT * FROM posts WHERE title LIKE %:title%", nativeQuery = true)
    List<Post> selectPosts(@Param("title") String title);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.title = :title OR p.body = :body")
    int deletePosts(@Param("title") String title, @Param("body") String body);
}
