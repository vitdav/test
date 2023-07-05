package com.sgugo.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class EsBaseTest {
    @Resource
    private ElasticsearchClient client;

    @Test
    void indicesCreateTest() throws IOException{
        CreateIndexResponse response = client.indices().create(
                a -> a.index("book2")
        );
        System.out.println(response);
        System.out.println(response.index());
        System.out.println(response.acknowledged());
    }

    @Test
    public void indicesQueryTest() throws IOException {
        GetIndexResponse response = client.indices().get(
                c -> c.index("user")
        );

        System.out.println(response);
    }

    @Test
    public void indicesExistsTest() throws IOException {
        BooleanResponse response = client.indices().exists(
                c -> c.index("user")
        );
        System.out.println(response.value());
    }

    @Test
    public void deleteTest() throws IOException {
        DeleteIndexResponse response = client.indices().delete(
                d -> d.index("user")
        );
        System.out.println(response);
        System.out.println(response.acknowledged());
    }


    @Test
    public void addDocumentTest() throws IOException {

        User user = new User("Tom", 11,"我很酷");

        IndexResponse response = client.index(
                //index(索引).id(文档id).document(要插入的数据=实体类)
                i -> i.index("user").id("2").document(user)
        );
        System.out.println(response);
    }

    @Test
    public void updateDocumentTest() throws IOException {
        User user = new User("Aaron", 12,"我很酷");
        UpdateResponse<User> response = client.update(
                u -> u.index("user").id("1").doc(user), User.class
        );
        System.out.println(response);
    }

    @Test
    public void existDocumentTest() throws IOException {
        BooleanResponse response = client.exists(
                //指定要判断的文档的：索引和文档id
                e -> e.index("user").id("1")
        );
        System.out.println(response);
        System.out.println(response.value());
    }


    @Test
    public void getDocumentTest() throws IOException {
        GetResponse<User> response = client.get(
                g -> g.index("user").id("1"), User.class
        );
        System.out.println(response.source().getAge());
    }

    @Test
    public void deleteDocumentTest() throws IOException {
        DeleteResponse response = client.delete(
                d -> d.index("user").id("2")
        );
        System.out.println(response);
    }


    @Test
    public void bulkTest() throws IOException {
        List<User> userList = new ArrayList<>();
        userList.add(new User("user1", 11,"酷"));
        userList.add(new User("user2", 12,"很酷"));
        userList.add(new User("user3", 13,"超酷"));
        userList.add(new User("user4", 14,"真酷"));
        userList.add(new User("user5", 15,"太酷"));

        List<BulkOperation> bulkList = new ArrayList<>();

        //遍历添加到bulk中
        for(User user : userList){
            bulkList.add(
                BulkOperation.of(
                    o->o.index(i->i.document(user))
                )
            );
        }

        BulkResponse bulkResponse = client.bulk(b -> b.index("user")
                .operations(bulkList));

    }

}
