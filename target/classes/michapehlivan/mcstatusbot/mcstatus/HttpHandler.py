from http.server import BaseHTTPRequestHandler, HTTPServer
import McServer as mc
import json


currentserver = mc.McServer("host", "ip")
class HttpHandler(BaseHTTPRequestHandler):

    def on_Start():
        global currentserver
        try:
            data = Reader.getServer()
            currentserver = mc.McServer(data[0], data[1])
        except:
            pass

    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type','text/html')
        self.end_headers()
        code = self.headers.get("code")
        message = currentserver.data[int(code)]()
        self.wfile.write(bytes(str(message), "utf8"))

    def do_POST(self):
        self.send_response(200)
        self.send_header('Content-type','text/html')
        self.end_headers()

        host = self.headers.get("serverhost")
        ip = self.headers.get("serverip")
        currentserver = mc.McServer(host, ip)

        message = "server successfully set"
        self.wfile.write(bytes(message, "utf8"))


class Reader:

    path = "mcstatusbot\\src\\main\\java\\michapehlivan\\mcstatusbot\\serverdata\\Servers.json"

    def getServer():
        file = json.load(open(Reader.path))
        host = file["serverhost"]
        ip = file["serverip"]
        return [host, ip]

with HTTPServer(('', 8000), HttpHandler) as server:
    HttpHandler.on_Start()
    server.serve_forever()
