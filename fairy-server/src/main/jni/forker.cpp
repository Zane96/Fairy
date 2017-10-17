//
// Created by 徐志 on 2017/9/30.
//
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

using namespace std;

#define TAG "Fairy"


static int load() {

    //Java文件的执行参数
    char *args[] = {"app_process",
        "-Djava.class.path=/data/local/tmp/server.dex",
        "/data/local/tmp",
        "--nice-name=fairy",
        "me.zane.fairy_server.ServerMain",
    NULL};

    pid_t pid = fork();
    if (pid < 0) {
        return -1;
    }

    //LOGD("fork");
    switch (pid) {
         case -1:
              //LOGE("cannot fork");
              return -1;
         case 0:
              //child
              //LOGD("success fork");
              break;
         default:
              //parent
              return pid;
    }

    return execvp(args[0], args);
}

//在shell脚本中已经kill掉了旧进程
//这里不再判断是否还存在旧进程
int main(int argc, char **argv) {
    return load();
}

