package com.sgugo.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;

import java.lang.reflect.Array;
import java.util.Arrays;


public class ESTest3 {
    @SneakyThrows
    public static void main(String[] args) {
        //创建ES 客户端
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        );
        RestHighLevelClient esClient = new RestHighLevelClient(builder);

        //批量新增文档
        //创建请求对象：BulkRequest()
        BulkRequest request = new BulkRequest();

        //添加文档数据：需要使用IndexRequest请求对象
        //为了简化操作，这里直接手写JSON字符串，而不是先创建数据对象再转为JSON
        IndexRequest user1 = new IndexRequest().index("user").id("1002")
                .source(XContentType.JSON, "name", "V", "age", 20, "gender", "male");
        IndexRequest user2 = new IndexRequest().index("user").id("1003")
                .source(XContentType.JSON, "name", "J", "age", 21, "gender", "male");
        IndexRequest user3 = new IndexRequest().index("user").id("1004")
                .source(XContentType.JSON, "name", "T", "age", 22, "gender", "male");

        request.add(user1).add(user2).add(user3);

        //发送请求，获取响应
        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        //查看操作结果
        System.out.println("耗时："+response.getTook());
        System.out.println("成员："+ Arrays.toString(response.getItems()));

        //关闭 ES 客户端
        esClient.close();
    }
}



