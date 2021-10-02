from http.server import BaseHTTPRequestHandler, HTTPServer
import McServer as mc
import json

class HttpHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type','text/html')
        self.end_headers()
        request = self.requestline.replace("GET /", "").replace(" HTTP/1.1", "")
        message = self.dataFromRequest(request)
        self.wfile.write(bytes(message, "utf8"))

    def dataFromRequest(self, request):
        data = Reader.getServers(request[1:])
        host = data[0]
        ip = data[1]
        mcserver = mc.McServer(host, ip)
        return str(mcserver.data[int(request[0])]())

class Reader:

    path = "mcstatusbot\src\main\java\michapehlivan\mcstatusbot\serverdata\Servers.json"

    def getServers(guild):
        file = json.load(open(Reader.path))
        return file[guild]

with HTTPServer(('', 8000), HttpHandler) as server:
    server.serve_forever()