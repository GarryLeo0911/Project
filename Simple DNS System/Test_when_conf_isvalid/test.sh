#!/bin/bash
# Execute the program
coverage run --append server.py tests/sample.conf > Test_when_conf_isvalid/actual.out &

#send domain name to the server
sleep 2
printf "www.minecraft.net" | nc localhost 1024
printf "vscode.com" | nc localhost 1024
printf "uk" | nc localhost 1024

#Terminate the connection
printf "CLOSE_SERVER" | nc localhost 1024

if cmp -s acutal.out expect.out; then
  echo "test conf file valid testcase failed"
fi