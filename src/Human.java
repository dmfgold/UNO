import java.util.ArrayList;
import java.util.Scanner;

public class Human {

    private String name;
    private int numCards;
    private ArrayList<Card> hand;
    private ArrayList<Card> used;
    private ArrayList<Card> hidden;
    private Scanner input = new Scanner(System.in);

    public Human(String name, ArrayList<Card> hand, ArrayList<Card> used, ArrayList<Card> hidden){

        this.name = name;
        this.hand = hand;
        this.used = used;
        this.hidden = hidden;

    }

    public void take(){
        hand.add(hidden.get(hidden.size() - 1));
        hidden.remove(hidden.size() - 1);
    }

    public void reveal(){ 
        for (Card card : hand) {
            card.setId(hand.indexOf(card) + 1);
            System.out.println(card);
        }
    }

    public void place(int id){
        used.add(hand.get(id - 1));
        hand.remove(hand.get(id - 1)); 
    }

    public int getNumCards(){
        numCards = 0;
        for (Card card : hand) {
            numCards++;
        }
        return numCards;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void turn(){
        //Initialize starting variables
        boolean placeable = false;
        
        //Human
        double chosenID;
        Card chosenCard = null;

        //If last placed card isn't +2 or +4, allow player to place
        if(!getTop("type", used).equals("+2") && !getTop("type", used).equals("+4"))
        {
            println("\nIt is " + getName() + "'s turn!");
            reveal();
            
            //Check if every card in the hand matches either the color, the number, or type
            for (Card card : hand) {
                
                //If card matches last placed card color or last placed is a WILD
                if(card.getColour().contains(getTop("colour", used)) || card.getColour().contains("all colours")){
                    placeable = true;
                    break;
                }
                //Number match
                else if(card.getNumber() == getTop(used) && card.getType().equals("number") && getTop("type", used).equals("number"))
                {
                    placeable = true;
                    break;
                }
                //Type match when type is not number
                else if(card.getType().contains(getTop("type", used)) && !card.getType().contains("number"))
                {
                    placeable = true;
                    break;
                }
                //No placeable cards
                else
                {
                    placeable = false;
                }
            
            }
            //If a card is placeable
            if(placeable){

                println("Which card would you like to place?");

                //Select card loop
                while(true){
                
                    //Get card based of ID
                    chosenID = input.nextDouble();
                    input.nextLine();
                    
                    chosenID = Math.round(chosenID);
                    
                    if(chosenID > getHand().size() || chosenID < 1){
                        println("Please enter a whole number between 1 and " + getHand().size());
                    }

                    else{
                        //Select a card
                        for (Card card : hand) {
                            if(card.getId() == chosenID){
                                chosenCard = card;
                                println(chosenCard.toString());
                            }
                        }
                    }
                    
                    // If the color is valid, place
                    if(chosenCard.getColour().contains(getTop("colour", used)) || chosenCard.getColour().contains("all colours"))
                    
                    {
                        //If card is a WILD card, allow user to set card to either RED, GREEN, BLUE, or YELLOW, and place
                        if(chosenCard.getColour().contains("all colours")){
                            place((int) chosenID);
                            println("What color would you like to set the " + chosenCard.getType() + " card to?");
                            while(true)
                            {
                                String colour = input.nextLine();
                                if(!colour.contains("RED") && !colour.contains("BLUE") && !colour.contains("GREEN") && !colour.contains("YELLOW"))
                                {
                                    println("Please choose a valid color (RED, YELLOW, BLUE, GREEN)");
                                }
                                else{
                                    setColor(used, colour);
                                    break;
                                }
                            }
                        }
                        else //Else, if card is just a regular colored card, place
                        {
                            place((int) chosenID);
                        }
                        break;
                    }

                    //If number is valid, place
                    else if(chosenCard.getNumber() == getTop(used) && chosenCard.getType().equals("number") && getTop("type", used).equals("number")){
                        place((int) chosenID);
                        break;
                    }

                    //If type is valid, place
                    else if(chosenCard.getType().contains(getTop("type", used)) && !chosenCard.getType().contains("number"))
                    
                    {
                        place((int) chosenID);
                        break;
                    }                    
        
                    else //Selected card does not match color, type, or number
                    {
                        println("\nThe cards number/color/type does not match, try again!");
                        println("Most recently placed card");

                        //If most recently played card is WILD, only display color
                        if(getTop("type", used).equals("WILD")){
                            println("\nThe color was set to " + getTop("colour", used));
                        } else //else, print entire card
                        {
                            println(getTop("card", used));
                        }
                        println("\nSelect a new card");
                    }
                }


            }
            //Force player to take a card if they don't have a card that matches the most recently played card
            else{
                println("\nYou don't have any cards that you can place, you are forced to take a card!");
                take();
            }
        }

        else if(getTop("type", used).equals("+2") || getTop("type", used).equals("+4")){
            int draw = 0;
            
            if(getTop("type", used).equals("+2")){
                draw = 2;
            }
            else{
                draw = 4;
            }
            
            println("You draw " + draw + " cards!");
            for (int i = 0; i < draw; i++) {
                take();
            }
            used.add(new Card(getTop("colour", used), "WILD"));
        }

    }

    //Set the color of the card on top
    public void setColor(ArrayList<Card> used, String colour){
        getCardTop(used).setColour(colour);
    }

    //Get different string attributes of the card on top
    public String getTop(String attr, ArrayList<Card> used){
        if(attr.contains("colour"))
        {
            return (used.get(used.size() - 1).getColour());
        }
        else if (attr.contains("type")){
            return (used.get(used.size() - 1).getType());
        }
        else if(attr.contains("card")){
            return (used.get(used.size() - 1).toString());
        }
        return "";
    }

    //Get top most card number
    public int getTop(ArrayList<Card> used){
        return (used.get(used.size() - 1).getNumber());
    }

    //Get top most card (object)
    public Card getCardTop(ArrayList<Card> used){
        return (used.get(used.size() - 1));
    }

    //Get the players class type based of index
    public String getPlayer(ArrayList<Object> turns, int index){
        return turns.get(index).getClass().getName();
    }

    public void println(String string){
        System.out.println(string);
    }
}

//Can place system
//Force place system