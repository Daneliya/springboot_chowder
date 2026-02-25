package com.xxl.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.alibaba.fastjson.JSON;
import com.xxl.elasticsearch.constants.EsConstant;
import com.xxl.elasticsearch.dto.HotelDoc;
import com.xxl.elasticsearch.pojo.Hotel;
import com.xxl.elasticsearch.service.IHotelService;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ES 新 Java Client 测试类
 * 使用 co.elastic.clients 包 (ES 8.x 推荐)
 *
 * @author xxl
 * @date 2026/2/24 15:22
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration," +
                "org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientAutoConfiguration"
})
public class HotelDocumentClientTest {

    @Autowired
    private IHotelService hotelService;

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

    /**
     * 新增文档
     */
    @Test
    public void testAddDocument() throws IOException {
        // 1.根据id查询酒店数据
        Hotel hotel = hotelService.getById(61083L);
        // 2.转换为文档类型
        HotelDoc hotelDoc = new HotelDoc(hotel);
        // 3.保存
        IndexResponse response = client.index(i -> i
                .index("hotel")
                .id(String.valueOf(hotelDoc.getId()) + 1)
                .document(hotelDoc)
        );

        System.out.println("索引结果: " + response.result());
    }

    /**
     * 查询文档
     */
    @Test
    public void testGetDocumentById() throws IOException {
        // 使用新 API 获取文档
        GetResponse<HotelDoc> response = client.get(g -> g
                .index("hotel")
                .id("61082"), HotelDoc.class);

        if (response.found()) {
            HotelDoc hotelDoc = response.source();
            System.out.println("查询结果: " + JSON.toJSONString(hotelDoc));
        } else {
            System.out.println("文档不存在");
        }
    }

    /**
     * 删除文档
     */
    @Test
    public void testDeleteDocumentById() throws IOException {
        client.delete(d -> d
                .index(EsConstant.INDEX_HOTEL)
                .id("61083")
        );
    }

    /**
     * 更新文档
     */
    @Test
    void testUpdateDocument() throws IOException {
        // 1.根据id查询酒店数据
        Hotel hotel = hotelService.getById(61083L);
        // 2.转换为文档类型
        HotelDoc hotelDoc = new HotelDoc(hotel);
        hotelDoc.setStarName("五钻");
        hotelDoc.setPrice(1000);

        UpdateResponse response = client.update(u -> u
                        .index("hotel")
                        .id("61083")
                        .doc(hotelDoc)
                        .upsert(hotelDoc),
                HotelDoc.class
        );
        System.out.println("更新结果: " + response.result());

        // 使用 Map 构建更新字段
        Map<String, Object> doc = Map.of(
                "price", "999",
                "starName", "五钻"
        );
        UpdateResponse response2 = client.update(u -> u
                        .index("hotel")
                        .id("61083")
                        .doc(doc)
                        .upsert(doc),
                Map.class
        );
        System.out.println("更新结果: " + response2.result());
    }

    /**
     * 批量导入文档
     */
    @Test
    public void testBulkRequest() throws IOException {
        List<Hotel> hotelList = hotelService.list();

        List<BulkOperation> operations = new ArrayList<>();

        for (Hotel hotel : hotelList) {
            HotelDoc hotelDoc = new HotelDoc(hotel);

            // 构建批量操作
            BulkOperation op = BulkOperation.of(b -> b
                    .index(i -> i
                            .index("hotel")
                            .id(hotelDoc.getId().toString())
                            .document(hotelDoc)
                    )
            );
            operations.add(op);
        }

        // 执行批量请求
        BulkResponse response = client.bulk(b -> b
                .operations(operations)
        );

        // 检查是否有错误
        if (response.errors()) {
            System.out.println("批量导入存在错误");
            response.items().forEach(item -> {
                if (item.error() != null) {
                    System.out.println("错误: " + item.error().reason());
                }
            });
        } else {
            System.out.println("批量导入成功，共: " + response.items().size() + " 条");
        }
    }

