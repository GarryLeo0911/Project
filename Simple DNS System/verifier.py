"""
Write code for your verifier here.

You may import library modules allowed by the specs, as well as your own other modules.
"""
from sys import argv
from pathlib import Path


def check_valid_hostname(hostname):
    if not hostname:
        return False
    # Split the hostname by periods to check each part
    parts = hostname.split(".")
    if len(parts)>=3:
        partA = parts[-1]
        partB = parts[-2]
        partC = ".".join(parts[:-2])

        # Check partA
        if not partA:
            return False
        if not 1 <= len(partA) <= 63:
            return False
        for char in partA:
            if not char.isalnum() and char != "-":
                return False

        # Check partB
        if not partB:
            return False
        if not 1 <= len(partB) <= 63:
            return False
        for char in partB:
            if not char.isalnum() and char != "-":
                return False

        #Check partC
        if not partC:
            return False
        if not 1 <= len(partC) <= 63:
            return False
        for char in partB:
            if not char.isalnum() and char != "-" and char != ".":
                return False

    if len(parts) < 3:
        return False

    return True


def check_valid_domain_name(hostname):
    if not hostname:
        return False
    # Split the hostname by periods to check each part
    parts = hostname.split(".")
    if len(parts)>=3:
        partA = parts[-1]
        partB = parts[-2]
        partC = ".".join(parts[:-2])

        # Check partA
        if not partA:
            return False
        if not 1 <= len(partA) <= 63:
            return False
        for char in partA:
            if not char.isalnum() and char != "-":
                return False

        # Check partB
        if not partB:
            return False
        if not 1 <= len(partB) <= 63:
            return False
        for char in partB:
            if not char.isalnum() and char != "-":
                return False

        #Check partC
        if not partC:
            return False
        if not 1 <= len(partC) <= 63:
            return False
        for char in partB:
            if not char.isalnum() and char != "-" and char != ".":
                return False


    elif len(parts) == 2:
        partA = parts[1]
        partB = parts[0]

        # Check partA
        if not partA:
            return False
        if not 1 <= len(partA) <= 63:
            return False
        for char in partA:
            if not char.isalnum() and char != "-":
                return False

        # Check partB
        if not partB:
            return False
        if not 1 <= len(partB) <= 63:
            return False
        for char in partB:
            if not char.isalnum() and char != "-":
                return False

    elif len(parts) == 1:
        partA = parts[0]
        # Check partA
        if not partA:
            return False
        if not 1 <= len(partA) <= 63:
            return False
        for char in partA:
            if not char.isalnum() and char != "-":
                return False
    return True


def check_single_file_validity(directory_name, filename):
    file = open(f"{directory_name}/{filename}", "r")
    file_content = file.readlines()

    server_port = int(file_content[0].strip())
    if server_port<1024 or server_port>65535:
        print("invalid single")
        exit()

    # Read the single file's content
    records = []
    for i in range(len(file_content)-1):
        record = file_content[i+1].strip("\n").split(",")
        records.append(record)

    # Check the domain name and port's validity
    for i in range(len(records)):
        domain_name = records[i][0]
        port = int(records[i][1])
        if check_valid_domain_name(domain_name) == False:
            print("invalid single")
            exit()
        if port<1024 or port>65535:
            print("invalid single")
            exit()


def check_master_configuration(master_file):
    # Open the file
    file_description = open(master_file, "r")
    # Read the file
    file_content = file_description.readlines()
    # Delete the "\n"
    for i in range(len(file_content)):
        file_content[i] = file_content[i].strip("\n")

    # Check whether the file's content
    # (1)Check whether the first line has a valid port
    master_port = file_content[0]
    if master_port.isdecimal() == False:
        print("invalid master")
        exit()
    else:
        master_port = int(master_port)
        if master_port<1024 or master_port>65535:
            print("invalid master")
            exit()
    # (2) Check whether it contains the invalid records
    records_list = []
    for i in range(len(file_content) - 1):
        line_content = file_content[i + 1].strip("\n")
        if not line_content:
            print("invalid master")
            exit()
        line_list = line_content.split(",")
        domain = line_list[0]
        port = line_list[1]
        # Check the port's validity
        if port.isdecimal() == False:
            print("invalid master")
            exit()
        else:
            port = int(port)
            if port < 1024 or port > 65535:
                print("invalid master")
                exit()
        # Check the domain's validity
        if check_valid_hostname(domain) == False:
            print("invalid master")
            exit()
        # Add the line list to the records_list
        records_list.append(line_list)
    # (3) Check whether the records are contradict
    for i in range(len(records_list)):
        # Get the domain name
        domain_name = records_list[i][0]
        #Check whether the remaining list have the same domain name with this one
        for y in range(len(records_list)):
            if records_list[y][0] == domain_name:
                #Check whether their port is different
                if records_list[i][1] != records_list[y][1]:
                    print("invalid master")
                    exit()


