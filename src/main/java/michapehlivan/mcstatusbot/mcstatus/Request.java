package michapehlivan.mcstatusbot.mcstatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Request {

    public static String request(int request) throws IOException{
        URL url = new URL("http://localhost:8000/" + Integer.toString(request));
        URLConnection con = url.openConnection();
        BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}
