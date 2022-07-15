import java.util.ArrayList;
import java.util.Random;

public class Computer {
    
    private String name;
    private String difficulty;
    private int numCards;

    private ArrayList<Card> hand;
    private ArrayList<Card> used;
    private ArrayList<Card> hidden;

    public Computer(String name, String difficulty, ArrayList<Card> hand, ArrayList<Card> used, ArrayList<Card> hidden){
        this.name = name;
        this.difficulty = difficulty;
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
            //Assign ID in order of appearence
            card.setId(hand.indexOf(card) + 1);
            
            // Card 1, Card 2, Card 3 <- Hand ArrayList
            // Card 1 -> Set ID -> indexOf Card 1 in Hand = 0 -> 0 + 1 = ID of 1
            // Card 2 -> Set ID -> indexOf Card 2 in Hand = 1 -> 1 + 1 = ID of 2
            // Card 3 -> Set ID -> indexOf Card 3 in Hand = 2 -> 2 + 1 = ID of 3
        }
    }

    public void place(int id){
        
        used.add(hand.get(id - 1));
        hand.remove(id - 1); 
        System.out.println("\n"+name + " placed a");
        System.out.println(used.get(used.size() - 1).toString());
    }

    public int getNumCards(){
        numCards = 0;
        for (Card card : hand) {
            numCards++;
        }
        return numCards;
    }

    public String getDifficulty() {
        return difficulty;
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

        //If most recently played card isn't a +2 and +4, allowing computer to place
        if(!getTop("type", used).equals("+2") && !getTop("type", used).equals("+4"))
        {
            println("\nIt is " + getName() + "'s turn!");
            println("They have " + getNumCards() + " cards remaining!");
            reveal();
            
            //Check for every card in hand if any card matches most recently played card
            for (Card card : hand) {
                
                //If a card matches in color, place card
                if(card.getColour().contains(getTop("colour", used)) || card.getColour().contains("all colours")){
                    place(card.getId());

                    //If WILD card can be placed, randomly select a color to set it to
                    if(card.getColour().contains("all colours")){
                        String[] colours = {"RED", "GREEN", "BLUE", "YELLOW"};
                        Random random = new Random();
                        setColor(used, colours[random.nextInt(4)]);
                        println("\nThe color was set to " + card.getColour());
                    }
                    //Make sure computer doesn't draw card
                    placeable = true;
                    break;
                }
                
                else if(card.getNumber() == getTop(used) && card.getType().equals("number") && getTop("type", used).equals("number"))
                {
                    getTop(used);
                    place(card.getId());
                    //Make sure computer doesn't draw card
                    placeable = true;
                    break;
                }

                else if(card.getType().contains(getTop("type", used)) && !card.getType().contains("number"))
                {
                    place(card.getId());
                    //Make sure computer doesn't draw card
                    placeable = true;
                    break;
                }
                                
            }

            //If no card matches, force draw
            if(!placeable){
                println("\n"+getName() + " doesn't have any cards that you can place, they are forced to take a card!");
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
            
            println(name + " draws " + draw + " cards!");
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