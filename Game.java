import java.util.*;

/**
 *  This class is the main class of the Castle Maze game.
 *  CastleMaze is a very simple, text based adventure game.  Users
 *  can walk around some scenery. Users can pick up item and iteract with them,
 *  as it is needed in order to win the game. 
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 * Terry Phung
 *
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> visitedRoom;
    private ArrayList<Room> roomList;
    private Player currentPlayer;
    private Item sword, potion, poison, table, chair, shelf, key, letter;
    private Monster bigMonster, smallMonster, runningMonster, walkingMonster;
    private Room transporter, courtyard;
    private boolean gate;
    private Random random;
    private boolean alive;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        //creates all the elements in the game
        createItems();
        createMonsters();
        createRooms();
        gate = false;

        parser = new Parser();
        
        //initialises the player and its stats : health, bag and max bag weight.
        currentPlayer = new Player();
        alive = true; 
        //initialises the random class for transporter room use.
        random = new Random();

    }

    /**
     * Creates all the existing types of items in the map.
     */
    private void createItems()
    {
        //creates existing types of items in the map. 
        //Also creates the text attached to the items.
        
        sword = new Item("sword",20);
        potion = new Item("potion",5);
        poison = new Item("poison",5);
        table = new Item("table",200);
        chair = new Item("chair",150);
        shelf = new Item("shelf",300);
        key = new Item("key",5);
        letter = new Item("letter",1);
        
        letter.setText("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"\n" +
                       " November 30th 1970, " + "\n" +
                       "\n" +
                       " It's a trap .. this place is a maze..  " + "\n" +
                       " \n" +
                       " You're going to make it out if you perservere..." + "\n" +
                       " You must have woken up in confusion .. that is " + "\n" +
                       " because the demon has trapped you here.. You have" + "\n" +
                       " to find the KEY.. it's rumored to be at the great hall.." + "\n" +
                       " There's a gate at the courtyard.. find your key and use it" + "\n" +
                       " there to unlock the gate back to the real world ..." + "\n" +
                       " Take the sword that I have left .. it's been enchanted to " + "\n" +
                       " kill the monsters here.. hit them once and they die .." + "\n" +
                       " \n" +
                       "  - Best of luck to whoever it may concern - " + "\n" +
                       " Bernard Rezet - an escapee"+"\n" +
                       "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                       
        potion.setText(" Ordinary potion" + "\n" +
                       " sounds dodgy but it works ..");
                       
        sword.setText(" Metal Sword - made in china");
        
        key.setText("The cursed key - opens the gate back to the real world ");
    }

    /**
     * Creates all the existing monsters of type Monster in the game.
     */
    private void createMonsters()
    {
        //creates the different types of monsters and their attack power
        bigMonster = new Monster("huge Monster",40);
        smallMonster = new Monster("small Monster",20);
        runningMonster = new Monster("a running Monster",35);
        walkingMonster = new Monster("a walking Monster",35);
    }

    /**
     * Create all the rooms, link their exits together, and add content to the rooms.
     */
    private void createRooms()
    {
        Room hallway, spawn, kitchen, tower, livingQuarters, greatHall, guardRoom,
                            armoury, reception, bathroom, southHallway;

        // create all the rooms
        spawn = new Room ("in a chamber.");
        hallway = new Room ("in an old hallway .. somehow it's clean");
        kitchen = new Room ("in an abandoned kitchen .. there are still unwashed dishes..");
        tower = new Room ("at the Ground floor of the castle tower.. the stairs disintegrated .. ");
        livingQuarters = new Room ("in thie living Quarters. There's a comfy couch !");
        greatHall = new Room ("at The Great Hall.. There's the key !");
        bathroom = new Room ("in Just a bathroom");
        guardRoom = new Room ("at a guard room with a dead guard");
        armoury = new Room ("in a place full of rusted swords ...");
        reception = new Room ("at the reception .. This leads to the outside !");
        courtyard = new Room ("at the courtyard.. This courtyard has a gate ! ");
        southHallway = new Room ("in a dusty hallway ... ");
        transporter = new Room ("in a magical room .. somehow you're being transported ..." + "\n");
        

        // initialise room exits and item locations
        transporter.setExit("none !",transporter);

        spawn.setExit("south", hallway);
        spawn.setItem(sword);
        spawn.setItem(potion);
        spawn.setItem(chair);
        spawn.setItem(table);
        spawn.setItem(letter);
       
        hallway.setExit("north", spawn);
        hallway.setExit("west", kitchen);
        hallway.setExit("east", tower);
        hallway.setExit("south", transporter);
        hallway.setItem(chair);
        hallway.setMonster(bigMonster);

        kitchen.setExit("east", hallway);
        kitchen.setExit("south",bathroom);
        kitchen.setItem(potion);
        kitchen.setItem(chair);

        tower.setExit("west",hallway);
        tower.setExit("south",livingQuarters);
        tower.setItem(potion);
        tower.setItem(potion);
        tower.setItem(chair);
        tower.setMonster(smallMonster);

        bathroom.setExit("north",kitchen);
        bathroom.setExit("east",greatHall);
        bathroom.setExit("south",southHallway);
        bathroom.setItem(poison);

        greatHall.setExit("west",bathroom);
        greatHall.setExit("east",livingQuarters);
        greatHall.setItem(potion);
        greatHall.setItem(key);

        livingQuarters.setExit("west",greatHall);
        livingQuarters.setExit("south",guardRoom);
        livingQuarters.setExit("north",tower);

        guardRoom.setExit("north",livingQuarters);
        guardRoom.setExit("west",armoury);
        guardRoom.setItem(potion);
        guardRoom.setItem(sword);
        guardRoom.setItem(shelf);

        armoury.setExit("east",guardRoom);
        armoury.setExit("west",southHallway);

        southHallway.setExit("east",armoury);
        southHallway.setExit("north",bathroom);
        southHallway.setExit("south",reception);

        reception.setExit("north",southHallway);
        reception.setExit("outside",courtyard);

        courtyard.setExit("inside",reception);

        currentRoom = spawn;  // game start location : at the spawn.
        
        // initialises the stack to access the visited rooms if player wants to go 'back'
        visitedRoom = new Stack<Room>();
        visitedRoom.push(currentRoom);
        
        // adds all the existing rooms to an array list for further use.
        roomList = new ArrayList<>();

        roomList.add(hallway);
        roomList.add(kitchen);
        roomList.add(tower);
        roomList.add(livingQuarters);
        roomList.add(greatHall);
        roomList.add(bathroom);
        roomList.add(guardRoom);
        roomList.add(armoury);
        roomList.add(reception);
        roomList.add(courtyard);
        roomList.add(southHallway);
        roomList.add(transporter);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        //prints the first launch text.
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished && alive) { // if player alive and game not finished
            Command command = parser.getCommand();
            finished = processCommand(command);
            checkPlayerAlive(); //check player's health status after each command.
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Monster Castle");
        System.out.println();
        System.out.println("After a wild night out, you find yourself in a castle.");
        System.out.println("You hear sounds of monsters ...");
        System.out.println("You're trapped here ! find your way out back to the real world...");
        System.out.println("There's a letter on the floor.");
        System.out.println();
        System.out.println("There's a sword on the ground, pick it up by typing 'take sword' !");
        System.out.println();
        printGame(); // prints the game and player status with its bag contents..
    }
    
    /**
     * Print out the game status :
     * The room the player is in, the items there are in the room 
     * and if there's a monster, prints out a monster.
     */
    private void printGame()
    {
        //prints the current room description
        System.out.println(currentRoom.getLongDescription());

        //checks if there's items in a room. Prints out all the items if there are.
        if (currentRoom.getItemList() != null)
         {
            System.out.println();
            System.out.println(currentRoom.getItemList()); //gets all the items that the room has and prints it to player.
        }

        System.out.println();
        printPlayerStatus(); // prints the player's status

        //check if there's a monster in a room, if there is then prints out the monster.
        if (currentRoom.getMonster() != null)
        {
            System.out.println("There's a " + currentRoom.getMonster().getName() + "\n");
            System.out.println(" ~ [ 0`_0`] ~ "+ "\n");
            System.out.println("you can 'fight' the 'monster' using your 'sword' ");
        }
    }
    
    /**
     * Print out player status : his health points, bag contents and the bag's weight.
     */
    private void printPlayerStatus()
    {
        System.out.println(currentPlayer.getStatus());
    }
    
    /**
     * check if player's health is above 0, if it isn't then the player is 
     * dead and the game ends, by assigning true or false to variable alive.
     */
    private void checkPlayerAlive()
    {
        if (currentPlayer.getHealth() > 0 ){
            alive = true;
        }
        else if (currentPlayer.getHealth() < 0 ){
            alive = false;
        }
    }
    
    /**
     * Method used when player uses the 'use item' command,
     * Checks if Item can be taken and checks if player's bag
     * has weight for the item.
     */
    private void playerTakeItem(Command command)
    {
       //gets the input of the item's name as String and checks if there's this item
       // if there is then it returns the item itself to thisItem as a type Item.
       String itemName = command.getSecondWord();
       Item thisItem = currentRoom.getItem(itemName);
       
       //gets the maximum weight.
       int maxBagWeight = currentPlayer.getMaxBagWeight();
       
       //checks if the player can take the item and if their bag has weight allowance for it.
       if (thisItem.getWeight() + currentPlayer.getBagWeight() <= maxBagWeight){ 
         if (command.hasSecondWord() && thisItem.getName() != "nothing" ){  // if the item exists.
             //removes item from room to place it in player's bag.
             currentRoom.removeItem(thisItem);
             currentPlayer.addItem(thisItem);
             System.out.println("you've taken " + thisItem.getName());
         }
         else{
             System.out.println("You've taken nothing at all");
          }
        }

        //if their bag is full.
        else if (thisItem.getWeight() + currentPlayer.getBagWeight() > maxBagWeight & thisItem.getWeight() < 100){
          System.out.println("Your bag can't take more weight. your maximum weight is " + maxBagWeight);
        }
        
        //if the item isn't takeable.
        else if (thisItem.getWeight() > 100){
          System.out.println("You can't take this item");
        }
        
       //updates and prints the player's status.
       printPlayerStatus();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;
        
        // unknown command
        if(command.isUnknown()) { 
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        
        //help command
        if (commandWord.equals("help")) { 
            printHelp();
        }
        
        //go room command
        else if (commandWord.equals("go")) {
            //check if the player can walk away.. if there's a monster then they have to kill it first.
            if(currentRoom.hasMonster()){
                System.out.println("A growling monster is in front of you, you can't walk away...");
            }
            
            //checks if the command is go back.
            else if(command.getSecondWord().equals("back")){
                moveAroundMonster();
                goBack(command);
                if (currentRoom.getMonster() != null){
                    monsterAttack();
                }
            }
            
            // go to the indicated room
            else{
                moveAroundMonster();
                goRoom(command);
                
                //monster lands the first attack if there's a monster in a certain room.
                if (currentRoom.getMonster() != null)
                {
                    monsterAttack();
                }

            }
        }
        
        // if player types back without 'go'. does the same as go back
        else if (commandWord.equals("back")){
            if(currentRoom.hasMonster()){
                System.out.println("A growling monster is in front of you, you can't walk away...");
            }
            else if (currentRoom.getMonster() != null){
                    monsterAttack();
                }
            else {
                moveAroundMonster();
                goBack(command);
            }
        }
        
        //take a specified item
        else if (commandWord.equals("take")){
            playerTakeItem(command);
        }
        
        //quits the game
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        
        //fights the enemy
        else if (commandWord.equals("fight")) {
            fightMonster(command);
        }
        
        //use a specified item.. checks if the gate is open.. 
        else if (commandWord.equals("use")) {
            useItem(command);
            if (gate){
                wantToQuit = true;
            }
        }
        
        else if (commandWord.equals("read")){
            readItem(command);
        }
        // else command not recognised.
        return wantToQuit;
    }
    
    /**
     * Makes the monsters in the game move around to neighbouring rooms,
     * Takes the monster in a room, fetches the neighbouring rooms,
     * looks at their exits and assigns the possible rooms to an arrayList
     * then randomly select an item from this arrayList,
     * and set the monster to that location.
     */
    private void moveAroundMonster()
    {
        //for each room
        for(Room room : roomList){
            //if this room has a monster
            if (room.hasMonster()){
                
                //takes the monster, stores it in currentMonster temporarily 
                //and removes it from the room
                Monster currentMonster = room.getMonster();
                room.removeMonster();
                 
                //gets all the possible exits of this room
                HashMap <String, Room> exits = new HashMap<>();
                ArrayList <Room> nextRoomList = new ArrayList<>();
                exits = room.returnExits();
                
                //assigns the possible next rooms to the arrayList nextRoomList
                for (Room exit : exits.values()){
                    nextRoomList.add(exit);
                }
                
                //gets the next room randomly
                int nextRoomIndex = random.nextInt(nextRoomList.size() -1);
                Room nextRoom = nextRoomList.get(nextRoomIndex);
                
                //check if there isn't a monster already in the room or if the room is transporter.
                while (nextRoom.hasMonster() || nextRoom == transporter){
                    nextRoomIndex = random.nextInt(nextRoomList.size() -1);
                    nextRoom = nextRoomList.get(nextRoomIndex);
                }
                
                //moves the temporarily stored monster to this new random room.
                nextRoom.setMonster(currentMonster);
            }
        }

    }
    
    /**
     * Method used when player meets a monster and decides to fight it.
     * Checks if there's a monster in the room. If there is,
     * the input must start with action 'fight' followed by the 'monster ' using item 'sword'
     * > fight monster sword
     * checks the input, and if the input is just fight monster player will simply lose health (not using a sword)
     * if conditions met and the monster is hit with a sword then it will simply die.
     * Also checks if the monster has been already killed.
     */
    private void fightMonster(Command command)
    {
        //boolean to check if monster is alive
        boolean killed = currentRoom.hasKilledMonster();
        
        //if it's alive
        if (currentRoom.hasMonster() && !killed){
            if (command.hasThirdWord() && command.getSecondWord().equals("monster")){
                //check if player has the item specified.
                Item currentItem = currentPlayer.itemExist(command.getThirdWord());

                if (currentItem != null && currentItem.equals(sword)){ //if he has a sword
                    System.out.println("you hit the monster, it dealt 40 damage" + "\n");
                    currentRoom.killMonster();
                    System.out.println("The monster is dead ! ... ");
                    System.out.println("<%%%%|==========>  -[x_x]- " + "\n");
                }

                else{ // if he doesnt have a sword
                    System.out.println("you don't have a sword to fight the monster !");
                    monsterAttack();
                }

                printPlayerStatus();
            }

            else { //wrong command -> the monster attacks.
                System.out.println("i dont understand your command ... you did nothing.");
                monsterAttack();
            }
        }
        else if (command.hasThirdWord() & killed || command.hasSecondWord() && killed){ // if monster is already dead
                System.out.println("Monster is already dead .. !");
            }

        else if ( command.hasSecondWord() && command.getSecondWord().equals("monster") && !killed && !currentRoom.hasMonster()) { // if there aren't any monsters
            System.out.println("there are no monsters in this room");
        }
        else {
            System.out.println(" Unknown command "); //wrong command
        }
    }
    
    /**
     * Method used when player is trying to use an item.
     * Checks if the desired item is in bag,
     * then checks the item type and creates the effect of the item.
     */
    private void useItem(Command command)
    {
        //gets the item specified
        Item currentItem = currentPlayer.itemExist(command.getSecondWord());
        gate = false; // gate is still closed -> game still goes on

        //if the item exists
        if (currentItem != null){
            if (currentItem.equals(potion)){ // if its potion
            
                currentPlayer.gainHealth(20);
                System.out.println("you drank a health potion .. you gained 20 health points");
                currentPlayer.removeItem(potion);
            }

            else if (currentItem.equals(poison)){ //if its poison
                if (command.hasThirdWord() && command.getThirdWord().equals("self")){ //use it on player's self.
                    currentPlayer.loseHealth(40);
                    System.out.println("you drank poison .. outch you lost 40 health points");
                    currentPlayer.removeItem(poison);
                }
                else if (command.hasThirdWord() && command.getThirdWord().equals("monster") && currentRoom.hasMonster()) {//use it on monster
                    currentRoom.killMonster();
                    System.out.println("\n"+" _ [ x - x ] _ " + "\n");
                    System.out.println("you've killed the monster ~ it burned to death from poison");
                    currentPlayer.removeItem(poison);
                }
                else {
                    System.out.println("using it on who ?"); //if not specified on who
                }
            }
            else if (currentItem.equals(sword)){
                //if player tries to use sword. player can only 'fight' using a sword.
                System.out.println("you can only use the 'sword' to 'fight' the 'monster' !"); 
            }

            else if(currentItem.equals(key)){
                if(currentRoom.equals(courtyard)){ 
                    // if the player has the key and is at the room with the gate. 
                    gate = true;
                    System.out.println("\n" + "|| GATE OPEN || "+ "\n"+ "you've reached back to the real world ...");
                    System.out.println("Congratulations ! you've won the game !! " );
                    //game is won
                }
                else{
                    System.out.println("you have to go to the gate to use the key!");
                }
            }
        }

        else {
            //item isn't in bag
            System.out.println("You don't have that item in your bag.");
        }

        if (!gate){
            //if game is still going, print player status.
            printPlayerStatus();
        }
    }
    
    /**
     * When user is trying to read a description of an item, when available.
     */
    private void readItem(Command command)
    {
        Item currentItem = currentPlayer.itemExist(command.getSecondWord());
        //check if item exits
        if(currentItem != null){
            if (currentItem.hasText()){ //check if it has text
                System.out.println(currentItem.getText());
            }
        
            else { // if no  text on item
                System.out.println("nothing is written on the " + currentItem.getName());
            }
        }
        
        else { //reading nothing  
            System.out.println("There's nothing to read...");
        }
        
    }
    
    /**
     * When the monster attacks the player. It deals damage according to
     * how much damage it can deal, which is set when creating it.
     */
    private void monsterAttack() 
    {
        //Monster attacks the player.
        Monster monster = currentRoom.getMonster();
        currentPlayer.loseHealth(monster.attack());
        System.out.println("\n" + "The monster attacked, you lost "
                            + currentRoom.getMonster().attack() + " health points.");
        printPlayerStatus();
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {       
        System.out.println("Your command words are:");
        parser.showCommands();
        
        System.out.println("_______________________________________________________________________" + "\n");
        System.out.println("go 'room' - goes to a room" + "\n");
        System.out.println("back' - goes back to the previous room" + "\n");
        System.out.println("quit - quits game" + "\n");
        System.out.println("help - displays this help" + "\n");
        System.out.println("take 'item' - takes an item and stores it in your bag" + "\n");
        System.out.println("fight 'monster' 'weapon' - fight against your enemy" + "\n");
        System.out.println("use 'item' - use an item from your bag. must be in your bag to use it !" + "\n");
        System.out.println("read 'item' - read something, like a letter .." + "\n");
        System.out.println("_______________________________________________________________________");

    }

    /**
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        //gets the next direction
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        
        //if its the transporter room
        else if(nextRoom == transporter){
            while (nextRoom == transporter){
                nextRoom = randomRoom();
            }
            currentRoom = nextRoom;
            System.out.print("you've been transported to a another location ..");
            System.out.print("\n");
            visitedRoom = new Stack<Room>();
            visitedRoom.push(currentRoom);
            printGame();
        }

        else{ 
            //go to specified room and updates the visited room stack.
            visitedRoom.push(currentRoom);
            currentRoom = nextRoom;
            printGame();
        }
    }
    
    /**
     * method to select a random room from all the available rooms.
     */
    private Room randomRoom()
    {
        Room randomRoom;
        
        //selects a random room
        int randomIndex = random.nextInt(roomList.size()-1);
        randomRoom = roomList.get(randomIndex);

        //returns the random room
        return randomRoom;
    }

    /**
     * Method called when user wants to go back to previous room.
     * Checks if previous room is available before proceeding.
     */
    private void goBack(Command command)
    {
        //check if the command has extra words -> invalid
        if(command.hasThirdWord()){
            System.out.println("back what ?");
        }
        
        //check if there's a room visited before this one
        else if(visitedRoom.size() > 1){
            Room previousRoom = visitedRoom.pop();

            currentRoom = previousRoom;

            printGame();
        }
        
        else{ // back at first room
            System.out.println("You can't go back any further");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
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
}
