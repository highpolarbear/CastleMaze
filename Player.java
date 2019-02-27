import java.util.*;

/**
 * class Player.
 * 
 * This class is part of the Castle Maze game.
 * 
 * A "Player" is the player character of the game, it holds
 * the items that the player has picked up, the weight of all the items
 * and the player's health points.
 * 
 * It is initialised in the Game class for each new Game.
 *
 * @author Terry Phung
 * @version 1
 */
public class Player
{
    private int health;
    private int bagWeight;
    private ArrayList<Item> bag;
    private int maxBagWeight = 35;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        health = 100;
        bag = new ArrayList<>();
        bagWeight = 0;
    }
    
    /**
     * Returns the player's current health points.
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * Returns the player's current bag weight.
     */
    public int getBagWeight()
    {
        return bagWeight;
    }
    
    /**
     * Adds an item to the bag of the player.
     */
    public void addItem(Item item)
    {

        bag.add(item);
        addBagWeight(item.getWeight());

    }
    
    /**
     * Returns the maximum bag weight set of the player.
     */
    public int getMaxBagWeight()
    {
      return maxBagWeight;
    }
    
    /**
     * Player gains health, and set how many health points the player gains.
     */
    public void gainHealth(int gain)
    {
        health += gain;
    }

    /**
     * Player loses health, and set how many health points the player loses.
     */
    public void loseHealth(int loss)
    {
        health -= loss;
    }

    /**
     * Gets the player's status: health, bag weight and its contents in a String.
     */
    public String getStatus()
    {
        return ("Your Health : " + health + ", Your Bag weights : " + bagWeight
                + ", Your Bag contains : " + getBagContents());
    }

    /**
     * Check if player has a certain item, tries to find the item in player's bag and returns the item itself.
     */
    public Item itemExist(String item)
    {
        Item findItem = null;
        //go through every item in the bag 
        for (int i = 0; i < bag.size(); i++){
            if (item.equals(bag.get(i).getName())){ //if it's the item we're looking for
                    findItem = bag.get(i);
                }
        }
        return findItem;
    }

    /**
     * Removes an item from player, by looking if it exist inside of player's bag.
     */
    public void removeItem(Item item)
    {
        boolean removed = false;
        for (int i = 0; i < bag.size(); i ++){ // go through every item
            if (item.equals(bag.get(i)) && removed == false){ // if this is the item to remove
                bagWeight -= bag.get(i).getWeight();
                bag.remove(i); //remove it.

                removed = true;
            }
        }
    }

    /**
     * Get the contents of player's bag.
     */
    private String getBagContents()
    {
        String printString = "";
        if (bag.size() > 0){
            for(int i = 0 ; i < bag.size(); i++){ //go through every item
                printString += bag.get(i).getName(); // get its name
                if (i < bag.size() - 1 ){
                    printString += " , ";
                }
            }
        }
        else{
            printString += "nothing";
        }
        return printString;
    }

    /**
     * adds weight to the player's bag.
     */
    private void addBagWeight(int weight)
    {
        bagWeight += weight;
    }
}
