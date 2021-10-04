package michapehlivan.mcstatusbot.mcstatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import michapehlivan.mcstatusbot.serverdata.ServerObject;

public class Request {

    public static String request(int requestcode) throws URISyntaxException, IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8000/get"))
            .header("code", Integer.toString(requestcode))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.body();
    }

    public static String post(ServerObject server) throws URISyntaxException, IOException, InterruptedException{
        String host = server.getHost();
        String ip = server.getIp();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8000/post"))
            .header("serverhost", host)
            .header("serverip", ip)
            .POST(BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.body();
    }
}
