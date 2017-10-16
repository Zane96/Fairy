package me.zane.fairy_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class FairyServer extends Thread{
    private ServerSocket serverSocket;

    public FairyServer(String name) {
        super(name);

    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(Config.SERVER_PORT);
        } catch (IOException e) {
            ZLog.e("Socket error when open:" + e.getMessage());
            return;
        }

        ZLog.i("Server Socket open success: " + serverSocket.getInetAddress());
        while (true) {
            final Socket socket;
            try {
                socket = serverSocket.accept();
                ZLog.i("Client Socket accept success from: " + serverSocket.getInetAddress());

                final BufferedSource source = Okio.buffer(Okio.source(socket));
                final BufferedSink sink = Okio.buffer(Okio.sink(socket));

                process(socket, source, sink);
            } catch (IOException e) {
                ZLog.e("Socket error in serverSocket accept(): " + e.getMessage());
                if (serverSocket.isClosed()) {
                    break;
                }
            }
        }
    }

    private void process(final Socket socket, final BufferedSource source, final BufferedSink sink) throws IOException{
        Request request = Request.parse(source);
        new LogcatService(request).enqueue(new LogcatCall.ResponseCallback() {
            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onFailed(String error) {
                ZLog.e("Error in execute shell logcat: " + error);

            }

            @Override
            public void onCompleted(){
                try {
                    source.close();
                    sink.close();
                    socket.close();
                } catch (IOException e) {
                    ZLog.i("error in socket and stream close: " + e.getMessage());
                }
            }
        });
    }
}
