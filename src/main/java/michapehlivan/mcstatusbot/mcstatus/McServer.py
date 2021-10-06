from mcstatus import MinecraftServer

#class for managing server data 
class McServer:

    #create server object and create variables
    def __init__(self, host, ip) -> None:
        try:
            server = MinecraftServer(ip, 25565)
            global serverhost, status, online
            status = server.status()
            serverhost = host
            online = True
        except:
            online = False

    #returns true if the server is online
    def getState():
        return online

    #returns the host of the server
    def getHost():
        return serverhost

    #returns the number of online players
    def getOnline():
        return status.players.online

    #returns the maximum amount of online players for this server
    def getMax():
        return status.players.max

    #returns the description of the server
    def getDescription():
        return status.description

    #returns the Minecraft version the server is running on
    def getVersion():
        return status.version.name

    #returns the latency in ms of the server
    def getPing():
        return status.latency

    #returns a list containing all online players, the list is empty if more than 10 are online
    def getPlayers():
        names = []
        if(status.players.sample != None):
            for i in status.players.sample:
                names.append(i.name)
        if(status.players.online <= 10):
            return names
        else:
            return []

    #list containing all functions
    data = [getState, getHost, getOnline, getMax, getDescription, getVersion, getPing, getPlayers]
