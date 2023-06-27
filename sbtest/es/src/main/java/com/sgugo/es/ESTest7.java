package com.sgugo.es;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;


public class ESTest7 {
    @SneakyThrows
    public static void main(String[] args) {
        //创建ES 客户端
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        );
        RestHighLevelClient esClient = new RestHighLevelClient(builder);

        //分页查询
        //创建请求对象：SearchRequest
        SearchRequest request = new SearchRequest();

        //指定要查询的索引
        request.indices("user");

        //构建查询条件：也就是请求体
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        // 创建BoolQueryBuilder对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 调用组合方法
        boolQueryBuilder.must(QueryBuilders.matchQuery("age","21"));
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("gender","female"));

        //请求体拼接 组合查询的条件：BoolQueryBuilder对象
        ssb.query(boolQueryBuilder);

        //请求对象添加查询条件
        request.source(ssb);

        //发送请求，查询数据
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        //查看查询的结果集
        SearchHits hits = response.getHits();
        for(SearchHit hit : hits){
            System.out.println(hit);
        }

        //关闭 ES 客户端
        esClient.close();
    }
}

