"""
Write code for your launcher here.

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
        print("INVALID MASTER")
        exit()
    else:
        master_port = int(master_port)
        if master_port<1024 or master_port>65535:
            print("INVALID MASTER")
            exit()
    # (2) Check whether it contains the invalid records
    records_list = []
    for i in range(len(file_content) - 1):
        line_content = file_content[i + 1].strip("\n")
        if not line_content:
            print("INVALID MASTER")
            exit()
        line_list = line_content.split(",")
        domain = line_list[0]
        port = line_list[1]
        # Check the port's validity
        if port.isdecimal() == False:
            print("INVALID MASTER")
            exit()
        else:
            port = int(port)
            if port < 1024 or port > 65535:
                print("INVALID MASTER")
                exit()
        # Check the domain's validity
        if check_valid_hostname(domain) == False:
            print("INVALID MASTER")
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
                    print("INVALID MASTER")
                    exit()


def divide_master_configuration(master_file, objective_directory):

    # Firstly, read the master configuration file
    file_description = open(master_file, "r")
    file_content = file_description.readlines()
    port = file_content[0].strip("\n")
    records_list = []
    for i in range(len(file_content)-1):
        record = file_content[i+1].strip("\n")
        hostname = record.split(",")[0]
        port = int(record.split(",")[1])
        record_content = [hostname, port]
        records_list.append(record_content)

    # Secondly, create the configuration file for the root
    root_filename = "root.conf"
    root_file = open(f"{objective_directory}/{root_filename}", "w")
    root_file.writelines(f"{port}\n")
    root_file.close()
    #Begin to divide all the root name into this file
    root_file = open(f"{objective_directory}/{root_filename}", "a")
    root_name_list = []
    # Get the root name
    for i in range(len(records_list)):
        root_name = records_list[i][0].split(".")[-1]
        if root_name not in root_name_list:
            root_name_list.append(root_name)
    # Begin to write the root into the file
    for i in range(len(root_name_list)):
        root_file.writelines(f"{root_name_list[i]},{port+i+1}\n")
    root_file.close()

    # Thirdly, divide all the tld domain name into according configuration file
    TLD_list = []
    for i in range(len(root_name_list)):
        temporary_TLD_list = []
        root_name = root_name_list[i]
        # Get the root port's number
        root_file = open(f"{objective_directory}/{root_filename}", "r")
        root_file_content = root_file.readlines()
        for x in range(len(root_file_content)):
            root_info = root_file_content[x].strip("\n").split(",")
            if root_name == root_info[0]:
                root_port = root_info[1]
        # Begin to write all the TLD domain into the according file
        TLD_filename = f"{root_name}.conf"
        TLD_filepath = Path(f"{objective_directory}/{TLD_filename}")
        # Condition that the file is not existing
        if TLD_filepath.exists() == False:
            TLD_file = open(f"{objective_directory}/{TLD_filename}", "w")
            TLD_file.writelines(f"{root_port}\n")
            TLD_file.close()
        # Search for all the records which have the according root name
        for x in range(len(records_list)):
            domain_name = records_list[x][0]
            if root_name == domain_name.split(".")[-1]:
                TLD = f"{domain_name.split('.')[-2]}.{domain_name.split('.')[-1]}"
                if TLD not in TLD_list:
                    TLD_list.append(TLD)
                if TLD not in temporary_TLD_list:
                    temporary_TLD_list.append(TLD)
        # Write all the TLD into the file
        for x in range(len(temporary_TLD_list)):
            TLD_file = open(f"{objective_directory}/{TLD_filename}", "a")
            TLD_file.writelines(f"{temporary_TLD_list[x]},{int(root_port)+(x+1)*1000}\n")
            TLD_file.close()

    # Fourthly, divide all the auth name into the according configuration file
    for i in range(len(TLD_list)):
        auth_list = []
        root_name = TLD_list[i].split(".")[-1]
        TLD_name = TLD_list[i]
        # Get the TLD port's number
        TLD_file = open(f"{objective_directory}/{root_name}.conf", "r")
        TLD_file_content = TLD_file.readlines()
        for x in range(len(TLD_file_content)):
            if TLD_list[i] == TLD_file_content[x].strip("\n").split(",")[0]:
                TLD_port = int(TLD_file_content[x].strip("\n").split(",")[1])
        # Get the TLD domain
        auth_filename = f'{TLD_list[i].split(".")[-2]}_{TLD_list[i].split(".")[-1]}.conf'
        auth_filepath = Path(f"{objective_directory}/{auth_filename}")
        # Condition that the file is not existing
        if auth_filepath.exists() == False:
            auth_file = open(f"{objective_directory}/{auth_filename}", "w")
            auth_file.writelines(f"{int(TLD_port)}\n")
            auth_file.close()
        # Search for all the records which have the according TLD name
        for x in range(len(records_list)):
            domain_name = records_list[x][0]
            if TLD_name == ".".join(domain_name.split(".")[-2:]):
                auth_list.append([domain_name, records_list[x][1]])
        # Write all the auth into the file
        for x in range(len(auth_list)):
            auth_file = open(f"{objective_directory}/{auth_filename}", "a")
            auth_file.writelines(f"{auth_list[x][0]},{auth_list[x][1]}\n")
            auth_file.close()


def main(args: list[str]) -> None:
    # Firstly, check whether the number of arguments is proper
    if len(args) != 2:
        print("INVALID ARGUMENTS")
        exit()

    # Secondly, check whether the master file is existing
    master_file_path = Path(args[0])
    if master_file_path.exists() == False:
        print("INVALID MASTER")
        exit()

    # Thirdly, check whether the directory is existing and check its permission
    # Check whether the directory is existing
    directory_path = Path(args[1])
    if directory_path.exists() == False:
        print("NON-WRITABLE SINGLE DIR")
        exit()
    # Check the directory's permission
    else:
        directory_stat = directory_path.stat()
        if not (directory_stat.st_mode & 0o0400) or not (directory_stat.st_mode & 0o0200):
            print("NON-WRITABLE SINGLE DIR")
            exit()

    # Fourthly, check whether the "master" configuration is invalid
    check_master_configuration(args[0])

    # Fifthly, begin to divide the records to different "single" configuration
    divide_master_configuration(args[0], args[1])


if __name__ == "__main__":
    main(argv[1:])
else:
    main(argv[1:])
