package com.sgugo.es;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;


public class ESTest11 {
    @SneakyThrows
    public static void main(String[] args) {
        //创建ES 客户端
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        );
        RestHighLevelClient esClient = new RestHighLevelClient(builder);

        //高亮查询
        //创建请求对象：SearchRequest
        SearchRequest request = new SearchRequest();

        //指定要查询的索引
        request.indices("user");

        //创建强求体
        SearchSourceBuilder ssb = new SearchSourceBuilder();

        //构建查询方式：高亮查询
        MatchPhraseQueryBuilder mp = QueryBuilders.matchPhraseQuery("name", "T");

        //设置高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");//设置标签前缀
        highlightBuilder.postTags("</font>");//设置标签后缀
        highlightBuilder.field("name");//设置高亮字段
        ssb.highlighter(highlightBuilder);

        //设置查询方式
        ssb.query(mp);

        //设置请求体：请求对象添加查询条件
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

