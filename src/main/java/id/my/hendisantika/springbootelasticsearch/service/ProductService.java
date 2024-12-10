package id.my.hendisantika.springbootelasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import id.my.hendisantika.springbootelasticsearch.model.dto.Product;
import id.my.hendisantika.springbootelasticsearch.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/12/24
 * Time: 05.53
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final String index;
    private final ElasticsearchClient client;

    public Product findById(String id) throws IOException {
        final GetResponse<Product> getResponse = client.get(builder -> builder.index(index).id(id), Product.class);
        Product product = getResponse.source();
        product.setId(id);
        return product;
    }

    public Page<Product> search(String input) throws IOException {
        return createPage(createSearchRequest(input, 0, 10), input);
    }

    public Page<Product> next(Page page) throws IOException {
        int from = page.getFrom() + page.getSize();
        final SearchRequest request = createSearchRequest(page.getInput(), from, page.getSize());
        return createPage(request, page.getInput());
    }

    private Page<Product> createPage(SearchRequest searchRequest, String input) throws IOException {
        final SearchResponse<Product> response = client.search(searchRequest, Product.class);
        if (response.hits().total().value() == 0) {
            return Page.EMPTY;
        }
        if (response.hits().hits().isEmpty()) {
            return Page.EMPTY;
        }

        response.hits().hits().forEach(hit -> hit.source().setId(hit.id()));
        final List<Product> products = response.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
        return new Page(products, input, searchRequest.from(), searchRequest.size());
    }

    private SearchRequest createSearchRequest(String input, int from, int size) {
        return new SearchRequest.Builder()
                .from(from)
                .size(size)
                .query(qb -> qb.multiMatch(mmqb -> mmqb.query(input).fields("name", "description")))
                .build();
    }
}
