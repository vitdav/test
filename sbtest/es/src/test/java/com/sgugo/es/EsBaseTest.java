package com.sgugo.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;


@SpringBootTest
@Slf4j
public class EsBaseTest {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Test
    void contextLoads() throws IOException{

        log.info("创建索引");
        //写法比RestHighLevelClient更加简洁
        CreateIndexResponse indexResponse = elasticsearchClient.indices().create(c -> c.index("user"));
        log.info(String.valueOf(indexResponse));
    }

}
