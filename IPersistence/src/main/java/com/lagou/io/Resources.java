package com.lagou.io;

import java.io.InputStream;

public class Resources {
    // 根据配置文件路径，将配置文件加载成字节输入流，存入到内存中
    public static InputStream getResourceAsStream(String path){
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
