package com.sgugo.sbtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/socket")
public class SocketController {

    @GetMapping("test1")
    public void test1() throws IOException {
        Socket socket = new Socket();
    }
}
