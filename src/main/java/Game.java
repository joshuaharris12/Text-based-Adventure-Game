import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
/**
 *  This class is the main class of the "Adventures in London" application. 
 *  The applcation is a text base game and iteracts with the game by typing
 *  commands.
 *  Users are able to walk around areas of London including the Tower of 
 *  London.
 *  
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes and Joshua Harris
 * @version 2016.02.29 (Modifed 18/11/2019)
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private static Room finalRoom;
    private Random randomGenerator;
    private static Room[] rooms;
    private static Character[] characters;
    private static int stepsBeforeHungry;   // time limit for being hungry
    private static Room roomNext;
    /**
     * Create the game and initialise its internal map.
     * Create the player. 
     */
    public Game() 
    {
        // initalises objects and calls the creteRooms() method
        // sets the maximum weight of the player to 15
        player = new Player(15);  
        createRooms();
        parser = new Parser();
        randomGenerator = new Random();
        stepsBeforeHungry = 10;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create local variables of type Room.
        Room dungeon, courtyard, tower, outsideTower, towerHillTube, 
        hydePark, greenPark, greenParkTube, buckinghamPalaceFoyer,
        throneRoom,ritzHotel,random;

        // creates a random room
        random = new Room("in a random room", false, true);
        // create the rooms
        dungeon = new Room("in the dungeon of the tower of London"
        , true, false);

        courtyard= new Room("in the courtyard of the tower of London"
        , false, false);
        tower = new Room("in the tower where the crown jewels are kept",
            false, false);
        outsideTower= new Room("outside the tower of London", false,
            false);
        towerHillTube = new Room("at Tower Hill tube station", false, 
            false);
        hydePark = new Room("in hyde park", false, false);
        greenPark = new Room("in green park", false, false);
        greenParkTube = new Room("at Green Park tube station", false, 
            false);
        buckinghamPalaceFoyer = new Room("in the foyer of Buckingham Palace"
        , true, false);
        throneRoom = new Room("in the throne room of Buckingham Palace", 
            false, false);
        ritzHotel = new Room("in a delux room at The Ritz hotel",false, 
            false);
        // adds the variables to the array so that the array can be used
        // to generate a random room
        rooms = new Room[] {dungeon, courtyard, tower, outsideTower,
            towerHillTube, hydePark, greenPark, greenParkTube, 
            buckinghamPalaceFoyer,throneRoom, ritzHotel};

        // initialise room exits
        dungeon.setExit("east",courtyard);
        courtyard.setExit("south",outsideTower);
        courtyard.setExit("east", tower);
        courtyard.setExit("west", dungeon);
        tower.setExit("west", courtyard);
        outsideTower.setExit("north", courtyard);
        outsideTower.setExit("west", towerHillTube);
        towerHillTube.setExit("down", greenParkTube);
        towerHillTube.setExit("east", outsideTower);
        greenParkTube.setExit("south", greenPark);
        greenParkTube.setExit("down", towerHillTube);
        greenPark.setExit("north", greenParkTube);
        greenPark.setExit("south", buckinghamPalaceFoyer);
        greenPark.setExit("east", ritzHotel);
        greenPark.setExit("west", hydePark);
        buckinghamPalaceFoyer.setExit("north", greenPark);
        buckinghamPalaceFoyer.setExit("east", throneRoom);
        buckinghamPalaceFoyer.setExit("west", hydePark);
        throneRoom.setExit("west", buckinghamPalaceFoyer);
        ritzHotel.setExit("west", greenPark);
        hydePark.setExit("east", greenPark);
        hydePark.setExit("south", buckinghamPalaceFoyer);
        hydePark.setExit("north", random);

        // initialise room items
        hydePark.addItem(new Item("statue", 60, false, false));
        dungeon.addItem(new Item("magic-wand", 1,true, false));
        tower.addItem(new Item("sword",3, true, false));
        tower.addItem(new Item("shield", 10, true, false));
        courtyard.addItem(new Item("apple",1, true, true));
        ritzHotel.addItem(new Item("bed", 50, false, false));
        ritzHotel.addItem(new Item("steak", 1, true, true));
        buckinghamPalaceFoyer.addItem(new Item("chair",50,false, false));
        buckinghamPalaceFoyer.addItem(new Item("painting",3,false, false));
        throneRoom.addItem(new Item("throne",25,false, false));
        throneRoom.addItem(new Item("crown-jewels",8,true, false));
        hydePark.addItem(new Item("magic-cookie",0, true, true));
        towerHillTube.addItem(new Item("map", 1,true, false));
        tower.addItem(new Item("crisps",1,true, true));
        hydePark.addItem(new Item("cheese", 1,true, true));
        greenPark.addItem(new Item("magic-key",1, true,false));
        // initialise room characters
        Character andy, queen, jack, police;

        andy = new Character("Andy", dungeon, false);
        dungeon.addCharacter(andy);
        queen = new Character("Queen", buckinghamPalaceFoyer, false);
        buckinghamPalaceFoyer.addCharacter(queen);
        jack = new Character("Jack", greenPark, false);
        greenPark.addCharacter(jack);
        police = new Character("Police", towerHillTube, false);
        towerHillTube.addCharacter(police);
        characters = new Character[] {andy, queen, jack, police};
        // start game in the dungeon
        player.setCurrentRoom(dungeon);  
        // the winning room
        finalRoom = tower;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands 
        // and execute them until the game is over.

        boolean finished = false;
        while (! finished && !hasWon() && !caught()) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            printIsHungry();
        }
        if (hasWon()){
            System.out.println("You have won!!" +
                "\nYou have successfully returned the crown jewels.");
        }
        else if (caught()){
            System.out.println("YOU HAVE BEEN CAUGHT!");
            System.out.println("GAME OVER!");
        }
        else{
            System.out.println("Thank you for playing.  Good bye.");
        }
    }

    /**
     * Print out the updated list of items in the room.
     */
    private void printItemList()
    {
        String description = player.getCurrentRoom().getItemsDescription();
        if (description != null){
            System.out.println("Items: " + description);
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Adventures in London!");
        System.out.println(
            "Adventures in London is an adventure game.");
        System.out.println(
            "You have been framed for steeling the crown jewels");
        System.out.println(
            "To prove your innocence, you must recover them.");
        System.out.println("Your mission: ");
        System.out.println("- Break out of the dungeon");
        System.out.println("- locate the crown jewels");
        System.out.println(
            "- Return the crown jewels to the tower of London");
        System.out.println("- Don't get caught!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInformation();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord){
            case UNKNOWN:
            System.out.println("I don't know what you mean..."); 
            break;

            case HELP:
            printHelp();  
            break;

            case GO:
            goRoom(command);
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;

            case GRAB:
            grab(command.getSecondWord());
            break;

            case DROP:
            drop(command.getSecondWord());
            break;

            case BACK:
            back();
            break;

            case EAT:
            eat(command.getSecondWord());
            break;

            case ITEMS:
            items();
            break;

            case USE:
            use(command.getSecondWord());
            break;

            case FOLLOW:
            follow(command.getThirdWord());
            break;

            case STOP:
            stopFollowing(command.getSecondWord(),command.getThirdWord());
            break;

            case GIVE:
            giveItem(command.getSecondWord(), command.getThirdWord());
            break;

            case SLEEP:
            sleep();
            break;
        }
        // else command not recognised.
        return wantToQuit;
    }
    // implementations of user commands:

    /**
     * Prints out The description of the curent room the player is in.
     */
    private void printLocationInformation()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println();
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are on the run. You are alone. You wander");
        System.out.println("the streets of London looking for clues.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();  
    }

    /** 
     * "go" was entered.
     * Try to in to one direction. If there is an exit, 
     * enter the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            // checks if the room is a transporter room
            if (nextRoom.getIsMagic()){
                // sets the current room to a randomly chosen room
                player.setCurrentRoom(generateRandomRoom());
                // adds 1 to the movesMade
                player.incrementMovesMade();
                System.out.println(
                    "That was strange," + 
                    " that must have been a magic transporter room");
                printLocationInformation();
            }
            else{
                // checks if the room the player is in, is locked
                if (!nextRoom.getIsLocked() && !player.getCurrentRoom().
                getIsLocked()){
                    // adds previous room to stack
                    player.addPreviousRoom(player.getCurrentRoom());
                    // updates current room
                    player.setCurrentRoom(nextRoom); 
                    player.incrementMovesMade();
                    // Moves the characters on thier own
                    moveCharacters();
                    if (player.hasFollowers()){
                        player.followPlayer();
                    }
                    printLocationInformation();
                }
                else if (player.getCurrentRoom().getIsLocked()){
                    System.out.println("The door is locked");
                    System.out.println(
                        "Find the magic-wand to open the door"); 
                }
                else  {
                    roomNext = nextRoom;
                    System.out.println("The door is locked");
                    System.out.println(
                        "Find the magic-key to open the door"); 
                }
            }
        }
    }

    /**
     * Prints a message to let the user know the player is hungry.
     */
    private void printIsHungry()
    {
        if (player.getMovesMade()>= stepsBeforeHungry){
            System.out.println("You need to eat now");
        }
    }

    /**
     * "grab" was entered.
     * Picks up an item from the room. If the item can 
     * be picked up, the item is added to the hash set, 
     * if not the item is left in the room.
     * @param name The name of the item to be addded.
     */
    private void grab(String name)
    {
        if (name != null){
            Room currentRoom = player.getCurrentRoom();
            Item item = currentRoom.findItemFromName(name);
            if (item != null){
                if(item.getAbleToPickUp()){
                    if (player.getCurrentWeight() + item.getWeight()
                    <= player.getMaximumWeight()){
                        player.addItem(item);
                        System.out.println(item.getName() + 
                            " picked up");
                        currentRoom.removeItem(item);
                        printItemList();
                    }
                    else {
                        System.out.println(
                            "The item makes the load too heavy");
                    }
                }
                else {
                    System.out.println("Item can't be picked up");
                }
            }
            else {
                System.out.println("can't find the item");
            }
        }
        else {
            System.out.println("Grab what?");
        }
    }

    /**
     * "drop" was entered.
     * Drops the item in the room where the player is currently in.
     * The item is removed from the collection. 
     * @param name The name of the item.
     */
    private void drop(String name)
    {
        // Retrieves room object of current room
        Room currentRoom = player.getCurrentRoom();

        // retrieves item object of the item that is being dropped
        Item item = player.getItemFromCollection(name);
        if (item != null){
            player.removeItem(name);
            System.out.println(name + " dropped");
            // if the room already contains that item, 
            // the item will only be displayed once.
            if (!currentRoom.getContainsItem(item)){
                // Adds the item that has been dropped 
                // to the current room.
                currentRoom.addItem(item);
                // Updates the list of items that 
                // are in the room being displayed.
                printItemList();
            }
        }
        else {
            System.out.println("I'm not carrying that item");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, 
     * false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * "back" was entered.
     * Returns the player to the previous room they were in.
     */
    private void back()
    {
        Room previousRoom = player.getPreviousRoom();
        if (previousRoom != null){
            player.setCurrentRoom(previousRoom);
            printLocationInformation();
        }
        else {
            System.out.println("Can't go back");
        }
    }

    /**
     * Checks if the user has completed the mission.
     * @return true or false.
     * Returns true if the player is 'holding' the 
     * crown-jewels and the current room is the tower.
     */
    private boolean hasWon()
    {
        // Checks if the player is holding the crown-jewels
        // and is in the tower.
        if (player.isHeld("crown-jewels") && 
        player.getCurrentRoom() == finalRoom){
            return true;
        }
        return false;
    }

    /**
     * "items" were entered.
     *  Prints out a list of the items the player is
     *  currently holding and thier total weight.
     */
    private void items()
    {
        String items = player.getItems();
        if (items != null){
            System.out.println("Items: " + items + 
                "\nCurrent weight: " + player.getCurrentWeight()
                + "kg");
        }
        else {
            System.out.println("Not carrying any items");
        }
    }

    /**
     * "eat" was entered.
     * Replicates eating an item of food.
     * The player can eat an item that they are holding and 
     * if the item is edible.
     * @param name The name of the item the player wants to eat.
     */
    private void eat(String name)
    {
        // retrieves item object if the player is holding the item.
        Item item = player.getItemFromCollection(name);
        //Checks if item is being held by the player.
        if (item != null){
            // Checks if the item can be eaten.
            if (item.getIsEdible()){
                if (item.getName().equals("magic-cookie")){
                    // increases the current maximum weight a 
                    // player can hold
                    eatMagicCookie();
                    // removes item from the players collection
                    player.removeItem(item);
                }
                else if (player.getMovesMade() >= stepsBeforeHungry && 
                !item.getName().equals("magic-cookie")){
                    System.out.println("That was yummy! I'm full now");
                    // removes the piece of food from the players 
                    // collection
                    player.removeItem(item);
                    // resets the number of moves made since last eating
                    player.resetMovesMade();
                }     
                else if (player.getMovesMade() < stepsBeforeHungry) {
                    System.out.println("I'm not hungry");
                }
            }
            else {
                System.out.println("I can't eat that, it's not edible");
            }
        }
        else {
            System.out.println("I'm not carrying that");
        }
    }

    /**
     * Simulates eating a magic cookie.
     * If the cookie is eaten, the maximum weight a player can hold is
     * increased by 5kg.
     */
    private void eatMagicCookie()
    {
        player.increaseMaximumWeight(5);
        System.out.println(
            "That was tasty, I can now carry even more weight");
    }

    /**
     * "used" has been entered.
     * This replicates using an item.
     * The player can open locked doors and use a map.
     * If item is not recongised, then an error message is
     * displayed.
     * @param itemName The name of the item thats being used.
     */
    private void use(String itemName)
    {
        if (itemName != null){
            switch (itemName){
                case "magic-wand":
                if (player.isHeld("magic-wand")){
                    player.getCurrentRoom().setIsLocked();
                    System.out.println("The door is now unlocked");
                    player.removeItem("magic-wand");
                }
                else {
                    System.out.println("I'm not holding the magic wand");
                }
                break;
                case "map":
                if (player.isHeld("map")){
                    System.out.println(
                        "You will find the crown jewels with the Queen");
                    try{
                        mapViewer();
                    }
                    catch(Exception e){
                        System.out.println("URL is invalid");
                    }
                }
                else {
                    System.out.println("I'm not holding the map");
                }
                break;

                case "magic-key":
                if (player.isHeld("magic-key")){
                    if (roomNext != null){
                        if (roomNext.getIsLocked()){
                            roomNext.setIsLocked();
                            System.out.println("The door is now open");
                            player.removeItem("magic-key");
                        }
                    }
                    else {
                        System.out.println("can't use the magic-key");
                    }
                }
                else {
                    System.out.println("I'm not holding the magic wand");
                }
                break;

                default:
                System.out.println("You can't use that item");
                break;
            }
        }
        else {
            System.out.println("use what?");
        }
    }

    /**
     * Trys to open an internet browser to display google maps showing 
     * Buckingham Palace.
     * Throws an exception if there is a problem with loading the map.
     */
    private void mapViewer() throws Exception
    {
        double latitude = 51.501476;
        double longitude = -0.140634;
        URI uri = new URI("https://www.google.com/maps/place/" + latitude 
                + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri); 
    }

    /**
     * Generates a random integer between 0 and the number of rooms that 
     * have been created. The random index that is generated is used then
     * to access a random room from the array.
     * @return Random room object
     */
    public Room generateRandomRoom()
    {
        // generates a random index
        int index = randomGenerator.nextInt(rooms.length);
        return rooms[index];
    }

    /**
     * "follow" was entered. The character starts following the player.
     */
    private void follow(String thirdWord)
    {
        if (thirdWord != null){
            // gets the character object of the character by its name
            Character character = player.getCurrentRoom().getCharacter(
                    thirdWord);
            if (character != null){
                // adds the character to the followers collection.
                player.addFollower(character);
                character.setIsFollowingPlayer();
                // removes character from current room
                player.getCurrentRoom().removeCharacter(character);
                System.out.println(character.getName() +
                    ": \"Okay, I will follow you\"");
            }
            else{
                System.out.println("They are not in the room");
            }
        }
        else {
            System.out.println("I don't know who wants to follow me.");
        }
    }

    /**
     * Removes the player from the collection of characters that follow 
     * the player.
     * @param thirdWord The name of the character who is to be removed.
     */
    private void stopFollowing(String secondWord,String thirdWord)
    {
        if (secondWord != null){
            if (secondWord.equalsIgnoreCase("following")){
                Room currentRoom = player.getCurrentRoom();
                // returns the character thats linked with the name
                // (thirdWord)
                Character character = player.findCharacterFromName(thirdWord);
                if (character != null){
                    player.removeFollower(character);
                    currentRoom.addCharacter(character);
                    character.setIsFollowingPlayer();
                    System.out.println(character.getName() +
                        ":\"I've stopped following you!\"");
                }
                else{
                    System.out.println("They are not following you");
                }
            }
        }
        else{
            System.out.println("stop what?");
        }
    }

    /**
     * Characters can move around by themselves, 
     * as one of the avilable exits is chosen at random of the room the 
     * character is currently in and the character moves to that room.
     * This is done for every character that is not
     * following a player or is not the queen.
     */
    private void moveCharacters()
    {
        Room orignalRoom = null;
        Room newRoom = null;
        for (int i = 0; i < characters.length; i++){
            Character character = characters[i];
            // Determines if the character is following a 
            // player and if they are the queen
            if (!character.getIsFollowingPlayer() && 
            !character.getName().equalsIgnoreCase("queen")){
                orignalRoom = character.getCurrentRoom();
                if (!character.getCurrentRoom().getIsMagic()){
                    // randomly choses a new room which is an exit of
                    // the orignal room.
                    newRoom = character.getCurrentRoom().
                    getExit(character.getCurrentRoom().randomExit());
                    character.setCurrentRoom(newRoom);
                }
                else if (character.getCurrentRoom().getIsMagic()){
                    // if in random room them takes character to
                    // a chosen random room.
                    newRoom = generateRandomRoom();
                    character.setCurrentRoom(newRoom);
                }
                orignalRoom.removeCharacter(character);
                newRoom.addCharacter(character);
            }
        }
    }

    /**
     * "give" has been entered. Player gives an item to a character 
     * from the player's collection of items they are carrying.
     * @param name The name of the character.
     * @param item The name of the item to be passed.
     */
    private void giveItem(String name, String item)
    {
        // retrieves character object by supplying the name of character
        Character character = player.findCharacterFromName(name);
        if (character != null){
            // determines if player is holding the item.
            if (player.isHeld(item)){
                character.addItem(player.getItemFromCollection(item));
                player.removeItem(player.getItemFromCollection(item));
                System.out.println(character.getName() + 
                    ": \"Thanks for that!\"");
            }
            else {
                System.out.println("I'm not holding that");
            }
        }
        else {
            System.out.println("Who is that?");
        }
    }

    /**
     * "sleep" was entered. If the current room contains a bed, then the
     * player can sleep. If the player sleeps, the number of steps before 
     * they become hungry is increased.
     */
    private void sleep()
    {
        // Determines if current room contains a bed
        if (player.getCurrentRoom().getContainsItem(
            player.getCurrentRoom().findItemFromName("bed"))){
            System.out.println("I'm going to sleep\n");
            System.out.println("zzzzzzzzzzzzzzzzz\n");
            System.out.println("Ah that was a good sleep!");
            stepsBeforeHungry =+ 5;
        }
        else {
            System.out.println("There is no bed!");
        }
    }

    /**
     * Determines if the player has been caught by the police with the crown
     * jewels.
     * @return true if the player is in the same room as the police character
     * and is carrying the crown jewels.
     */
    private boolean caught()
    {
        if (player.getCurrentRoom().characterInRoom("police") && 
        player.isHeld("crown-jewels")){
            return true;
        }
        return false;
    }
}