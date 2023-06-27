package com.sgugo.es;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

public class ESTestTwo {
    @SneakyThrows
    public static void main(String[] args) {
        //创建ES 客户端
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        );
        RestHighLevelClient esClient = new RestHighLevelClient(builder);

        //查询索引：请求对象
        GetIndexRequest request = new GetIndexRequest("user");
        //发送请求，获取查询结果
        GetIndexResponse response = esClient.indices().get(request, RequestOptions.DEFAULT);

        //查看索引的：别名、映射和设置
        System.out.println("Aliases查询结果："+response.getAliases());
        System.out.println("Mappings查询结果："+response.getMappings());
        System.out.println("Mappings查询结果："+response.getSettings());

        //关闭 ES 客户端
        esClient.close();
    }
}