    /**
     * 搜索文档
     */
    @SneakyThrows
    @Test
    public void getDocumentByMobile() {
        // 使用新 API 进行搜索
        SearchResponse<Map> response = client.search(s -> s
                        .index("hotel")
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value("如家")
                                )
                        ),
                Map.class
        );

        System.out.println("搜索结果总数: " + response.hits().total().value());
        response.hits().hits().forEach(hit -> {
            System.out.println("文档: " + JSON.toJSONString(hit.source()));
        });
    }

//    /**
//     * 分页搜索
//     *
//     * @throws IOException
//     */
//    @Test
//    void testPageAndSort() throws IOException {
//        // 页码，每页大小
//        int page = 1, size = 5;
//
//        // 1.准备Request
//        SearchRequest request = new SearchRequest("hotel");
//        // 2.准备DSL
//        // 2.1.query
//        request.source().query(QueryBuilders.matchAllQuery());
//        // 2.2.排序 sort
//        request.source().sort("price", SortOrder.ASC);
//        // 2.3.分页 from、size
//        request.source().from((page - 1) * size).size(5);
//        // 3.发送请求
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//        // 4.解析响应
//        handleResponse(response);
//
//    }
//
//    private void handleResponse(SearchResponse response) {
//        // 4.解析响应
//        SearchHits searchHits = response.getHits();
//        // 4.1.获取总条数
//        long total = searchHits.getTotalHits().value;
//        System.out.println("共搜索到" + total + "条数据");
//        // 4.2.文档数组
//        SearchHit[] hits = searchHits.getHits();
//        // 4.3.遍历
//        for (SearchHit hit : hits) {
//            // 获取文档source
//            String json = hit.getSourceAsString();
//            // 反序列化
//            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
//            System.out.println("hotelDoc = " + hotelDoc);
//        }
//    }

    @Test
    void testPageAndSort() throws IOException {
        // 页码，每页大小
        int page = 1, size = 5;

        // 1. 构建查询并发送请求（ES 8.x 流式 API 写法）
        SearchResponse<HotelDoc> response = client.search(
                // 第一个参数：构建 SearchRequest 的 lambda 表达式
                s -> s
                        .index("hotel") // 指定索引名
                        .query(q -> q.matchAll(m -> m)) // 匹配所有文档（替代 matchAllQuery()）
                        .sort(sort -> sort // 排序（替代 sort("price", SortOrder.ASC)）
                                .field(f -> f
                                        .field("price") // 排序字段
                                        .order(co.elastic.clients.elasticsearch._types.SortOrder.Asc) // 升序
                                )
                        )
                        .from((page - 1) * size) // 分页起始位置（替代 from()）
                        .size(size), // 每页条数（替代 size()）
                // 第二个参数：文档类型的 Class 对象，自动反序列化
                HotelDoc.class
        );

        // 2. 解析响应结果
        handleResponse(response);
    }

    /**
     * 解析 ES 8.x SearchResponse 响应结果
     *
     * @param response ES 8.x 搜索响应对象
     */
    private void handleResponse(SearchResponse<HotelDoc> response) {
        // 1. 获取总命中数
        TotalHits totalHits = response.hits().total();
        if (totalHits != null) {
            long total = totalHits.value();
            // totalHits.relation() 表示计数类型（eq=精确值，gte=大于等于）
            System.out.println("共搜索到" + total + "条数据（计数类型：" + totalHits.relation() + "）");
        }

        // 2. 遍历命中的文档
        for (Hit<HotelDoc> hit : response.hits().hits()) {
            // 直接获取反序列化后的 HotelDoc 对象（无需手动 parse JSON）
            HotelDoc hotelDoc = hit.source();
            // 可选：获取文档元数据（ID、得分、版本等）
            String docId = hit.id();
            Double score = hit.score();
            System.out.println("文档ID：" + docId + "，得分：" + score + "，内容：" + hotelDoc);
        }
    }
}
