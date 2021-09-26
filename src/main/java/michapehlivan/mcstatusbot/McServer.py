from mcstatus import MinecraftServer

class McServer:

    def __init__(self, ip):
        global serverIp
        serverIp = ip
        self.updateData()

    def updateData(self):
        global server, status, online
        try:
            server = MinecraftServer(serverIp, 25565)
            status = server.status()
            online = True
        except:
            online = False

    #data getters
    def getState(self):
        return online

    def getOnline(self):
        return status.players.online

    def getMax(self):
        return status.players.max

    def getName(self):
        return status.description["text"]

    def getVersion(self):
        return status.version.name

    def getPing(self):
        return status.latency

    def getPlayers(self):
        names = []
        if(status.players.sample != None):
            for i in status.players.sample:
                names.append(i.name)
        return names

    data = [getState, getOnline, getMax, getName, getVersion, getPing, getPlayers]