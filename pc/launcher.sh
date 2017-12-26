#!/system/bin/sh

pkill -f fairy
rootDir=/data/local/tmp

#exec
echo "exec~~~"
path=${rootDir}/libfairy.so
fairy=${rootDir}/fairy

rm -rf ${fairy}
cp ${path} ${fairy}

exec ${fairy}