#!/bin/bash

rm -r src/main/libs
rm -r src/main/obj
cd src/main/jni
cd ~
source .bash_profile
cd -
ndk-build