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
package me.zane.fairy_server;

import me.zane.fairy_server.model.Headers;
import me.zane.fairy_server.model.Response;
import okio.Buffer;

/**
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class ResponseFactory {
    private static String DEFAULT_SCHEME = "HTTP/1.1";
    private static int SUCCESS_CODE = 200;
    private static String SUCCESS_MESSAGE = "ok";

    public static Response success(String body) {
        return success(body, new Headers.Builder());
    }

    public static Response success(String body, Headers.Builder builder) {
        Buffer buffer = new Buffer().writeUtf8(body);
        builder.addLenient("Content-Length", String.valueOf(buffer.size()));

        return new Response(DEFAULT_SCHEME, SUCCESS_CODE, SUCCESS_MESSAGE, builder.build(), buffer);
    }

    public static Response failed(int code, String body) {
        return failed(code, body, new Headers.Builder());
    }

    public static Response failed(int code, String body, Headers.Builder builder) {
        Buffer buffer = new Buffer().writeUtf8(body);
        builder.addLenient("Content-Length", String.valueOf(buffer.size()));

        String message = "Unknow error";
        if (code >= 100 && code < 200) {
            message = "Informational";
        } else if (code >= 200 && code < 300) {
            message = "OK";
        } else if (code >= 300 && code < 400) {
            message = "Redirection";
        } else if (code >= 400 && code < 500) {
            message = "Client Error";
        } else if (code >= 500 && code < 600) {
            message = "Server Error";
        }

        return new Response(DEFAULT_SCHEME, code, message, builder.build(), buffer);
    }
}
