import java.util.HashMap;
/**
 * This class is a class of the "Adventures in London" application. 
 * The applcation is a text base game and iteracts with the game by typing
 * commands.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords
{
    private HashMap<String, CommandWord> commands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        commands = new HashMap<>();
        for (CommandWord command : CommandWord.values()){
            if (command != CommandWord.UNKNOWN){
                commands.put(command.getCommandString(), command);
            }
        }
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return commands.containsKey(aString);
    }

    /**
     * 
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = commands.get(commandWord);
        if (command != null){
            return command;
        }
        else{
            return CommandWord.UNKNOWN;
        }
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(String command: commands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
