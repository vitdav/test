package com.sgugo.sbtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommonTest {

    @Test
    public void test1() throws Exception {
        POITest poi = new POITest();
        poi.test1();
    }
}
