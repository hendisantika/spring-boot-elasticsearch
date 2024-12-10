package id.my.hendisantika.springbootelasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
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

import java.util.Iterator;

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
}
