package michapehlivan.mcstatusbot.util;

import java.util.ArrayList;

/**
    Class for generating a list of players based on a String representation of a Python list
    @author Micha Pehlivan
 */
public class PlayerList {
    
    private ArrayList<String> playerlist = new ArrayList<String>();

    /**
     * Constructor of PlayerList class, loops through a String and constructs an ArrayList out of it
     * @param list the String representation of a python list
     * @author Micha Pehlivan
     */
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
