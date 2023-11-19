/**
 * This class is the main class of the "Adventures in London" application. 
 * The applcation is a text base game and iteracts with the game by typing
 * commands.
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a second
 * word (for example, if the command was "take map", then the two strings
 * obviously are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kölling and David J. Barnes and Joshua Harris
 * @version 2016.02.29 (Modifed on 16/11/2019)
 */
public class Command
{
    private CommandWord commandWord;
    private String secondWord;
    private String thirdWord;
    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param commandWord The command word. UNKNOWN if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     * @param thirdWord The thid word of the command.
     */
    public Command(CommandWord commandWord, String secondWord,
    String thirdWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }
    
    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return The third word of this command. Returns null if there was no
     * third word.
     */
    public String getThirdWord()
    {
        return thirdWord;
    }
    
    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    
    /**
     * @return true if the command has a third word.
     */
    public boolean hasthirdWord()
    {
        return (thirdWord != null);
    }
}