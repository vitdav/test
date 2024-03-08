package com.sgugo.sbtest.nio;

import java.io.IOException;

public class ClientA {
    public static void main(String[] args) throws IOException {
        new ChatClient().startClient("Shark");
    }
}
