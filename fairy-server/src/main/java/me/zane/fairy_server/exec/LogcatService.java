package me.zane.fairy_server.exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.zane.fairy_server.ZLog;
import me.zane.fairy_server.exec.LogcatCall;
import me.zane.fairy_server.model.PostBody;
import me.zane.fairy_server.model.Request;

/**
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class LogcatService {
    private ExecutorService executor;

    public LogcatService() {
        this.executor = Executors.newCachedThreadPool();
    }

    public void enqueue(Request request, LogcatCall.ResponseCallback callback) {
        String body = request.getBody().readUtf8();
        PostBody postBody = PostBody.parse(body);

        executor.execute(new LogcatCall(postBody, callback));
    }
}
