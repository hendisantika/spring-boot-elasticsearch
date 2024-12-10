package id.my.hendisantika.springbootelasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.HealthStatus;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.auth.CredentialsProvider;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/12/24
 * Time: 05.46
 * To change this template use File | Settings | File Templates.
 */
public class ElasticSearchTests {
    private static final String IMAGE_NAME = "docker.elastic.co/elasticsearch/elasticsearch:8.3.3";
    private static final ElasticsearchContainer container =
            new ElasticsearchContainer(IMAGE_NAME)
                    .withExposedPorts(9200)
                    .withPassword("53cret");

    private static final NodeSelector INGEST_NODE_SELECTOR = nodes -> {
        final Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            // roles may be null if we don't know, thus we keep the node in then...
            if (node.getRoles() != null && !node.getRoles().isIngest()) {
                iterator.remove();
            }
        }
    };
    private static final String INDEX = "my_index";
    private static ElasticsearchClient client;
    private static RestClient restClient;
    private static ProductServiceImpl productService;
    private static ElasticsearchAsyncClient asyncClient;

    @BeforeAll
    public static void startElasticsearchCreateLocalClient() throws Exception {
        container.start();

        HttpHost host = new HttpHost("localhost", container.getMappedPort(9200), "https");
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "s3cret"));
        final RestClientBuilder builder = RestClient.builder(host);

        builder.setHttpClientConfigCallback(clientBuilder -> {
            clientBuilder.setSSLContext(container.createSslContextFromCa());
            clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return clientBuilder;
        });
        builder.setNodeSelector(INGEST_NODE_SELECTOR);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        restClient = builder.build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper(mapper));
        client = new ElasticsearchClient(transport);
        asyncClient = new ElasticsearchAsyncClient(transport);
        productService = new ProductServiceImpl(INDEX, client);
    }

    @AfterAll
    public static void closeResources() throws Exception {
        restClient.close();
    }

    @AfterEach
    public void deleteProductIndex() throws Exception {
        client.indices().delete(b -> b.index(INDEX));
    }

    @Test
    public void testClusterVersion() throws Exception {
        // this just exists to index some data, so the index deletion does not fail
        productService.save(createProducts(1));

        final HealthResponse response = client.cluster().health();
        // check for yellow or green cluster health
        assertThat(response.status()).isNotEqualTo(HealthStatus.Red);

        // TODO: add back one async health request request
        CountDownLatch latch = new CountDownLatch(1);
        asyncClient.cluster().health()
                .whenComplete((resp, throwable) -> {
                    assertThat(resp.status()).isNotEqualTo(HealthStatus.Red);
                    latch.countDown();
                });
        latch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void indexProductWithoutId() throws Exception {
        Product product = createProducts(1).get(0);
        product.setId(null);
        assertThat(product.getId()).isNull();

        productService.save(product);

        assertThat(product.getId()).isNotNull();
    }

    @Test
    public void indexProductWithId() throws Exception {
        Product product = createProducts(1).get(0);
        assertThat(product.getId()).isEqualTo("0");

        productService.save(product);

        product = productService.findById("0");
        assertThat(product.getId()).isEqualTo("0");
    }

    @Test
    public void testFindProductById() throws Exception {
        productService.save(createProducts(3));

        final Product product1 = productService.findById("0");
        assertThat(product1.getId()).isEqualTo("0");
        final Product product2 = productService.findById("1");
        assertThat(product2.getId()).isEqualTo("1");
        final Product product3 = productService.findById("2");
        assertThat(product3.getId()).isEqualTo("2");
    }
}
