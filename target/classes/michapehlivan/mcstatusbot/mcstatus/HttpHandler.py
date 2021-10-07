from http.server import BaseHTTPRequestHandler, HTTPServer
import sys
import McServer as mc
import json
import os


currentserver = mc.McServer("host", "ip")
class HttpHandler(BaseHTTPRequestHandler):

    #create currentserver variable on startup
    def on_Start():
        global currentserver
        try:
            data = Reader.getServer()
            currentserver = mc.McServer(data[0], data[1])
        except:
            pass

    #handle get request
    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type','text/html')
        self.end_headers()
        code = self.headers.get("code")
        message = currentserver.data[int(code)]()
        self.wfile.write(bytes(str(message), "utf8"))

    #handle post request
    def do_POST(self):
        self.send_response(200)
        self.send_header('Content-type','text/html')
        self.end_headers()

        host = self.headers.get("serverhost")
        ip = self.headers.get("serverip")
        currentserver = mc.McServer(host, ip)

        message = "server successfully set"
        self.wfile.write(bytes(message, "utf8"))

    def log_message(self, format: str, *args: any) -> None:
        return 

#Class for reading server data from json
class Reader:

    path = "mcstatusbot\\src\\main\\java\\michapehlivan\\mcstatusbot\\serverdata\\Servers.json"

    def getServer():
        file = json.load(open(Reader.path))
        host = file["serverhost"]
        ip = file["serverip"]
        return [host, ip]

#server setup and handle requests
with HTTPServer(('', 8000), HttpHandler) as server:
    HttpHandler.on_Start()
    server.serve_forever()