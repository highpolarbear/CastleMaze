import java.util.Set;
import java.util.HashMap;
import java.util.*;

/**
 * This is the class of a Room of the game Castle Maze.
 * 
 * Assignment 2 - Castle Maze.
 * Terry Phung
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;              // stores the items in the room.
    private ArrayList<Monster> monsters;
    private Monster monster;
    private boolean killedMonster;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        Monster monster = null;
        killedMonster = false;
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
     * adds an item to the room. called from class Game.
     */
    public void setItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * adds a monster to the room. called from class Game.
     * each room can only take one single monster.
     */
    public void setMonster(Monster currentMonster)
    {
        monster = currentMonster;
    }
    
    /**
     * deletes an item from a room.
     */
    public void removeItem(Item item)
    {
        items.remove(item);
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
     * Returns the Monster in the room.
     * Returns null if there are none.
     */
    public Monster getMonster()
    {
        return monster;
    }
    
    /**
     * Kills off the Monster in the room.
     * Sets monster back to null,
     * and that the room has a killed monster.
     */
    public void killMonster()
    {
        removeMonster();
        killedMonster = true;
    }
    
    /**
     * Removes the monster in the room without killing it.
     */
    public void removeMonster()
    {
        monster = null;
    }
    
    /**
     * Returns a boolean value, to check and see if there's a monster in the room.
     */
    public boolean hasMonster()
    {
        boolean exists = false; //return value exists
        if (monster != null){
            exists = true;
        }
        return exists;
    }
    
    /**
     * Returns a boolean value, to check whethen there's been a killed monster.
     */
    public boolean hasKilledMonster()
    {
        return killedMonster;
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }
    
    /**
     * Returns all the items in the room in a String.
     */
    public String getItemList()
    {
        String printString = null; //return String
        if (items.size() > 0){ //if there's items in the room
            printString = "In this room, there's ";
            for (int i = 0; i < items.size(); i++){ //get all the items' names
                printString += " a " + items.get(i).getName();
                if (i < items.size() - 1 ){
                    printString += " and ";
                }
            }
        }
        return printString;
    }
    
    
    /**
     * Tries to get an item from the room, by checking each item in the room
     * to see if it's the desired item.
     * if it doesn't exist, it returns 'nothing'.
     */
    public Item getItem(String itemName)
    {
        Item thisItem = new Item("nothing",0); 
        for (int i = 0; i < items.size(); i++){ // look through every item
            Item currentItem = items.get(i);
            if (itemName.equals(currentItem.getName())){ // if this is the item we're looking for
                thisItem = currentItem;
            }
        }
        
        return thisItem;
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
        for(String exit : keys) { //returns all the exits possible
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
     * returns every exit possible of the room.
     */
    public HashMap returnExits()
    {
        return exits;
    }
}

