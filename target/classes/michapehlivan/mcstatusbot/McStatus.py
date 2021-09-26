import McServer
import socket

socket = socket.socket()
socket.connect(("localhost", 6000))

#check if the server is online
server = McServer.McServer("ip of server")

#loop for recieving data requests and sending response
while(True):
    request = int.from_bytes(socket.recv(1024), "big")

    if(server.getState()):
        server.updateData()

    if(request == "-1"):
        socket.close()
        break
    else:
        socket.sendall((str(server.data[request](server)) + "\n").encode())
