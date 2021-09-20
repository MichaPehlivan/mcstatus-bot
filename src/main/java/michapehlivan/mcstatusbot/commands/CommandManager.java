package michapehlivan.mcstatusbot.commands;

import java.util.HashMap;
import java.util.Map;

import michapehlivan.mcstatusbot.commands.commands.StatusCommand;

public class CommandManager {
    
    public static Map<String, Command> commands = new HashMap<String, Command>();

    public static void init(){
        commands.put("status", new StatusCommand());
    }

}
