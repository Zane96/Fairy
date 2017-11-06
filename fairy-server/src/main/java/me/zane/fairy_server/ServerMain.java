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
