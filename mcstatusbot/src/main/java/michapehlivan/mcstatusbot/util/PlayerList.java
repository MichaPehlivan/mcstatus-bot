package michapehlivan.mcstatusbot.util;

import java.util.ArrayList;

public class PlayerList {
    
    private ArrayList<String> playerlist = new ArrayList<>();

    public PlayerList(String list){
        String[] elements = list.split("' ");
        for(int i = 0; i < elements.length; i++){
            playerlist.add(elements[i].replaceAll("['\\[\\]]", ""));
        }
    }

    public ArrayList<String> getPlayers(){
        return playerlist;
    }
}