def main(args: list[str]) -> None:
    # Firstly, check whether the number of arguments is proper
    if len(args) != 2:
        print("invalid arguments")
        exit()

    # Secondly, check whether the master file is existing
    master_file_path = Path(args[0])
    if master_file_path.exists() == False:
        print("invalid master")
        exit()

    # Thirdly, check whether the directory is existing and check its permission
    # Check whether the directory is existing
    directory_path = Path(args[1])
    if directory_path.exists() == False or directory_path.is_dir() == False:
        print("singles io error")
        exit()
    # Check the directory's permission
    else:
        directory_stat = directory_path.stat()
        if not (directory_stat.st_mode & 0o0400):
            print("singles io error")
            exit()

    # Fourthly, check whether the "master" configuration is invalid
    check_master_configuration(args[0])

    # Fifthly, get all the files' names inside the directory
    file_name_list = []
    for file in directory_path.iterdir():
        if file.is_file():
            file_name_list.append(file.name)

    # Sixthly, check whether the "single" file is valid
    for file_name in file_name_list:
        check_single_file_validity(args[1], file_name)

    # Seventhly, get the content of the master file
    master_file = open(args[0], "r")
    master_file_content = master_file.readlines()
    records = []
    for i in range(len(master_file_content)-1):
        record = master_file_content[i+1].strip("\n").split(",")
        records.append(record)

    # Eighthly, compare the single files' whole hostname content with the master file
    for i in range(len(file_name_list)):
        file = open(f"{args[1]}/{file_name_list[i]}", "r")
        file_content = file.readlines()
        server_port = file_content[0].strip("\n")
        single_file_records = []
        for x in range(len(file_content)-1):
            single_file_record = file_content[x+1].strip("\n").split(",")
            # Check whether the records have the same port with the server port
            if server_port == single_file_record[1]:
                print("neq")
                exit()
            single_file_records.append(single_file_record)
        for x in range(len(single_file_records)):
            domain_name = single_file_records[x][0]
            # For auth server
            if len(domain_name.split(".")) >= 3:
                domain_port = single_file_records[x][1]
                equality = False
                for y in range(len(records)):
                    if records[y][0] == single_file_records[x][0] and records[y][1] == domain_port:
                        equality = True
                if equality == False:
                    print("neq")
                    exit()
            # For TLD server
            if len(domain_name.split(".")) == 2:
                equality = False
                domain_port = single_file_records[x][1]
                # Check whether the other file has the same sever port
                for y in range(len(file_name_list)):
                    file = open(f"{args[1]}/{file_name_list[y]}", "r")
                    file_content = file.readlines()
                    server_port = file_content[0].strip("\n")
                    if domain_port == server_port:
                        equality = True
                if equality == False:
                    print("neq")
                    exit()
            # For root server
            if len(domain_name.split(".")) == 1:
                equality = False
                domain_port = single_file_records[x][1]
                # Check whether the other file has the same sever port
                for y in range(len(file_name_list)):
                    file = open(f"{args[1]}/{file_name_list[y]}", "r")
                    file_content = file.readlines()
                    server_port = file_content[0].strip("\n")
                    if domain_port == server_port:
                        equality = True
                if equality == False:
                    print("neq")
                    exit()


    # Check whether they have the same server port
    for i in range(len(file_name_list)):
        file = open(f"{args[1]}/{file_name_list[i]}", "r")
        file_content = file.readlines()
        server_port = file_content[0].strip("\n")
        for x in range(len(file_name_list)):
            if i != x:
                contrast_file = open(f"{args[1]}/{file_name_list[x]}", "r")
                contrast_file_content = contrast_file.readlines()
                contrast_port = contrast_file_content[0].strip("\n")
                if contrast_port == server_port:
                    print("neq")
                    exit()
    print("eq")
    exit()



if __name__ == "__main__":
    main(argv[1:])
