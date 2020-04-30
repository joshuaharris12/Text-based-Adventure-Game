import java.util.HashSet;
import java.util.Iterator;
import java.util.Arrays;
/**
 * This class is a class of the "Adventures in London" application. 
 * The applcation is a text base game and iteracts with the game by typing
 * commands.
 * The class models a player in a game. The player is the character
 * in the game who the user is playing as. The player moves between
 * the rooms and is the object that interacts with items and other characters
 * in the room. 
 * An object of the class holds references to the current room and 
 * the previous room, which are room objects that represents the 
 * room the player is currently in and the room the player was previously
 * in, respectively. Also, the player has a maximum weight in which the
 * player can carry. 
 * 
 * The number of moves the player makes, refers to the number of rooms the
 * player has visited.
 * 
 * @author Joshua Harris
 * @version 15.11.2019
 */
public class Player
{
    private Room currentRoom;
    private int maximumWeight;
    private int currentWeight;
    private HashSet<Item> items;
    private HashSet<Character> followers;
    private int movesMade;
    private Room[] previousRooms; // stack of previously visited rooms
    private int stackTop; // index that is the top of stack
    /**
     * Constructor for objects of class Player. The game class defines the
     * maximum wieght of items a player can hold.
     */

    public Player(int maximumWeight)
    {
        currentRoom = null;
        items = new HashSet<>();
        this.maximumWeight = maximumWeight;
        currentWeight = 0;
        movesMade = 0;
        followers = new HashSet<>();
        previousRooms = new Room[100]; 
        // maximum rooms a player can go back is 100
        stackTop = -1; // stack starts off empty
    }   

    /**
     * Returns the current room the player is in.
     * @return The room object for the current room the player is in.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Returns the previous room the player was in or null if there is 
     * no previous room.
     * @return The room object for the previous room the player was in.
     */
    public Room getPreviousRoom()
    {
        // Determines if the stack is empty (-1 indicates empty)
        if (stackTop > -1){
            stackTop -= 1;
            return previousRooms[stackTop + 1];
        }
        else {
            return null;
        }
    }

    /**
     * Sets the current room where the player is in.
     * @param currentRoom The current room object for the 
     * room the player is in.
     */
    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    /**
     * Adds the previous room to the top of the stack. Increments the top of
     * the stack.
     * @param previousRoom The room object of the previous room the player 
     * was in.
     * @return true if the room can be added to the top of the stack.
     * @returns false if the stack is full.
     */
    public boolean addPreviousRoom(Room previousRoom)
    {
        // Checks if the size of the stack is smaller than the max stack size
        if (stackTop < 99){
            stackTop += 1;
            previousRooms[stackTop] = previousRoom;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds an item to the player's collection of items.
     * The item object is added to the hash set.
     * @param item The item to be added to the collection.
     */
    public void addItem(Item item)
    {
        // Adds the Item, item to the hash set.
        items.add(item);
        // increments the weight.
        currentWeight += item.getWeight();
    }

    /**
     * Removes an item from the collection of items the player is holding.
     * Removes the item from the hash set items..
     * @param name The name of the item.
     */
    public void removeItem(String name)
    {
        Item item = getItemFromCollection(name);
        // Removes item from collection if the item object is not empty
        if (item != null){
            items.remove(item);
            currentWeight -= item.getWeight();
        }
    }

    /**
     * Removes an item from the collection of items the player is holding.
     * Removes the item from the hash set items.
     * @param item The item object of the item.
     */
    public void removeItem(Item item)
    {
        // Removes item from collection if the item object is not empty
        if (item != null){
            items.remove(item);
            currentWeight -= item.getWeight();
        }
    }

    /**
     * Returns the item object thats linked to the name provided.
     * @return null if the item is not in items (item not held by player).
     * @return item if the item is in items (item is being held by player).
     * @param name The name of the item.
     */
    public Item getItemFromCollection(String name)
    {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()){
            Item item = it.next();
            // compares the name held in the item object to the
            // name parameter
            if (item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }

    /**
     * Returns the combined weight of all of the items the player
     * is carrying.
     * @return The current weight of the items in the collection.
     */
    public int getCurrentWeight()
    {
        return currentWeight;
    }

    /**
     * Returns the number of moves the player has made.
     * @return Number of moves.
     */
    public int getMovesMade()
    {
        return movesMade;
    }

    /**
     * Increments the number of moves made by 1.
     */
    public void incrementMovesMade()
    {
        movesMade++;
    }

    /**
     * Returns the maximum weight of items the player can carry.
     * @return Maximum weight.
     */
    public int getMaximumWeight()
    {
        return maximumWeight;
    }

    /**
     * Checks if an item is stored in the hash-set items using the name of 
     * the item.
     */
    public boolean isHeld(String name)
    {
        Iterator<Item> it = items.iterator();
        boolean found = false;
        while (it.hasNext() && !found){
            // Retrieves item from the set.
            Item item = it.next();
            // compares the name held in the item object to the name
            // parameter
            if (item.getName().equals(name)){
                found = true; 
            }
        }
        return found;
    }

    /**
     * Increase the maximum weight a player can hold by a positive amount.
     * @param increaseAmount The amount to increase the maximum weight by.
     */
    public void increaseMaximumWeight(int increaseAmount)
    {
        if (increaseAmount > 0){
            // Increments the maximum weight by the value 'amount'. 
            maximumWeight += increaseAmount;
        }
    }

    /**
     * Returns a list of the items the player is holding as a string.
     * @return itemList, if the hash set is not empty.
     * @return null, if the hash set is empty.
     */
    public String getItems()
    {
        // Checks if the hash set is empty
        if (!items.isEmpty()){
            String itemList = "";
            // Creates iterator object for the hash set.
            Iterator<Item> it = items.iterator();
            // concatentes the names of every item to form a string.
            while (it.hasNext()){
                itemList += it.next().getName() + " ";      
            }
            return itemList;
        }
        return null;
    }

    /**
     * Resets the number of moves made to zero.
     */
    public void resetMovesMade()
    {
        movesMade = 0;
    }

    /**
     * Adds a character to followers so that the character follows the 
     * player around the game.
     */
    public void addFollower(Character character)
    {
        followers.add(character);
    }

    /**
     * Removes the character from the set of characters that are following
     * the player. 
     * @param character The character that the player wants to stop being
     * followed by.
     */
    public void removeFollower(Character character)
    {
        if (character != null){
            followers.remove(character);
        }
    }

    /**
     * Updates the current room for each character that is following the 
     * player. 
     */
    public void followPlayer()
    {
        if (!followers.isEmpty()){
            for (Character character : followers){
                character.setCurrentRoom(currentRoom);
            }
        }
    }

    /**
     * Determines if any characters are following the player.
     * @return true, if followers is not empty.
     * @return false, if followers is empty.
     */
    public boolean hasFollowers()
    {
        if (!followers.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Matches and returns the character using the the characters name.
     * @param name The name of the character.
     * @return The character object of the matching character.
     * @return null If the character is not following the player.
     */
    public Character findCharacterFromName(String name)
    {
        Iterator<Character> it = followers.iterator();
        while (it.hasNext()){
            Character character = it.next();
            if (character.getName().equalsIgnoreCase(name)){
                return character;
            }
        }
        return null;
    }
}