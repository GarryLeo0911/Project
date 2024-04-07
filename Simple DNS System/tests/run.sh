#!/bin/bash

cd ..
# earse previous coverage
coverage erase

# Run the test bash I have created
bash Test_add_command/test.sh
bash Test_delete_command/test.sh
bash Test_argument_invalid/test.sh
bash Test_when_conf_isinvalid/test.sh
bash Test_when_conf_isvalid/test.sh

coverage report -m
