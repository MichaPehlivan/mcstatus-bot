from mcstatus import MinecraftServer
import socket

socket = socket.socket()
socket.connect(("localhost", 6000))

#check if the server is online
try:
    server = MinecraftServer("ip of server", 25565)
    online = True
except:
    online = False

#data getters
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
    if(status.players.sample != None):
        for i in status.players.sample:
            names.append(i.name)
    return names

data = [getState, getOnline, getMax, getName, getVersion, getPing, getPlayers]

#loop for recieving data requests and sending response
while(True):
    if(online):
        status = server.status()

    request = int.from_bytes(socket.recv(1024), "big")
    if(request == "-1"):
        socket.close()
        break
    else:
        socket.sendall((str(data[request]()) + "\n").encode())