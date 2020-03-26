package com.lagou.servlet;

import serverSource.HttpProtocolUtil;
import serverSource.HttpServlet;
import serverSource.Request;
import serverSource.Response;

import java.io.IOException;

public class Dome1Controller extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {
        String content = "<h1>LagouServlet Dome1 get</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {

    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }
}
