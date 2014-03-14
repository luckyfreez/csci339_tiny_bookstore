#!/bin/bash -x
killall java
echo "done killing all existing java servers"
make run_alllocal
echo "server created"
python remote_test.py seq buy > /dev/null 2>&1
echo "done with seq buy"
python remote_test.py seq search > /dev/null 2>&1
echo "done with seq search"
python remote_test.py seq lookup > /dev/null 2>&1
echo "done with seq lookup"
