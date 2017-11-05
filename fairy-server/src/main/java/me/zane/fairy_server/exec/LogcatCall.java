package me.zane.fairy_server.exec;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.zane.fairy_server.ResponseFactory;
import me.zane.fairy_server.ZLog;
import me.zane.fairy_server.model.PostBody;
import me.zane.fairy_server.model.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * 准备回调的数据，进行回调，中间层（发起者）
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class LogcatCall implements Runnable{
    /**
     * 强制退出，强制使用threadtime格式的数据，来支持feed流
     * threadtime — 显示日期、调用时间、优先级、标记以及发出消息的线程的 PID 和 TID。
     */
    private static final String LOGCAT_BASE = "logcat -d -v threadtime %s %s";

    private LogcatExec exec;

    public interface ResponseCallback {
        void onCompleted(Response response);
    }

    private ResponseCallback callback;
    private PostBody postBody;

    public LogcatCall(PostBody postBody, ResponseCallback callback) {
        this.callback = callback;
        this.postBody = postBody;
        exec = new LogcatExec();
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
            String rawOptions = postBody.getValue(PostBody.OPTIONES_KEY);
            String format = extractFormat(rawOptions);

            String options = rawOptions.replace(format, "");
            ZLog.d("rawOptions: " + rawOptions + " format: " + format + " options: " + options);

            sink.write(String.format((LOGCAT_BASE),
                    options,
                    postBody.getValue(PostBody.FILTER_KEY)).getBytes());
            sink.close();
            ZLog.i("exec logcat command: " + String.format((LOGCAT_BASE), options, postBody.getValue(PostBody.FILTER_KEY)));

            response = exec.execWithTime(source, format);
//            if (!options.contains("-v")
//                        || options.contains("-v time")
//                        || options.contains("-v threadtime")) {
//                response = execWithTime(source);
//            } else {
//                response = execWithOutTime(source);
//            }
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
     * 在options中提取 -v [format]
     * @param rawOptions
     * @return
     */
    private String extractFormat(String rawOptions) {
        String format = "";
        if (!rawOptions.contains("-v")) {
            return format;
        }

        Pattern p = Pattern.compile("-v\\s[a-z]*");
        Matcher m = p.matcher(rawOptions);

        while (m.find()) {
            format = rawOptions.substring(m.start(), m.end());
        }

        return format;
    }



//    /**
//     * the dataline without date eg.10-17 11:54:47.550
//     * so it can be stop write data when dataline num is more than ${LINE_THRESHOLD}
//     * @param source
//     * @return
//     */
//    private Response execWithOutTime(BufferedSource source) throws IOException{
//        int lineNum = 0;
//        StringBuilder sb = new StringBuilder();
//
//        while (true) {
//            lineNum++;
//            if (lineNum > LINE_THRESHOLD) {
//                break;
//            }
//
//            String line = source.readUtf8Line();
//            if (line == null) {
//                break;
//            }
//
//            ZLog.d("logcat data: " + line);
//            sb.append(line).append("\n");
//        }
//
//        Result result = new Result(sb.toString(), ""); //时间戳为空
//        return ResponseFactory.success(gson.toJson(result));
//    }
}
