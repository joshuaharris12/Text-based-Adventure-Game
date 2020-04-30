import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of London" application. 
 * "World of London" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;         // stores a description of room.
    private HashMap<String, Room> exits; // stores exits of this room.
    private HashSet<Item> items;         // stores items in the room.
    private boolean isLocked; 
    private HashSet<Character> characters;
    private boolean isMagic;    // is the room a teleporter room
    private static Random randomGenerator;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param isLocked The room is locked.
     * @param isMagic The room is a magic transporter room.
     */
    public Room(String description, boolean isLocked, boolean isMagic) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashSet<>();
        characters = new HashSet<>();
        this.isLocked = isLocked;
        this.isMagic = isMagic;
        randomGenerator = new Random();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     * You are in the kitchen.
     * Exits: north west
     * Items: sword book 
     * Characters: Andy Adam Joshua
     * @return A long description of this room.
     */
    public String getLongDescription()
    {
        String longDescription = "You are " + description + ".\n" +
            getExitString();
        if (getItemsDescription() != null){
            longDescription += "\nItems: " + getItemsDescription();
        }
        if (getCharactersDescription() != null){
            longDescription += "\nCharacters: " +
            getCharactersDescription();
        }
        return longDescription;
    }

    /**
     * Returns wether the room is locked or not.
     * @return Returns true if the room is locked.
     * @return Returns false if the room is unlocked.
     */
    public boolean getIsLocked()
    {
        return isLocked;
    }

    /**
     * Sets the isLocked field to false so that the room is unlocked.
     */

    public void setIsLocked()
    {
        this.isLocked = false;
    }

    /**
     * Returns either true or false depending if the room is magic.
     * @return True if the room is a magic transporter room.
     * @return False if the room is not a magic transporter room.
     */
    public boolean getIsMagic()
    {
        return isMagic;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Adds the item object to the hash map items.
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    /**
     * Removes the object from the hash map items.
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }

    /** 
     * Returns the item object if the name matches an item or returns null
     * if the item is cannot be found.
     * @param name The name of the item.
     * @return The item thats associated with the name.
     */
    public Item findItemFromName(String name)
    {
        boolean found = false;
        Item foundItem = null;
        Iterator<Item> it = items.iterator();
        while (it.hasNext() && !found){
            Item item = it.next();
            if (item.getName().toLowerCase().equals(name.toLowerCase())){
                found = true;
                foundItem = item;
            }
        }
        return foundItem;
    }

    /**
     * Determines if the room already contains a specific item
     * @param room The room object that possibly contains the item.
     */
    public boolean getContainsItem(Item item)
    {
        if (items.contains(item)){
            return true;
        }
        return false;
    }

    /**
     * Returns the description of the items stored in the hash map, items.
     * @return The description of every item in items.
     * @return Returns null if empty;
     */
    public String getItemsDescription()
    {
        if (!items.isEmpty())
        {
            String itemsDescription = "";
            for (Item item: items)
            {
                itemsDescription = itemsDescription + item.getName() 
                + " ";
            }
            return itemsDescription;
        }
        return null;
    }

    /**
     * Removes a character from the hash map characters.
     */
    public void removeCharacter(Character character)
    {
        characters.remove(character);
    }

    /**
     * Returns a character object of the character with the name that 
     * matches.
     */
    public Character getCharacter(String name)
    {
        Iterator<Character> it = characters.iterator();
        boolean found = false;
        while (it.hasNext() && !found){
            Character character = it.next();
            if (character.getName().equalsIgnoreCase(name)){
                found = true;
                return character;
            }
        }
        return null;
    }

    /**
     * Adds a new character to the room. The character object is added
     * to the hash set.
     * @param character The character.
     */
    public void addCharacter(Character character)
    {
        characters.add(character);
    }

    /**
     * Returns the description of the characters in the room.
     * @return A string of the names of the characters..
     * @return Returns null if empty;
     */
    public String getCharactersDescription()
    {
        if (!characters.isEmpty())
        {
            String charactersDescription = "";
            for (Character character: characters)
            {
                charactersDescription = charactersDescription +
                character.getName() + " ";
            }
            return charactersDescription;
        }
        return null;
    }

    /**
     * Choses a valid random diection that the room has.
     * @return A string which is a direction.
     * @return null If there is no exit in the room.
     */
    public String randomExit()
    {
        // retrieves keys from the hash map - keys are the exits
        if (!exits.isEmpty()){
            ArrayList<String> exitArray = new ArrayList<>(exits.keySet());
            return exitArray.get(randomGenerator.nextInt(exitArray.size()));
        }
        return null;
    }

    /**
     * Returns the character object if the name matches a character or 
     * returns null if the character cannot be found.
     * @param name The name of the character.
     * @return The character thats associated with the name.
     */
    private Character findCharacterFromName(String name)
    {
        boolean found = false;
        Character foundCharacter = null;
        Iterator<Character> it = characters.iterator();
        while (it.hasNext() && !found){
            Character character = it.next();
            if (character.getName().toLowerCase().equals(name.toLowerCase())){
                found = true;
                foundCharacter = character;
            }
        }
        return foundCharacter;  
    }

    /**
     * Determines if a particular character is in the room.
     * @param name The name of the character
     * @return true if character object is in characters.
     * @return false if character object is not in characters.
     */
    public boolean characterInRoom(String name)
    {
        Character character = findCharacterFromName(name);
        if (character != null){
            if (characters.contains(character)){
                return true;
            }
        }
        return false;
    }
}