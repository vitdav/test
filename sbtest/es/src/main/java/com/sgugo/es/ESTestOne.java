package com.sgugo.es;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

public class ESTestOne {
    @SneakyThrows
    public static void main(String[] args) {
        //创建ES 客户端
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        );
        RestHighLevelClient esClient = new RestHighLevelClient(builder);

        //创建索引
        //创建索引请求对象
        CreateIndexRequest request = new CreateIndexRequest("user");
        //发送请求，创建索引,获取响应对象
        CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);
        //查看操作状态：检测索引是否创建成功
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged); //true

        //关闭 ES 客户端
        esClient.close();
    }
}
