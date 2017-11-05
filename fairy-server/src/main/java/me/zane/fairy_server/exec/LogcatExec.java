package me.zane.fairy_server.exec;

import com.google.gson.Gson;

import java.io.IOException;

import me.zane.fairy_server.ResponseFactory;
import me.zane.fairy_server.ZLog;
import me.zane.fairy_server.model.Response;
import me.zane.fairy_server.model.Result;
import okio.BufferedSource;

/**
 * 与Shell交互，执行logcat命令，组装数据
 * Created by Zane on 2017/11/5.
 * Email: zanebot96@gmail.com
 */

class LogcatExec {
    private static final int LINE_THRESHOLD = 100;//如果读取数量超过了阀值，准备开始结束数据的读取
    private Gson gson;

    LogcatExec() {
        gson = new Gson();
    }

    /**
     * the dataline with date eg.10-17 11:54:47.550
     * @param source
     * @param format 开发者定义的format
     * @return
     * @throws IOException
     */
    Response execWithTime(BufferedSource source, String format) throws IOException{
        int lineNum = 0;
        String lastTimeLine = "";
        String currentTimeLine = "";
        StringBuilder sb = new StringBuilder();

        while (true) {
            lineNum++;
            String rawLine = source.readUtf8Line();
            if (rawLine == null) {
                break;
            }

            ZLog.d("logcat data: " + rawLine);
            currentTimeLine = rawLine.substring(0, 18);//截取时间戳
            //大于阀值，开始准备结束数据读取
            if (lineNum > LINE_THRESHOLD) {
                //注意最后一行数据是 -------beginmain的情况
                if (!currentTimeLine.equals(lastTimeLine) && !currentTimeLine.startsWith("-")) {
                    break;
                }
            }

            sb.append(formatLine(format, rawLine)).append("\n");

            if (!currentTimeLine.startsWith("-")) {
                lastTimeLine = currentTimeLine;
            } else {
                currentTimeLine = lastTimeLine;
            }
        }

        Result result = new Result(sb.toString(), currentTimeLine);
        ZLog.d("exec finish");
        return ResponseFactory.success(gson.toJson(result));
    }

    /**
     * Fairy的服务端中，强制使用threadtime(11-05 16:25:19.551  2668  2851 D MSF.D.QLog: QLog init)
     *  格式来支持feed流，在客户端根据需求重新组装数据
     *
     * brief — 显示优先级/标记以及发出消息的进程的 PID
     * process — 仅显示 PID。
     * tag — 仅显示优先级/标记。
     * raw — 显示原始日志消息，不显示其他元数据字段。
     * time — 显示日期、调用时间、优先级/标记以及发出消息的进程的 PID。
     * threadtime — 显示日期、调用时间、优先级、标记以及发出消息的线程的 PID 和 TID。
     * long — 显示所有元数据字段，并使用空白行分隔消息。 (暂不支持)
     *
     * @param format
     * @param rawLine
     * @return
     */
    private String formatLine(String format, String rawLine) {
        //-----begin或者默认格式（threadtime）情况直接返回
        if (rawLine.startsWith("--") || format.equals("")) {
            return rawLine;
        }

        String pid;
        String prioritySympol;
        String raw; //log数据
        String time;
        String date;
        String tid; //thread id

        raw = rawLine.substring(rawLine.indexOf(": ") + 1, rawLine.length());
        date = rawLine.substring(0, 5);
        time = rawLine.substring(6, 18);
        pid = rawLine.substring(20, 24);
        tid = rawLine.substring(26, 30);
        prioritySympol = rawLine.substring(31, rawLine.indexOf(": "));
        ZLog.d(date + " " + time + " " + pid + " " + tid + " " + prioritySympol + " " + raw);

        String line;
        StringBuilder sb = new StringBuilder();
        switch (format.substring(format.lastIndexOf(" ") + 1, format.length())) {
            case "brief":
                line = sb.append(prioritySympol).append(" ").append(pid).append(": ").append(raw).toString();
                break;
            case "process":
                line = sb.append(pid).append(": ").append(raw).toString();
                break;
            case "tag":
                line = sb.append(prioritySympol).append(": ").append(raw).toString();
                break;
            case "raw":
                line = raw;
                break;
            case "time":
                line = sb.append(date).append(" ").append(time)
                               .append(" ").append(prioritySympol).append(" ")
                               .append(pid).append(": ").append(raw).toString();
                break;
            case "threadtime":
                line = sb.append(date).append(" ").append(time)
                               .append(" ").append(prioritySympol).append(" ")
                               .append(pid).append(" ").append(tid).append(": ").append(raw).toString();
                break;
            default:
                line = String.format("Don't support %s", format);
                break;
        }

        return line;
    }
}
