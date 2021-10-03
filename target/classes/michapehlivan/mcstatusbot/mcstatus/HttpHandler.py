from http.server import BaseHTTPRequestHandler, HTTPServer
import McServer as mc
import json

class HttpHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type','text/html')
        self.end_headers()
        code = self.headers.get("code")
        serverArray = Reader.getServer()
        mcserver = mc.McServer(serverArray[0], serverArray[1])
        message = self.mcserver.data[int(code)]()
        self.wfile.write(bytes(str(message), "utf8"))

class Reader:

    path = "mcstatusbot\\src\\main\\java\\michapehlivan\\mcstatusbot\\serverdata\\Servers.json"

    def getServer():
        file = json.load(open(Reader.path))
        host = file["host"]
        ip = file["ip"]
        return [host, ip]

with HTTPServer(('', 8000), HttpHandler) as server:
    server.serve_forever()
