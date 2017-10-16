package me.zane.fairy_server;

import okio.Buffer;

/**
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class ResponseFactory {
    private static String DEFAULT_SCHEME = "http/1.1";
    private static int SUCCESS_CODE = 200;
    private static String SUCCESS_MESSAGE = "success";

    public static Response success(Buffer body, String timeLine) {
        return success(body, new Headers.Builder(), timeLine);
    }

    public static Response success(Buffer body, Headers.Builder builder, String timeLine) {
        builder.addLenient("Content-Length", String.valueOf(body.size()));

        return new Response(DEFAULT_SCHEME, SUCCESS_CODE, SUCCESS_MESSAGE, builder.build(), body, timeLine);
    }

    public static Response failed(int code, Buffer body, String timeLine) {
        return failed(code, body, new Headers.Builder(), timeLine);
    }

    public static Response failed(int code, Buffer body, Headers.Builder builder, String timeLine) {
        builder.addLenient("Content-Length", String.valueOf(body.size()));

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

        return new Response(DEFAULT_SCHEME, code, message, builder.build(), body, timeLine);
    }
}
