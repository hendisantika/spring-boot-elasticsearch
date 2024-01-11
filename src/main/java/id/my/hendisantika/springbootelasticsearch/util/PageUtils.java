package id.my.hendisantika.springbootelasticsearch.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/11/24
 * Time: 09:48
 * To change this template use File | Settings | File Templates.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtils {

    public static Pageable pageable(int page, int pageSize, String sortField, String sortDirection) {
        if (page == 0) {
            return Pageable.unpaged();
        }
        return PageRequest.of(
                page - 1, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
    }
}
