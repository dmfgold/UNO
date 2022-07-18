import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


public class Game {
    
    //Initalize variables
    private int index = 0;
    private boolean reverse = false;
    private Scanner input = new Scanner(System.in);
    private double numOfOpponents;

    //Play method - compile all actions together
    public void play(){
        
        //Initalize starting variables
        boolean end = false;
        
        //Hold turn order
        ArrayList<Object> turns = new ArrayList<Object>();      
        
        //Decks
        ArrayList<Card> used = new ArrayList<Card>();
        ArrayList<Card> hidden = new ArrayList<Card>();
        Deck deck = new Deck(hidden, used);
        deck.initializeDeck();
        deck.shuffleDeck();
        deck.addPlayedDeck();
        deck.drawHiddenDeck();

        while(!(getTop("type", used).equals("number"))){
            deck.addPlayedDeck();
            deck.drawHiddenDeck();
        }

        //All Hands used by players
        ArrayList<Card> hand = new ArrayList<Card>();
        ArrayList<Card> cHand1 = new ArrayList<Card>();
        ArrayList<Card> cHand2 = new ArrayList<Card>();
        ArrayList<Card> cHand3 = new ArrayList<Card>();
        ArrayList<Card> cHand4 = new ArrayList<Card>();
        ArrayList<Card> cHand5 = new ArrayList<Card>();

        //Add hands to array for easy access
        ArrayList<ArrayList<Card>> cHands = new ArrayList<>()
        
        {
            {
            add(cHand1);
            add(cHand2);
            add(cHand3);
            add(cHand4);
            add(cHand5);
            }
        };
        
        //Game Start
        println("\nWhat is your name?");
        String name = input.nextLine();

        //Create human object
        Human player = new Human(name, hand, used, hidden);

        //Add them to turn order (starting)
        turns.add(player);

        println("\nHow many players do you want to play against? (Max 5)");

        //Inquire on number of players
        while(true){
            
            numOfOpponents = input.nextDouble();
            input.nextLine();
            
            //Account for doubles
            numOfOpponents = Math.round(numOfOpponents);
            
            if(numOfOpponents > 5 || numOfOpponents < 1){
                println("Please enter a whole number between 1 and 5");
            }

            else
            {
                break;
            }
        }

        println("You will be facing " + Math.round(numOfOpponents) + " computer opponent(s)!"); 

        //Create new Computer objects, assign name and hand, add to turns order (ascending)
        for (int i = 0; i < numOfOpponents; i++) {
            turns.add(new Computer("Computer " + (i+1), "EASY", cHands.get(i), used, hidden));
        }

        //Give every player 7 cards
        for (Object turn : turns) {
            for (int i = 0; i < 7; i++) {
                if(getPlayer(turns, turns.indexOf(turn)) == "Computer"){
            
                    ((Computer)turn).take();
                    
                }
                else if(getPlayer(turns, turns.indexOf(turn)) == "Human")
                {
                    ((Human)turn).take();
                }
            }
        }

        //First card notifciation 
        println("\nThe first card placed is a");
        println(getTop("card", used));

        //Game loop
        while(!end){
            
            //Increment / decrement through play order 
            nextPlayer(turns, used, hidden);

            //If computer / player have 0 cards, win and end loop
            for (Object turn : turns){
                if(getPlayer(turns, turns.indexOf(turn)) == "Computer"){
                    if(((Computer) turn).getNumCards() == 0){
                        println("\n"+((Computer) turn).getName() + " won the game!\n");
                        end = true;
                    }
                }
                else if(getPlayer(turns, turns.indexOf(turn)) == "Human"){
                    if(((Human) turn).getNumCards() == 0){
                        println("\n"+((Human) turn).getName() + " won the game!\n");
                        end = true;
                    }
                }
            }

            if(used.size() == 0){
                deck.transfer();
                deck.shuffleDeck();
            }
        }
    } // End of play

    //When turn
    public void turn(Object player){

        //Human condition
        if(player.getClass().getName().equals("Human")){
            ((Human) player).turn();
            
        }
       
        //Computer Turn
        else if (player.getClass().getName().equals("Computer")){
            ((Computer) player).turn();
        }
    }

    //Get next player
    public void nextPlayer(ArrayList<Object> turns, ArrayList<Card> used, ArrayList<Card> hidden){
        
        if(getPlayer(turns, index) == "Computer"){
            
            turn((turns.get(index)));
            
        }
        else if(getPlayer(turns, index) == "Human")
        {
            turn((turns.get(index)));
        }

        if(getTop("type", used).equals("REVERSE")){
            setReverse();
        }
        if(reverse && index > 0){
            index--;
        }
        else if (reverse && index == 0){
            index = turns.size() - 1;
        }
        else if (!reverse && index < turns.size() - 1){
            index++;
        }
        else if (!reverse && index == turns.size() - 1) {
            index = 0;
        }
        
        //Note: If facing only one opponent, reverse is the same as skip
        if(reverse && getTop("type", used).equals("SKIP") || reverse && getTop("type", used).equals("REVERSE") && numOfOpponents == 1){
            
            if(numOfOpponents == 1){
                if(index == 0){
                    index = turns.size() - 1;
                }
                else{ index--; }
                used.add(new Card(getTop("color", used), "WILD"));
            } 
            else
            {
                if(index == 0){
                    index = turns.size() - 1;
                }
                else{ index--; }
            }
        }
        //Note: If facing only one opponent, reverse is the same as skip
        else if(!reverse && getTop("type", used).equals("SKIP") || !reverse && getTop("type", used).equals("REVERSE") && numOfOpponents == 1){
            if(numOfOpponents == 1){
                if(index == turns.size() - 1){
                    index = 0;
                }
                else{ index++; }
                used.add(new Card(getTop("color", used), "WILD"));
            } 
            else{
                if(index == turns.size() - 1){
                index = 0;
            }
                else{ index++; }
            }
            
        }
    }

    //Checks if current order is reversed, changes based of current stated
    public void setReverse(){
        
        if(reverse){
            reverse = false;
        }
        else{
            reverse = true;
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

    public void print(String string){
        System.out.print(string);
    }

    public void println(int number){
        System.out.println(number);
    }
}