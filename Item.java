/**
 * Class Item - a Item in the game.
 * 
 * Each 'Item' is an object that the player can take and store and use it.
 * It has a name, a weight and certain items have readable text.
 *
 * @author Terry Phung
 *
 * Assignment 2 - Castle Maze.
 * Terry Phung
 *
 */

public class Item
{
    private String name;
    private int weight;
    private String text;
    
    /**
     * Takes parameter name and weight and sets it to the Item.
     */
    public Item(String name, int weight)
    {
        this.name = name;
        this.weight = weight;
        text = null;
    }
    
    /**
     * Returns the weight of the Item.
     */
    public int getWeight()
    {
        return weight;
    }
     
    /**
     * Returns the name of the Item.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Sets the text of a the item if the item has a readable text.
     */
    public void setText(String speech)
    {
        text = speech;
    }
    
    /**
     *  Returns a boolean value to check if the item has a text.
     */
    public boolean hasText(){
        boolean returnHasText = false;
        
        if (text != null){
            returnHasText = true;
        }
        
        return returnHasText;
    }
    
    /**
     *  Returns the text of the item.
     */
    public String getText(){
        return text;
    }
}
    


