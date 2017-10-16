package me.zane.fairy_server;


import java.io.IOException;

import okio.Buffer;
import okio.BufferedSource;

/**
 * 请求实体封装
 * 提供解析生成实例
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class Request {
    private String method;
    private Headers headers;
    private Buffer body; //可读可写

    private Request(String method, Headers headers, Buffer body){
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Buffer getBody() {
        return body;
    }

    public void setBody(Buffer body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "method: " + method
                + " headers: " + headers.toString()
                + " body: " + body.toString();
    }

    //--------------------------------------------------------

    /**
     * parse the request from the socket inputstream (parse as http data)
     *
     * post / http/1.1
     * content-length=16
     *
     * options=-t 5
     * filter=ActivityManager:I
     * timeLine=null
     *
     * @param source
     * @return
     */
    public static Request parse(BufferedSource source) throws IOException{
        String requestLine = source.readUtf8LineStrict();
        String method = requestLine.substring(0, requestLine.indexOf(" "));

        Headers.Builder headerBuilder = new Headers.Builder();
        String headerLine;
        //long bodySize;
        while ((headerLine = source.readUtf8LineStrict()).length() != 0) {
            headerBuilder.addLenient(headerLine);
//            if ("Content-Length".startsWith(headerLine)) {
//                bodySize = Long.parseLong(headerLine.substring(15).trim());
//            }
        }
        Headers headers = headerBuilder.build();

        Buffer buffer = new Buffer();
        buffer.writeAll(source);
        //buffer.write(source, bodySize);

        return new Request(method, headers, buffer);
    }
}
