package com.xxl.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.xxl.elasticsearch.constants.EsConstant;
import com.xxl.elasticsearch.constants.LoginConstants;
import com.xxl.elasticsearch.dto.LoginDoc;
import com.xxl.elasticsearch.po.Login;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {


    private RestHighLevelClient client;

    @Before
    public void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.1.109:9200")
        ));
    }

    @After
    public void tearDown() throws IOException {
        this.client.close();
    }

    /**
     * 创建索引库
     *
     * @throws IOException
     */
    @Test
    public void createHotelIndex() throws IOException {
        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest(EsConstant.INDEX_LOGIN);
        // 2.准备请求的参数：DSL语句
        request.source(LoginConstants.MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 判断索引库是否存在
     *
     * @throws IOException
     */
    @Test
    public void testExistsHotelIndex() throws IOException {
        // 1.创建Request对象
        GetIndexRequest request = new GetIndexRequest(EsConstant.INDEX_LOGIN);
        // 2.发送请求
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        // 3.输出
        System.err.println(exists ? "索引库已经存在！" : "索引库不存在！");
    }

    /**
     * 删除索引库
     *
     * @throws IOException
     */
    @Test
    public void testDeleteHotelIndex() throws IOException {
        // 1.创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest(EsConstant.INDEX_LOGIN);
        // 2.发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }



    /**
     * 新增文档
     *
     * @throws IOException
     */
    @Test
    public void testAddDocument() throws IOException {
        // 1.根据id查询酒店数据
        //Login hotel = hotelService.getById(61083L);
        Login login = new Login();
        login.setUserId(1112L);
        login.setMobile(15510008021L);
        login.setTenantId(1);
        // 2.转换为文档类型
        LoginDoc hotelDoc = new LoginDoc(login);
        // 3.将HotelDoc转json
        String json = JSON.toJSONString(hotelDoc);

        // 1.准备Request对象
        IndexRequest request = new IndexRequest(EsConstant.INDEX_LOGIN).id(hotelDoc.getMobile().toString());
        // 2.准备Json文档
        request.source(json, XContentType.JSON);
        // 3.发送请求
        client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 查询文档
     *
     * @throws IOException
     */
    @Test
    public void testGetDocumentById() throws IOException {
        // 1.准备Request
        GetRequest request = new GetRequest(EsConstant.INDEX_LOGIN, "15510008021");
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 3.解析响应结果
        String json = response.getSourceAsString();

        LoginDoc loginDoc = JSON.parseObject(json, LoginDoc.class);
        System.out.println(loginDoc);
    }

    /**
     * 批量导入文档
     *
     * @throws IOException
     */
    @Test
    public void testBulkRequest() throws IOException {
        // 批量查询酒店数据
//        List<Login> logins = hotelService.list();
//
//        // 1.创建Request
//        BulkRequest request = new BulkRequest();
//        // 2.准备参数，添加多个新增的Request
//        for (Login login : logins) {
//            // 2.1.转换为文档类型HotelDoc
//            LoginDoc hotelDoc = new LoginDoc(login);
//            // 2.2.创建新增文档的Request对象
//            request.add(new IndexRequest("login")
//                    .id(hotelDoc.getUserId().toString())
//                    .source(JSON.toJSONString(hotelDoc), XContentType.JSON));
//        }
//        // 3.发送请求
//        client.bulk(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Test
    public void getDocumentByMobile() {
        // 1.准备Request
//        GetRequest request = new GetRequest(EsConstant.INDEX_LOGIN, "15510008022");
//        // 2.发送请求，得到响应
//        GetResponse response = client.get(request, RequestOptions.DEFAULT);
//        // 3.解析响应结果
//        String json = response.getSourceAsString();
//
//        LoginDoc hotelDoc = JSON.parseObject(json, LoginDoc.class);
//        System.out.println(hotelDoc);
        // 1.准备Request
        SearchRequest request = new SearchRequest("logstash");
        // 2.准备DSL
        request.source().query(QueryBuilders.termQuery("mobile", "15510008021"));
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        System.out.println(response);
    }

}
