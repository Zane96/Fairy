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
                ZLog.i("Client Socket accept success from: " + socket.getInetAddress());

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
        final Request request = Request.parse(source);
        service.enqueue(request, new LogcatCall.ResponseCallback() {
            @Override
            public void onCompleted(Response response) {
                try {
                    ZLog.d("response: " + response.toString());
                    writeResponse(socket, sink, response);
                    ZLog.d("finish write");
                } catch (IOException e) {
                    ZLog.e("error in write response to socket: " + e.getMessage() + socket.isClosed());
                }
                try {
                    source.close();
                    sink.close();
                } catch (IOException e) {
                    ZLog.e("error in socket and stream close: " + e.getMessage() + socket.isClosed());
                } catch (Throwable e) {
                    ZLog.e(e.getMessage());
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        ZLog.e(e.getMessage());
                    }
                }
            }
        });
    }

    private void writeResponse(Socket socket, BufferedSink sink, Response response) throws IOException {
        StringBuilder status = new StringBuilder();
        status.append(response.getScheme())
                .append(" ")
                .append(response.getCode())
                .append(" ")
                .append(response.getMessage());

        sink.writeUtf8(status.toString());
        ZLog.d("status: " + status.toString());
        sink.writeUtf8("\r\n");

        Headers headers = response.getHeaders();
        for (int i = 0, size = headers.size(); i < size; i++) {
            sink.writeUtf8(headers.name(i));
            sink.writeUtf8(": ");
            sink.writeUtf8(headers.value(i));
            sink.writeUtf8("\r\n");
            ZLog.d(headers.name(i) + " " + headers.value(i));
        }
        sink.writeUtf8("\r\n");
        sink.flush();

        Buffer body = response.getBody();
        if (body == null) return;
        response.getBody().readAll(sink);
        sink.flush();
    }
}
