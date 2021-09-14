from mcstatus import MinecraftServer
import socket

socket = socket.socket()
socket.connect(("localhost", 6000))

server = MinecraftServer("ip of server", 25565)

#status = server.status()

def getOnline():
    return 2

def getMax():
    return 20

def getName():
    return "server name"

def getVersion():
    return "1.17"

def getPing():
    return 5.87

def getPlayers():
    return ["Player1", "Player2"]

data = [getOnline(), getMax(), getName(), getVersion(), getPing(), getPlayers()]

while(True):
    request = int.from_bytes(socket.recv(1024), "big")
    if(request == "0"):
        socket.close()
        break
    else:
        socket.sendall((str(data[request]) + "\n").encode())
