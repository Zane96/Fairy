package me.zane.fairy_server;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * 执行shell logcat，并回调结果
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class LogcatCall implements Runnable{
    private static final String LOGCAT_BASE = "logcat -d %s %s";

    public interface ResponseCallback {
        void onCompleted(Response response);
    }

    private ResponseCallback callback;
    private PostBody postBody;

    public LogcatCall(PostBody postBody, ResponseCallback callback) {
        this.callback = callback;
        this.postBody = postBody;
    }

    @Override
    public void run() {
        //exec shell logcat
        Process shellProcess = null;
        BufferedSource source = null;
        BufferedSink sink = null;
        Response response = null;
        String timeLine = "";
        try {
            shellProcess = new ProcessBuilder("sh").start();
            source = Okio.buffer(Okio.source(shellProcess.getInputStream()));
            sink = Okio.buffer(Okio.sink(shellProcess.getOutputStream()));

            //wirte (logcat [options] [filterspecs]) command
            sink.write((String.format((LOGCAT_BASE), postBody.getValue(PostBody.OPTIONES_KEY), postBody.getValue(PostBody.FILTER_KEY)).getBytes());
            sink.flush();

            if (postBody.getValue(PostBody.TIMELINE_KEY).length() != 0) {
                response = execWithTime(source);
            } else {
                response = execWithOutTime(source);
            }
        } catch (IOException e) {
            response = ResponseFactory.failed(500, new Buffer().write(e.getMessage().getBytes()), timeLine);
        } finally {
            if (source != null) {
                try {
                    source.close();
                } catch (IOException e) {
                    ZLog.e("error in close source stream in Call: " + e.getMessage());
                }
            }
            if (sink != null) {
                try {
                    sink.close();
                } catch (IOException e) {
                    ZLog.e("error in close sink stream in Call: " + e.getMessage());
                }
            }
            callback.onCompleted(response);
        }

    }

    /**
     * exec with -t "${timeline}" option which return by server's response----TimeLine Field
     * @param source
     * @return
     */
    private Response execWithTime(BufferedSource source) {
        String dataLine = "";

    }

    /**
     * exec first without -t "${timeline}" option.
     * @param source
     * @return
     */
    private Response execWithOutTime(BufferedSource source) {

    }
}
