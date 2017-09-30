package me.zane.fairy_server;

/**
 * Created by Zane on 2017/9/30.
 * Email: zanebot96@gmail.com
 */

public class test {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    SystemClock.sleep(500);
                    LogUtil.i("You are all going to die down here!");
                }
            }
        }).start();

        Looper.prepareMainLooper();
        new DemigodServer().start();
        Looper.loop();
    }
}
