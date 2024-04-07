"""
Write code for your server here.

You may import library modules allowed by the specs, as well as your own other modules.
"""
from sys import argv
from pathlib import Path
import socket


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


def check_the_configuration(filename):
    with open(filename, "r") as file:
        file_content = file.readlines()

        # (1)Check the firstline of the coonfiguration file
        server_port = file_content[0].strip("\n")
        if server_port != "1024" and server_port != "1025" and server_port != "1026" and server_port != "1027" and server_port != "1028":
            print("INVALID CONFIGURATION")
            return False

        # (2)Read the remaining lines, and split them into list.
        # (3)Check whether the port and domain name is valid
        records_list = []
        for i in range(len(file_content)-1):
            line_content = file_content[i+1].strip("\n")
            line_list = line_content.split(",")
            domain = line_list[0]
            port = line_list[1]
            #Check the port's validity
            if port.isdecimal() == False:
                print("INVALID CONFIGURATION")
                return False
            else:
                port = int(port)
                if port<1 or port>65535:
                    print("INVALID CONFIGURATION")
                    return False
            #Check the domain's validity
            if check_valid_hostname(domain) == False:
                print("INVALID CONFIGURATION")
                return False
            #Add the line list to the records_list
            records_list.append(line_list)

        # (4)Check whether the records are contradict
        for i in range(len(records_list)):
            # Get the domain name
            domain_name = records_list[i][0]
            #Check whether the remaining list have the same domain name with this one
            for y in range(len(records_list)):
                if records_list[y][0] == domain_name:
                    #Check whether their port is different
                    if records_list[i][1] != records_list[y][1]:
                        print("INVALID CONFIGURATION")
                        return False



def search_the_domain_port(domain, records_list):
    for i in range(len(records_list)):
        #print("Begin to search the domain")
        if domain == records_list[i][0]:
            return records_list[i][1]
    return None


def listen_to_recursor(server_port, records_list, file_path):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
        server_port = int(server_port)
        server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        try:
            server.bind(("localhost", server_port))
        except OSError as e:
            pass
        server.listen()

        # At the beginning, copy the original file to a new file
        temporary_file = open("temporary_file.conf", "w")
        orginal_file = open(file_path, "r")
        orginal_file_content = orginal_file.read()
        temporary_file.write(orginal_file_content)
        orginal_file.close()
        temporary_file.close()

        while True:
            # Connect to recursor
            # print("Listen to recursors")
            recursor_socket, recursor_address = server.accept()
            # print("Connect to recursor")
            content = recursor_socket.recv(1024).decode().strip("\n")

            # Check whether the recursor's input is exist
            if content.startswith("!") == False:
                if content == "CLOSE_SERVER":
                    recursor_socket.close()
                    break
                domain_port = search_the_domain_port(content, records_list)
                if domain_port == None:
                    print(f"resolve {content} to NXDOMAIN", flush=True)
                    recursor_socket.sendall(("NXDOMAIN\n").encode())
                    recursor_socket.close()
                    # print("NXDOMAIN")
                elif domain_port.isdecimal():
                    print(f"resolve {content} to {domain_port}", flush=True)
                    recursor_socket.sendall((f"{domain_port}\n").encode())
                    recursor_socket.close()
                    # print(domain_port)

            # Execute the commands
            if content.startswith("!") == True:
                #get the hostname and port
                temporary_list = content.strip("\n").split()
                if len(temporary_list) == 3:
                    hostname = temporary_list[1]
                    port = temporary_list[2]
                if len(temporary_list) == 2:
                    hostname = temporary_list[1]

                # Open the original file again to rewrite it
                original_file = open(file_path, "w")

                # Execute the add command
                if content.startswith("!ADD"):
                    # First, add the hostname if the hostname is not exist
                    if search_the_domain_port(hostname, records_list) == None:
                        # Check whether the domain name is valid
                        if check_valid_hostname(hostname) == True and int(port)>=1 and int(port)<=65535:
                            records_list.append([hostname, port])
                    # Then, change the port if the hostname is existing
                    elif search_the_domain_port(hostname, records_list) != None:
                        # Check whether the domain name is valid
                        if check_valid_hostname(hostname) == True and int(port) >= 1 and int(port) <= 65535:
                            for i in range(len(records_list)):
                                if records_list[i][0] == hostname:
                                    records_list[i][1] = port
                    # Last, write the content into the original file
                    original_file.writelines(f"{server_port}")
                    for i in range(len(records_list)):
                        line_content = f"\n{records_list[i][0]},{records_list[i][1]}"
                        original_file.writelines(line_content)
                    original_file.close()
                    recursor_socket.close()

                # Execute the delete command
                if content.startswith("!DEL"):
                    # Check whether the hostname is existing
                    if search_the_domain_port(hostname, records_list) != None:
                        delete_index = []
                        for i in range(len(records_list)):
                            if hostname == records_list[i][0]:
                                delete_index.append(i)
                        for i in range(len(delete_index)):
                            del records_list[int(delete_index[i])]
                    # Write the content into the original file
                    original_file.writelines(f"{server_port}")
                    for i in range(len(records_list)):
                        line_content = f"{records_list[0]},{records_list[1]}"
                        original_file.writelines(line_content)
                    original_file.close()
                    recursor_socket.close()

                # Execute the exit command
                if len(temporary_list) == 1 and temporary_list[0] == "!EXIT":
                    # First close the server
                    recursor_socket.close()
                    # Then, recover the file
                    temporary_file = open("temporary_file.conf", "r")
                    temporary_file_content = temporary_file.read()
                    original_file = open(file_path, "w")
                    original_file.write(temporary_file_content)
                    original_file.close()
                    temporary_file.close()
                    # Delete the temporary file
                    path = Path("temporary_file.conf")
                    path.unlink()
                    break
        server.close()


def main(args: list[str]) -> None:
    # Check whether the argument's number is right
    if len(args) != 1:
        print("INVALID ARGUMENTS")
        exit()

    # Check whether the configuration file is exist
    configuration_path = Path(args[0])
    if configuration_path.exists() == False:
        print("INVALID CONFIGURATION")
        exit()

    # Check whether the configuration file is valid
    if check_the_configuration(args[0]) == False:
        exit()

    # Read the configuration file:
    file = open(args[0], "r")
    file_content = file.readlines()

    # Get the server's port
    server_port = int(file_content[0].strip("\n"))

    # Combine all the records into a list
    records_list = []
    for i in range(len(file_content)-1):
        line_content = file_content[i+1].strip("\n")
        line_list = line_content.split(",")
        domain = line_list[0]
        port = line_list[1]
        record = [domain, port]
        records_list.append(record)

    # Begin to listen to recursor
    listen_to_recursor(server_port, records_list, args[0])


if __name__ == "__main__":
    main(argv[1:])
