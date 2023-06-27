package com.sgugo.es;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;


public class ESTest6 {
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

        //构建查询条件：也就是请求体，这里进行全量查询
        SearchSourceBuilder ssb = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //色泽fetchSource方法的参数
        String[] includes = {"name","age"};
        String[] excludes = {};
        //设置分页数据：from设置查询的起始index，size设置每页查询几条
        ssb.fetchSource(includes,excludes);

        //请求对象添加查询条件
        request.source(ssb);


        //发送请求，查询数据
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        //查看查询的结果集
        SearchHits hits = response.getHits();
        for(SearchHit hit : hits){
            System.out.println(hit.getSourceAsString());
        }

        //关闭 ES 客户端
        esClient.close();
    }
}

