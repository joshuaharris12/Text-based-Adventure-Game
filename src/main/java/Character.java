import java.util.HashSet;
import java.util.Iterator;
/**
 * The class acts as a template for a character. A character is able to 
 * move on their own around the map. A character is able to follow a player
 * or can just roam around on their own. A character can hold items given 
 * to them by the player.
 *
 * @author  Joshua Harris
 * @version 19.11.2019
 */
public class Character
{
    private String name;
    private Room currentRoom;         
    private String description; // describes the character (e.g. police)
    private boolean isFollowingPlayer;
    private HashSet<Item> items;
    /**
     * Constructor for objects of class Character. 
     * Defines the name, the current room the character is in sand wether 
     * the character is following a player.
     * @param name The name of the character
     * @param currentRoom The room the character starts in
     * @param isFollowingPlayer Boolean value for if the character is 
     * following a player
     */
    public Character(String name, Room currentRoom, 
    boolean isFollowingPlayer)
    {
        // initialise instance variables
        this.name = name;
        this.currentRoom = currentRoom;
        this.isFollowingPlayer = isFollowingPlayer; 
        items = new HashSet<>();
    }
    
    /**
     * Returns the name of the character.
     * @return Character name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Sets the current room to the room provided.
     * @param currentRoom The room object of the new current room
     */
    public void setCurrentRoom(Room currentRoom){
        this.currentRoom = currentRoom;
    }
    
    /**
     * Returns the object of the room the player is currently in.
     * @return The current room object
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
  
    /**
     * Retuns the isFollowingPlayer field.
     * @return true, if the character is following a player.
     * @return false, if the character is not following a player.
     */
    public boolean getIsFollowingPlayer()
    {
        return isFollowingPlayer;
    }
    
    /**
     * Updates the field isFollowingPlayer to true if the character is 
     * following a player or false if the character is not following 
     * a player.
     */
    public void setIsFollowingPlayer()
    {
        isFollowingPlayer = !isFollowingPlayer;
    }
    
    /**
     * Adds a item to the characters collection of items the 
     * character is carrying.
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Removes a item from the characters collection of items 
     * the character is carrying.
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }
}