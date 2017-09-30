#!/system/bin/sh

## 杀掉旧服务
pkill -f fairy
## 开启新服务
#app_process -Djava.class.path=/data/local/tmp/launcher.dex /data/local/tmp --nice-name=demigod me.ele.demigod.service.Launcher

rootDir=/data/local/tmp
appProcess=/system/bin/app_process32

#添加app_process32软链接
if [ -x ${appProcess} ]; then
    rm -rf ${rootDir}/app_process
    echo 'exec2'
    ln -s ${appProcess} ${rootDir}/app_process
    echo 'exec3'
    export PATH=${rootDir}:$PATH
fi

#执行
echo "exec~~~"
path=${rootDir}/libfairy.so
fairy=${rootDir}/fairy

rm -rf ${fairy}
cp ${path} ${fairy}

exec ${fairy}