/**
 * This class is a class of the "Adventures in London" application. 
 * The applcation is a text base game and iteracts with the game by typing
 * commands.
 * The class models a real world item, like a Sword or a book. Each item 
 * has a name, a short description and a weight. Some items are able to be
 * picked up by the player and some are not. 
 * 
 * @author  Joshua Harris
 * @version 15.11.2019
 */
public class Item
{
    // instance variables 
    private String name;
    private int weight; // weight is a whole number in kg
    private boolean ableToPickUp; // can the item be picked up?
    private boolean isEdible;
    /**
     * Constructor for objects of class Item. This initalises the instance
     * variables with the data thats provided.
     * @param name The name of the item
     * 
     */
    public Item(String name, int weight, boolean
    ableToPickUp, boolean isEdible)
    {
        //intitalise instance variables
        this.name = name;
        this.weight = weight;
        this.ableToPickUp = ableToPickUp;
        this.isEdible = isEdible;
    }

    /**
     * Returns the name of the item.
     * @return Item's name.
     */
    public String getName()
    {
        return name;
    }
   
    /**
     * Checks if the item can be eaten.
     * @return Returns true if the isEdible is true and false 
     * if isEdible is false.
     */
    public boolean getIsEdible()
    {
        return isEdible;
    }
    
    /**
     * Returns the weight of an item.
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Determines if the item can be picked up.
     * Returns true if ableToPickUp is true and false if ableToPickUp
     * is false.
     * @return either true or false depending on ableToPickUp
     */
    public boolean getAbleToPickUp()
    {
        return ableToPickUp;
    }
}
