#!/bin/bash

LAUNCHERPID=`/opt/SUNWrtjv/bin/jps | grep Launcher* | cut -d' ' -f1`

echo $LAUNCHERPID

/opt/tsv/bin/dmonitor $LAUNCHERPID 120

/opt/tsv/bin/tsv log$LAUNCHERPID.jrt3