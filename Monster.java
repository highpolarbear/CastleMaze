
/**
 * Class Monster
 * A monster inside of the game.
 * 
 * Each 'monster' has a name attributed to it and can attack.
 *
 * @author Terry Phung
 * 
 * Assignment 2 - Castle Maze.
 * Terry Phung
 *
 */
public class Monster
{
    // instance variables - replace the example below with your own
    private String name;
    private int attackPower;

    /**
     * Takes parameter name and attack power,
     * Sets it to Monster.
     */
    public Monster(String name, int attackPower)
    {
        // initialise instance variables
        this.name = name;
        this.attackPower = attackPower;
    }

    /**
     * Returns the name of the Monster
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the attack power of the monster.
     */
    public int attack()
    {
        return attackPower;
    }
}
