# Fairy

Fairy是一个允许开发者在Android手机上使用 [adb logcat](https://developer.android.com/studio/command-line/logcat.html?hl=zh-cn#outputFormat) 命令 查看Android系统日志的调试工具。开发者可以在任何地方使用未Root的Android手机浏览系统日志信息。

​                          ![](/screenshot/Screenshot_1.png)              ![](/screenshot/Screenshot_2.png)

​                         ![](/screenshot/Screenshot_3.png)              ![](/screenshot/Screenshot_4.png)

## 使用

Fairy支持Android API 21以上的版本。这里有两种方式可以供您去选择并使用Fairy。

+ 下载

1. 在`Github`上下载项目

2. 在命令行中`cd`进入项目的根地址

3. 配置好电脑中的adb环境

4. + MacOS/Linux: 执行`sh pusher.sh`命令

   + Windows: 执行以下命令

     ```shell
     //change the ${dir} to the real path
     adb push ${dir}/server.dex  /data/local/tmp
     adb push ${dir}/launcher.sh  /data/local/tmp
     adb push ${dir}/libfairy.so  /data/local/tmp
     adb shell sh /data/local/tmp/launcher.sh
     ```

5. 安装[project path]/pc目录里面的apk文件

+ 导入

1. 将项目导入AndroidStudio.

2. + MacOS/Linux: 执行`./gradlew -p fairy-server runService.`

   + Windows: 首先执行`gradlew -p fairy-server moveSo`，然后执行以下命令：

     ```shell
     //change the ${dir} to the real path
     adb push ${dir}/server.dex  /data/local/tmp
     adb push ${dir}/launcher.sh  /data/local/tmp
     adb push ${dir}/libfairy.so  /data/local/tmp
     adb shell sh /data/local/tmp/launcher.sh
     ```

3. 运行fairy-client模块或者直接安装apk

如果你在终端中看到`exec~~~`这样的Log信息，并且能在手机中找到命名为`fairy`的服务端进程（你可以用这个命令去查看：`adb shell ps | grep fairy`，那么表示服务端已经成功的运行起来了。

Fairy默认使用armeabi类型的so库，如有需要更改的话，可以在`fairy-server`的`build.gradle`里面的`moveSo`任务中去修改。

## 问题

1. 如果在运行shell脚本的过程中遇到`can't execute: Permission denied`这个问题的话，您可以使用`chmod`这个命令去提升文件的执行权限。
2. 如果您是在Windows环境下开发，并且遇到脚本执行出现大量`not found`的日志信息，请使用[Dos2Unix](http://www.gnss.help/2017/07/24/dos2unix-install-usage/)工具将`launcher.sh`文件的编辑格式进行转换。

## TODO

+ 适配
+ ~增加grep功能~
+ ~优化展示log的页面~
+ ~优化item的展示页面~
+ ~增加数据持久~
+ ~增加浮动框去展示数据~

## 修复

+ 修复Android8.0+对于浮动窗口类型的限制
+ 修改readme去告诉Windows开发环境的开发者怎么去使用Fairy

## 更多

本项目已经使用[Android Architecture Components](https://developer.android.google.cn/topic/libraries/architecture/guide.html)完成了客户端的代码重构

![aritecture](/screenshot/aritecture.png)

## FAQ

zanebot96@gmail.com

+ 欢迎加入Fairy交流群，群号码：577953847
+ 如果您在使用过程中遇到任何问题，您也可以提交一个issue给我。
+ 同时欢迎各位开发者能帮助我一起优化这个项目，您可以提交任意的request给我！

![](/screenshot/icon.png)

## 协议

```
    Copyright 2017 Zane

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```



