//
// Created by Zane on 2017/9/30.
//
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <android/log.h>

#define LOGD(...) (__android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__))
#define LOGW(...) (__android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__))
#define LOGE(...) (__android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__))

#define TAG "Fairy"

static int server() {
    pid_t pid = fork();
    if (pid < 0) {
        return -1;
    } else if (pid == 0) {
        LOGD("child fork");
    } else {
        LOGD("parent");
        return pid;
    }

    //Java文件的执行参数
    char *args[] = {"app_process",
        "-Djava.class.path=/data/local/tmp/server.dex",
        "/data/local/tmp",
        "--nice-name=fairy",
        "me.zane.fairy_server.ServerMain",
    NULL};

    LOGD("exec");
    //吊起来就不会返回
    return execvp(args[0], args);
}

//守护服务进程
static void signal_handler(int signal) {
    LOGD("handle %d %d", signal, getpid());
    if (signal == SIGCHLD) {
        int status;
        pid_t pid;
        for (;;) {
            pid = waitpid(-1, &status, 0);
            LOGD("pid %d", pid);
            if (pid == -1) {
               return;
            }
        }
    }
}

static int loadDaemon() {
    sigset_t new_set;
    sigemptyset(&new_set);

    //先fork一次
    int i = server();
    LOGD("num %d", i);
    if (i <= 0) {
        return -1;
    }

    for (;;) {
        //等待信号量，阻塞
        LOGD("wait1");
        sigsuspend(&new_set);
        LOGD("wait2");
        //pause();
        LOGD("fork again");
        if (server() <= 0) {
            break;
        }
    }
}

//在shell脚本中已经kill掉了旧进程
//这里不再判断是否还存在旧进程
int main(int argc, char **argv) {
    LOGD("%d %s", argc, argv[0]);
    //注册SIGCHLD的信号处理函数
    signal(SIGCHLD, signal_handler);

    //fork出第一个sys内陷的进程
    switch (fork()) {
        case -1:
            perror("cannot fork");
            return -1;
        case 0:
            LOGD("fork daemon");
            break;
        default:
            LOGD("grandfather1");
            //sleep(10);
            LOGD("grandfather2");
            //_exit(0);
            for(;;){}
    }

    return loadDaemon();
}

