/*
 * Copyright (C) 2017 Zane.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zane.fairy_server.model;


import java.io.IOException;

import me.zane.fairy_server.ZLog;
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
        ZLog.d(requestLine);
        String method = requestLine.substring(0, requestLine.indexOf(" "));
        ZLog.d(method);

        Headers.Builder headerBuilder = new Headers.Builder();
        String headerLine;
        long bodySize = 0;
        while ((headerLine = source.readUtf8LineStrict()).length() != 0) {
            ZLog.d("headline: " + headerLine);
            headerBuilder.addLenient(headerLine);
            if (headerLine.startsWith("Content-Length")) {
                bodySize = Long.parseLong(headerLine.substring(15).trim());
            }
        }
        Headers headers = headerBuilder.build();

        Buffer buffer = new Buffer();
        //buffer.writeAll(source);
        buffer.write(source, bodySize);

        return new Request(method, headers, buffer);
    }
}
