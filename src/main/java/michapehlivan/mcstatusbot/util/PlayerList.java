package michapehlivan.mcstatusbot.util;

import java.util.ArrayList;

/* 
    Class for generating a list of players based on a String representation of a Python list
 */
public class PlayerList {
    
    private ArrayList<String> playerlist = new ArrayList<String>();

    //loop trough String and split into list
    public PlayerList(String list){
        String[] elements = list.split("' ");
        for(int i = 0; i < elements.length; i++){
            playerlist.add(elements[i].replaceAll("['\\[\\]]", ""));
        }
    }

    //list getter
    public ArrayList<String> getPlayers(){
        return playerlist;
    }
}
