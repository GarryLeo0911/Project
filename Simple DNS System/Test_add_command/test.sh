#!/bin/bash
# Execute the program
coverage run --append server.py tests/sample.conf &

#send add command to the server
sleep 2
printf "!ADD github.com 1752" | nc localhost 1024
printf "!ADD vscode.com 4251" | nc localhost 1024

#Compare whether the expect file is same with the generating file
if cmp -s temporary_file.conf Test_add_command/expect_file.conf; then
  echo "ADD command test failed"
fi

#Terminate the connection
printf "!EXIT" | nc localhost 1024
