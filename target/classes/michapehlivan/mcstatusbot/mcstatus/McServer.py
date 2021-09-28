from mcstatus import MinecraftServer

class McServer:

    def __init__(self, host, ip) -> None:
        global serverhost, serverip, server, status, online

        try:
            server = MinecraftServer(ip, 25565)
            status = server.status()
            serverhost = host
            serverip = ip
            online = True
        except:
            online = False

    def updateStatus():
        try:
            status = server.status()
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
        return names

    data = [updateStatus, getState, getHost, getOnline, getMax, getName, getVersion, getPing, getPlayers]
