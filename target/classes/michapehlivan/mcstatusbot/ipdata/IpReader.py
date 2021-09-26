import json

class IpReader:
    
    global path, file
    path = "mcstatusbot\src\main\java\michapehlivan\mcstatusbot\ipdata\IpList.json"
    file = open(path, "r")

    def getGuilds():
        return json.load(file)

    def getServers(guild):
        guilds = IpReader.getGuilds()
        servers = guilds[guild]
        serverList = []
        for i in servers:
            host = i["host"]
            ip = i["ip"]
            pair = [host, ip]
            serverList.append(pair)
        return serverList
