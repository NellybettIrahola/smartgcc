#!/bin/bash

mvn clean compile javafx:jlink
echo "target/smartgcc.zip - contains a 'portable' copy of SmartGCC that is platform-specific (windows, linux, or mac)"
