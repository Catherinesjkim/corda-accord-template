#!/usr/bin/env bash

../../../node_modules/.bin/cicero parse --template $1 --sample $2 --out /tmp/test2.txt > /tmp/cicero.log 2>&1

cat /tmp/test2.txt
