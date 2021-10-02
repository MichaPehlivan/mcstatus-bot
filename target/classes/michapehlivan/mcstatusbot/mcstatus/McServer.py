from mcstatus import MinecraftServer

class McServer:

    def __init__(self, host, ip) -> None:
        try:
            server = MinecraftServer(ip, 25565)
            global serverhost, status, online
            status = server.status()
            serverhost = host
            online = True
        except:
            online = False

    def getState():
        return online

    def getHost():
        return serverhost

    def getOnline():
        return status.players.online

    def getMax():
        return status.players.max

    def getName():
        return status.description["text"]

    def getVersion():
        return status.version.name

    def getPing():
        return status.latency

    def getPlayers():
        names = []
        if(status.players.sample != None):
            for i in status.players.sample:
                names.append(i.name)
        if(status.players.online < 10):
            return names
        else:
            return []

    data = [getState, getHost, getOnline, getMax, getName, getVersion, getPing, getPlayers]
