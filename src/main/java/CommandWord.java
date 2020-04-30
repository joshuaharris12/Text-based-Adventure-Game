/**
 * This class is a class of the "Adventures in London" application. 
 * The applcation is a text base game and iteracts with the game by typing
 * commands.
 * 
 * Maps each command word to the string version of the command word in the 
 * English language.
 *
 * @author Joshua Harris
 * @version 16.11.2019
 */
public enum CommandWord
{
    // Every command word has a string which is what the user enters.
    GO("go"), QUIT("quit"), HELP("help"), GRAB("grab"), DROP("drop"),
    EAT("eat"), BACK("back"), ITEMS("items"), UNKNOWN("?"), USE("use"),
    FOLLOW("follow"), STOP("stop"), GIVE("give"), SLEEP("sleep");
    
    private String commandString;
    /**
     * Initialises with the command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     *  Returns the command as a string.
     */
    public String getCommandString()
    {
        return commandString;
    }
}