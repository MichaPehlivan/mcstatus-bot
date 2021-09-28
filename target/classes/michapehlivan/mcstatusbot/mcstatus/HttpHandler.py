from http.server import BaseHTTPRequestHandler, HTTPServer
import McServer as mc

class HttpHandler(BaseHTTPRequestHandler):
    
    mcserver = mc.McServer("host", "ip of server")

    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type','text/html')
        self.end_headers()
        request = self.requestline.replace("GET /", "").replace(" HTTP/1.1", "")
        message = self.dataFromRequest(request)
        self.wfile.write(bytes(message, "utf8"))

    def dataFromRequest(self, request):
        return str(self.mcserver.data[int(request)]())

with HTTPServer(('', 8000), HttpHandler) as server:
    server.serve_forever()