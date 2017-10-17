package me.zane.fairy_server.exec;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import me.zane.fairy_server.Config;
import me.zane.fairy_server.ZLog;
import me.zane.fairy_server.model.Headers;
import me.zane.fairy_server.model.Request;
import me.zane.fairy_server.model.Response;
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
    private LogcatService service;

    public FairyServer(String name) {
        super(name);
        service = new LogcatService();
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
        service.enqueue(request, new LogcatCall.ResponseCallback() {
            @Override
            public void onCompleted(Response response) {
                try {
                    writeResponse(sink, response);
                } catch (IOException e) {
                    ZLog.e("error in write response to socket: " + e.getMessage());
                    // TODO: 2017/10/17 ???
                }
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

    private void writeResponse(BufferedSink sink,Response response) throws IOException {
        StringBuilder status = new StringBuilder();
        status.append(response.getScheme())
                .append(" ")
                .append(response.getCode())
                .append(" ")
                .append(response.getMessage());

        sink.writeUtf8(status.toString());
        sink.writeUtf8("\r\n");

        Headers headers = response.getHeaders();
        for (int i = 0, size = headers.size(); i < size; i++) {
            sink.writeUtf8(headers.name(i));
            sink.writeUtf8(": ");
            sink.writeUtf8(headers.value(i));
            sink.writeUtf8("\r\n");
        }
        sink.writeUtf8("\r\n");
        sink.flush();

        Buffer body = response.getBody();
        if (body == null) return;
        response.getBody().readAll(sink);
        sink.flush();
    }
}
