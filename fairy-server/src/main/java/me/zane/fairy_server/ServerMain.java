package me.zane.fairy_server;

import android.os.Looper;

import me.zane.fairy_server.exec.FairyServer;

/**
 * 通过app_process去启动Main
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class ServerMain {
    public static void main(String[] args) {
        Looper.prepareMainLooper();
        new FairyServer("fairy_server").start();
        Looper.loop();
    }
}
