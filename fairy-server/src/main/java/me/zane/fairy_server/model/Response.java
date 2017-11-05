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


import okio.Buffer;

/**
 * 响应实体封装
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class Response {
    private String scheme;
    private int code;
    private String message;
    private Headers headers;
    private Buffer body;

    public Response(String scheme, int code, String message, Headers headers, Buffer body) {
        this.scheme = scheme;
        this.code = code;
        this.message = message;
        this.headers = headers;
        this.body = body;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String shcheme) {
        this.scheme = shcheme;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    //------------------------------------------------------------


    @Override
    public String toString() {
        return "scheme: " + scheme
                + " code: " + code
                + " message: " + message
                + " headers: " + headers.toString();
    }
}
