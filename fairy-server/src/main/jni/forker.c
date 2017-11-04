//
// Created by 徐志 on 2017/9/30.
//
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <android/log.h>

#define LOGD(...) (__android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__))
#define LOGW(...) (__android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__))
#define LOGE(...) (__android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__))

#define TAG "Fairy"

static int load() {

    //Java文件的执行参数
    char *args[] = {"app_process",
        "-Djava.class.path=/data/local/tmp/server.dex",
        "/data/local/tmp",
        "--nice-name=fairy",
        "me.zane.fairy_server.ServerMain",
    NULL};

    //pid_t pid = fork();
    //LOGD("%d pid %d", pid, getpid());
    //pid_t ppid = fork();
    //LOGD("%d ppid %d", ppid, getpid());
    //if (ppid != 0) {
      //  return 0;
    //}
    //LOGD("start");
    signal(SIGCHLD, SIG_IGN);
    pid_t pid = fork();
    switch (pid) {
         case -1:
              LOGE("cannot fork");
              return -1;
         case 0:
              //child
              LOGD("%d, %d, success fork", pid, getpid());
              break;
         default:
              //parent
              LOGD("%d, %d, parent", pid, getpid());
              return pid;
    }

    return execvp(args[0], args);
}

//在shell脚本中已经kill掉了旧进程
//这里不再判断是否还存在旧进程
int main(int argc, char **argv) {
    LOGD("%d %s", argc, argv[0]);

    return load();
}

