# Fairy

Fairy is an easy debug tool which will allow developer to use [adb logcat](https://developer.android.com/studio/command-line/logcat.html?hl=zh-cn#outputFormat) command to view Android system log on your Android phone instead of on your PC.You can scan the system log information anywhere with your Android phone which is no need to root.

​                          ![](/screenshot/Screenshot_1.png)              ![](/screenshot/Screenshot_2.png)

​                         ![](/screenshot/Screenshot_3.png)              ![](/screenshot/Screenshot_4.png)

## Getting Start

Fairy support Android API `21+`. There are two ways to choose.

+ Download

1. Download the `project`.

2. Cd [project path]/pc in the terminal.

3. Configured adb environment In the terminal.

4. + MacOS/Linux: Run pusher.sh with `sh pusher.sh`command in terminal.

   + Windows: Run these commands

     ```shell
     //change the ${dir} to the real path
     adb push ${dir}/server.dex  /data/local/tmp
     adb push ${dir}/launcher.sh  /data/local/tmp
     adb push ${dir}/libfairy.so  /data/local/tmp
     adb shell /data/local/tmp/launcher.sh
     ```

5. Install the APK in the [project path]/pc

+ Import

1. Import project in AndroidStudio.

2. + MacOS/Linux: Run `./gradlew -p fairy-server runService`.

   + Windows: First Run `gradlew -p fairy-server moveSo`. Then run these commands: 

     ```shell
     //change the ${dir} to the real path
     adb push ${dir}/server.dex  /data/local/tmp
     adb push ${dir}/launcher.sh  /data/local/tmp
     adb push ${dir}/libfairy.so  /data/local/tmp
     adb shell /data/local/tmp/launcher.sh
     ```

3. Run fairy-client module or install APK directly.

Fairy default use armeabi .so

## TODO

- Fit Android low-end mobile phone.Some low-end mobile phone like MI2 and MI3 may kill the Fairy-Server when USB is break up or may can't support `logcat -t "[time]"`command. So these phones may not use Fairy because Fairy uses feed-stream which is based on timeline.
- ~Add log grep feature.~
- ~Optimize the view to display log.~
- ~Optimize the view to display item~
- ~Data persistence by SQL.~
- ~Add floating window to display data.~

## Fix

+ Fit Android8.0+ of the Window Type permission limit.
+ Update readme to notice the Windows Programmer how to use it.

## About More

I have completed the code refactoring in this release (`v2.0.0-alpha`) with [Android Architecture Components](https://developer.android.google.cn/topic/libraries/architecture/guide.html)

![aritecture](/screenshot/aritecture.png)

## FAQ

zanebot96@gmail.com

You can submit an issue for me too !~

​​                                                                     ![](/screenshot/icon.png)

## License

```
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
```

