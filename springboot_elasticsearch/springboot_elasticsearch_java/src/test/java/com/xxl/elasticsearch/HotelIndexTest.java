package com.xxl.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.xxl.elasticsearch.constants.EsConstant;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HotelIndexTest {

    private ElasticsearchClient client;

    private RestClient restClient;

    @BeforeEach
    public void setUp() {
        // 创建低级 REST 客户端
        this.restClient = RestClient.builder(
                HttpHost.create("http://192.168.100.105:9200")
        ).build();

        // 使用 Jackson 创建 JSON 映射器
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // 创建 ES 客户端
        this.client = new ElasticsearchClient(transport);
    }

    @AfterEach
    public void tearDown() throws IOException {
        this.restClient.close();
    }

    // ===================== 索引库操作-start =====================

    /**
     * 创建索引库
     *
     * @throws IOException
     */
    @Test
    public void createHotelIndex() throws IOException {
        client.indices().create(c -> c
                .index(EsConstant.INDEX_HOTEL)
        );
    }

    /**
     * 判断索引库是否存在
     *
     * @throws IOException
     */
    @Test
    public void testExistsHotelIndex() throws IOException {
        BooleanResponse exists = client.indices().exists(c -> c
                .index(EsConstant.INDEX_HOTEL)
        );
        System.err.println(exists.value() ? "索引库已经存在！" : "索引库不存在！");
    }

    /**
     * 删除索引库
     *
     * @throws IOException
     */
    @Test
    public void testDeleteHotelIndex() throws IOException {
        client.indices().delete(c -> c
                .index(EsConstant.INDEX_HOTEL)
        );
    }


}
