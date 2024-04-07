#!/bin/bash
# Execute the program
coverage run --append server.py Test_when_conf_isinvalid/sample.conf > Test_when_conf_isinvalid/actual.out &

#send domain name to the server
sleep 2
printf "vscode.com" | nc localhost 1024

#Terminate the connection
printf "CLOSE_SERVER" | nc localhost 1024

if cmp -s acutal.out expect.out; then
  echo "test conf file invalid testcase failed"
fi