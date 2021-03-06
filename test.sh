#!/bin/bash
killall java
echo "done killing all existing java servers"
make run_alllocal
echo "server created"

sleep 2
python remote_test.py seq buy > /dev/null 2>&1
sleep 2
while pgrep -u root python > /dev/null; do sleep 1; done
echo "done with seq buy"

python remote_test.py seq search > /dev/null 2>&1
sleep 2
while pgrep -u root python > /dev/null; do sleep 1; done
echo "done with seq search"

python remote_test.py seq lookup > /dev/null 2>&1
sleep 2
while pgrep -u root python > /dev/null; do sleep 1; done
echo "done with seq lookup"
