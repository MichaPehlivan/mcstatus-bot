from mcstatus import MinecraftServer
import socket

socket = socket.socket()
socket.connect(("localhost", 6000))

server = MinecraftServer("86.94.29.113", 25565)

#status = server.status()

def getOnline():
    return 2

def getMax():
    return 20

def getName():
    return "Stalin's server"

def getVersion():
    return "1.17"

def getPing():
    return 5.87

def getPlayers():
    return ["AhYeDaddyStalin", "Michabeest"]

data = {"online" : getOnline(), "max" : getMax(), "name" : getName(), "version" : getVersion(), "ping" : getPing(), "players" : getPlayers()}

while(True):
    request = socket.recv(1024).decode()
    if(request == "0"):
        socket.close()
        break
    else:
        socket.sendall((str(data[request]) + "\n").encode())
