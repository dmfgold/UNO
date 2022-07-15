import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    
    //Attributes
    ArrayList<Card> hiddenDeck = new ArrayList<Card>(); 
    ArrayList<Card> playedDeck = new ArrayList<Card>(); 
    ArrayList<Card> transferDeck = new ArrayList<Card>();

    //Constructor
    public Deck(ArrayList <Card> hiddenDeck, ArrayList <Card> playedDeck){

        this.hiddenDeck = hiddenDeck;
        this.playedDeck = playedDeck;
        
    }

    public void initializeDeck(){
       
        String[] Colours = {"BLUE", "GREEN", "YELLOW", "RED"};

        for(int cardDoubler = 0; cardDoubler < 2; cardDoubler++){

            for(int i = 0; i < 11; i++){

                Card blueCard = new Card("BLUE", i);
                Card redCard = new Card("RED", i);
                Card yellowCard = new Card("YELLOW", i);
                Card greenCard = new Card("GREEN", i);

                hiddenDeck.add(blueCard);
                hiddenDeck.add(redCard);
                hiddenDeck.add(yellowCard);
                hiddenDeck.add(greenCard);
                
            }
        
        }
    
         for(int i = 0; i < 4; i++){            
            
            Card plusFour = new Card("all colours", "+4");
            Card wildCard = new Card("all colours", "WILD");

            hiddenDeck.add(plusFour);
            hiddenDeck.add(wildCard);

         }
        

        for(int i = 0; i < 4; i++){

            Card skipCard = new Card(Colours[i], "SKIP");
            Card reverseCard = new Card(Colours[i], "REVERSE");
            Card plusTwo = new Card(Colours[i], "+2");

            hiddenDeck.add(skipCard);
            hiddenDeck.add(reverseCard);
            hiddenDeck.add(plusTwo);   
        }
    }  

    public void drawHiddenDeck(){
        hiddenDeck.remove(hiddenDeck.size() - 1);
    } 

    public void addPlayedDeck(){
        Card card = hiddenDeck.get(hiddenDeck.size() - 1);
        playedDeck.add(card);
    }

    public void transfer() {
        transferDeck.add(playedDeck.get(playedDeck.size() - 1));
        hiddenDeck.addAll(playedDeck); 
        playedDeck.clear();
        drawHiddenDeck();
        playedDeck.addAll(transferDeck);
    }
    
    public void shuffleDeck() {
        Collections.shuffle(hiddenDeck);
    }
}