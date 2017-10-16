package me.zane.fairy_server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.geometry.Pos;

/**
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class LogcatService {

    private LogcatCall asyncCall;
    private ExecutorService executor;
    private Request request;

    public LogcatService(Request request) {
        this.executor = Executors.newCachedThreadPool();
        this.request = request;
    }

    public void enqueue(LogcatCall.ResponseCallback callback) {
        String body = request.getBody().readUtf8();
        PostBody postBody = PostBody.parse(body);

        asyncCall = new LogcatCall(postBody, callback);
        executor.execute(asyncCall);
    }
}
