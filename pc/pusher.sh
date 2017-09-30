#!/bin/bash

echo $PATH
#取脚本所在目录
dir=$(cd `dirname $0`; pwd)

adb push $dir/server.dex  /data/local/tmp
adb push $dir/launcher.sh  /data/local/tmp
adb push $dir/libfairy.so  /data/local/tmp

# 不输出 后台执行
adb shell /data/local/tmp/launcher.sh
echo 'finish'