package me.zane.fairy_server.exec;

import com.google.gson.Gson;

import java.io.IOException;

import me.zane.fairy_server.ResponseFactory;
import me.zane.fairy_server.ZLog;
import me.zane.fairy_server.model.PostBody;
import me.zane.fairy_server.model.Response;
import me.zane.fairy_server.model.Result;
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
    private static final int LINE_THRESHOLD = 100;//如果读取数量超过了阀值，准备开始结束数据的读取

    private Gson gson;

    public interface ResponseCallback {
        void onCompleted(Response response);
    }

    private ResponseCallback callback;
    private PostBody postBody;

    public LogcatCall(PostBody postBody, ResponseCallback callback) {
        this.callback = callback;
        this.postBody = postBody;
        gson = new Gson();
    }

    @Override
    public void run() {
        //exec shell logcat
        ZLog.d("start exec");
        Process shellProcess;
        BufferedSource source = null;
        BufferedSink sink = null;
        Response response = null;
        try {
            shellProcess = new ProcessBuilder("sh").start();
            source = Okio.buffer(Okio.source(shellProcess.getInputStream()));
            sink = Okio.buffer(Okio.sink(shellProcess.getOutputStream()));

            //wirte (logcat [options] [filterspecs]) command
            String options = postBody.getValue(PostBody.OPTIONES_KEY);

            sink.write(String.format((LOGCAT_BASE),
                    options,
                    postBody.getValue(PostBody.FILTER_KEY)).getBytes());
            sink.close();
            ZLog.i("exec logcat command: " + String.format((LOGCAT_BASE), options, postBody.getValue(PostBody.FILTER_KEY)));

            if (!options.contains("-v")
                        || options.contains("-v time")
                        || options.contains("-v threadtime")) {
                response = execWithTime(source);
            } else {
                response = execWithOutTime(source);
            }
        } catch (IOException e) {
            response = ResponseFactory.failed(500, e.getMessage());
        } finally {
            if (source != null) {
                try {
                    source.close();
                } catch (IOException e) {
                    ZLog.e("error in close source stream in Call: " + e.getMessage());
                }
            }
            callback.onCompleted(response);
        }

    }

    /**
     * the dataline with date eg.10-17 11:54:47.550
     * @param source
     * @return
     */
    private Response execWithTime(BufferedSource source) throws IOException{
        int lineNum = 0;
        String lastTimeLine = "";
        String currentTimeLine = "";
        StringBuilder sb = new StringBuilder();

        while (true) {
            lineNum++;
            String line = source.readUtf8Line();
            if (line == null) {
                break;
            }

            ZLog.d("logcat data: " + line);
            currentTimeLine = line.substring(0, 18);//截取时间戳
            //大于阀值，开始准备结束数据读取
            if (lineNum > LINE_THRESHOLD) {
                //注意最后一行数据是 -------beginmain的情况
                if (!currentTimeLine.equals(lastTimeLine) && !currentTimeLine.startsWith("-")) {
                    break;
                }
            }
            sb.append(line).append("\n");
            if (!currentTimeLine.startsWith("-")) {
                lastTimeLine = currentTimeLine;
            } else {
                currentTimeLine = lastTimeLine;
            }
        }

        Result result = new Result(sb.toString(), currentTimeLine);
        ZLog.d("finish");
        return ResponseFactory.success(gson.toJson(result));
    }

    /**
     * the dataline without date eg.10-17 11:54:47.550
     * so it can be stop write data when dataline num is more than ${LINE_THRESHOLD}
     * @param source
     * @return
     */
    private Response execWithOutTime(BufferedSource source) throws IOException{
        int lineNum = 0;
        StringBuilder sb = new StringBuilder();

        while (true) {
            lineNum++;
            if (lineNum > LINE_THRESHOLD) {
                break;
            }

            String line = source.readUtf8Line();
            if (line == null) {
                break;
            }

            ZLog.d("logcat data: " + line);
            sb.append(line).append("\n");
        }

        Result result = new Result(sb.toString(), ""); //时间戳为空
        return ResponseFactory.success(gson.toJson(result));
    }
}
