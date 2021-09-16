from mcstatus import MinecraftServer
import socket

socket = socket.socket()
socket.connect(("localhost", 6000))

try:
    server = MinecraftServer("86.94.29.113", 25565)
    status = server.status()
    online = True
except:
    online = False

def getState():
    return online

def getOnline():
    return status.players.online

def getMax():
    return status.players.max

def getName():
    return status.description

def getVersion():
    return status.version.name

def getPing():
    return status.latency

def getPlayers():
    names = []
    for i in status.players.sample:
        names.append(i.name)
    return names

data = [getState(), getOnline(), getMax(), getName(), getVersion(), getPing(), getPlayers()]

while(True):
    request = int.from_bytes(socket.recv(1024), "big")
    if(request == "0"):
        socket.close()
        break
    else:
        socket.sendall((str(data[request]) + "\n").encode())
