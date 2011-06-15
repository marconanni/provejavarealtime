#!/bin/bash

LAUNCHERPID=`/opt/SUNWrtjv/bin/jps | grep Launcher* | cut -d' ' -f1`

echo $LAUNCHERPID

ps -ALlec | grep $LAUNCHERPID

cat -