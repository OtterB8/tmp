#!/bin/bash
genpath="./gen-java/com"
path="../src/main/java"
thrift -r --gen java kvstore.thrift
cp -a $genpath $path
