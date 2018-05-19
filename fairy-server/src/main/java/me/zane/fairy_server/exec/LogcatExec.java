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
    private static final int LINE_THRESHOLD = 250;//如果读取数量超过了阀值，准备开始结束数据的读取
    private Gson gson;

    LogcatExec() {
        gson = new Gson();
    }

    /**
     * the dataline with date eg.10-17 11:54:47.550
     * @param source
     * @param format 开发者定义的format
     * @param grep
     * @return
     * @throws IOException
     */
    Response execWithTime(BufferedSource source, String format, String grep) throws IOException{
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

            sb.append(formatLine(format, grep, rawLine));
            ZLog.d("add------");

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
     * @param grep
     * @return
     */
    private String formatLine(String format, String grep, String rawLine) {
        //-----begin情况直接返回
        if (rawLine.startsWith("--")) {
            return String.format("%s\n", rawLine);
        }

        //默认格式threadtime
        if (format.equals("")) {
            format = "threadtime";
        }

        String pid;
        String priority;
        String mark;
        String raw; //log数据
        String time;
        String date;
        String tid; //thread id

        raw = rawLine.substring(rawLine.indexOf(": ") + 1, rawLine.length());
        date = rawLine.substring(0, 5);
        time = rawLine.substring(6, 18);
        pid = rawLine.substring(20, 24);
        tid = rawLine.substring(26, 30);
        priority = rawLine.substring(31, 32);
        mark = rawLine.substring(33, rawLine.indexOf(": "));

        //grep颜色包装
        if (!grep.equals("")) {
            raw = raw.replace(grep, String.format("<font color=\"#6200ea\">%s</font>", grep));
        }

        //包装颜色
        String color;

        switch (priority) {
            case "V":
                color = "#e0e0e0";
                break;
            case "D":
                color = "#ffee58";
                break;
            case "I":
                color = "#ffa726";
                break;
            case "W":
                color = "#29b6f6";
                break;
            case "E":
                color = "#ef5350";
                break;
            default:
                color = "#4a148c";
                break;
        }
        StringBuilder sb = new StringBuilder(String.format("<p><font color=\"%s\">", color));

        switch (format.substring(format.lastIndexOf(" ") + 1, format.length())) {
            case "brief":
                sb.append(priority).append(" ")
                        .append(mark).append(" ")
                        .append(pid).append(": ")
                        .append(raw);
                break;
            case "process":
                sb.append(pid).append(": ").append(raw);
                break;
            case "tag":
                sb.append(priority).append(" ")
                        .append(mark).append(": ").append(raw);
                break;
            case "raw":
                sb.append(raw);
                break;
            case "time":
                sb.append(date).append(" ")
                        .append(time).append(" ")
                        .append(priority).append(" ")
                        .append(mark).append(" ")
                        .append(pid).append(": ").append(raw);
                break;
            case "threadtime":
                sb.append(date).append(" ")
                        .append(time).append(" ")
                        .append(priority).append(" ")
                        .append(mark).append(" ")
                        .append(pid).append(" ")
                        .append(tid).append(": ").append(raw);
                break;
            default:
                sb.append(String.format("Don't support %s", format));
                break;
        }

        return sb.append("</p>").toString();
    }
}
