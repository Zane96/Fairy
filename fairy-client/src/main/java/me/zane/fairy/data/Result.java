package me.zane.fairy.data;

import android.support.annotation.NonNull;

/**
 * Created by Zane on 2017/11/5.
 * Email: zanebot96@gmail.com
 */

public class Result {
    private String data;
    private Result(){}

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * Fairy的服务端中，强制使用threadtime格式来支持feed流，在客户端根据需求重新组装数据
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
     * @param rawData
     * @return
     */
    public static Result formatResult(@NonNull String format, String rawData) {
        String pid;
        String prioritySympol;
        String raw;
        String time;
        String date;
        String tid; //thread id

        Result result = new Result();
        if (format.equals("threadtime")) {
            result.setData(rawData);
            return result;
        }

        String data;
        String[] datas = rawData.split(" ");
        date = datas[0];
        time = datas[1];
        pid = datas[2];
        tid = datas[3];
//
//        switch (rawData) {
//            case "pid":
//
//        }

        return result;
    }
}
