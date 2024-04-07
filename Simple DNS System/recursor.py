"""
Write code for your recursor here.

You may import library modules allowed by the specs, as well as your own other modules.
"""
from sys import argv
import socket
import time

#make a function to query DNS server and return the response
def connect_to_server(port, query_things):
    try:
        server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server.connect(("localhost", port))
        server.sendall(query_things.encode())
        reply = server.recv(1024).decode()
        #print("connect to server")
        return reply
    except Exception:
        return None


#make a function to check whether the hostname is valid
def check_valid_hostname(hostname):
    # Split the hostname by periods to check each part
    parts = hostname.split(".")

    # Check if there are at least three parts in the hostname
    if len(parts) < 3:
        return False

    for part in parts:
        # Check if the part is empty or starts or ends with a hyphen
        if not part or part.startswith("-") or part.endswith("-"):
            return False

        # Check the length of the part
        if not 1 <= len(part) <= 63:
            return False

        # Check if each character is alphanumeric or a hyphen
        for char in part:
            if not char.isalnum() and char != "-":
                return False

    return True


#make a function to resolve the domain name
def resolve_domain(domain, root_port, time_out):
    # 1. connect to root server
    root_query_thing = f'{domain.split(".")[-1]}\n'
    reply_from_root = connect_to_server(root_port, root_query_thing)

    while True:
        #print("get the reply from root server")
        start_time = time.time()
        if reply_from_root == None:
            print("FAILED TO CONNECT TO ROOT")
            exit()
        if reply_from_root.strip("\n") == "NXDOMAIN":
            print("NXDOMAIN")
            return
        else:
            TLD_port = int(reply_from_root)

            # 2. connect to TLD server
            TLD_query_thing = f'{".".join(domain.split(".")[-2:])}\n'
            #print(TLD_port)
            reply_from_TLD = connect_to_server(TLD_port, TLD_query_thing)
            while True:
                #print("get reply from TLD server")
                if reply_from_TLD == None:
                    print("FAILED TO CONNECT TO TLD")
                    exit()
                if reply_from_TLD.strip("\n") == "NXDOMAIN":
                    print("NXDOMAIN")
                    return
                else:
                    authoritative_port = int(reply_from_TLD)

                    # 3. connect to authoriative server
                    authoritative_query_thing = f"{domain}\n"
                    #print(authoritative_port)
                    reply_from_authoritative = connect_to_server(authoritative_port, authoritative_query_thing)
                    while True:
                        #print("get reply from authoritative server")
                        end_time = time.time()
                        time_interval = end_time - start_time
                        if time_interval > time_out:
                            print("NXDOMAIN")
                            return
                        if reply_from_authoritative == None:
                            print("FAILED TO CONNECT TO AUTH")
                            exit()
                        if reply_from_authoritative.strip("\n") == "NXDOMAIN":
                            print("NXDOMAIN")
                            return
                        else:
                            print(reply_from_authoritative.strip("\n"))
                            return




def main(args: list[str]) -> None:
    # TODO
    root_port = int(args[0])
    time_out = float(args[1])
    if root_port<1024 or root_port>65535:
        print("INVALID ARGUMENTS")
        return
    try:
        while True:
            #print("start the input")
            domain = input()
            if check_valid_hostname(domain) == False:
                print("INVALID")
                continue
            if domain == "":
                print("end")
                break
            else:
                resolve_domain(domain, root_port, time_out)
    except EOFError:
        return



if __name__ == "__main__":
    if len(argv) != 3:
        print("INVALID ARGUMENTS")
    else:
        main(argv[1:])