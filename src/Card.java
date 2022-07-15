public class Card{
    
    private String colour; 
    private String type;
    private int number; 
    private int id;
    
    public Card(String colour, int number){
        this.colour = colour;
        this.number = number; 
        type = "number";
    }

    public Card(String colour, String type){
        this.colour = colour;
        this.type = type;
    }


    public String getColour(){
        return colour;
    }

    public int getNumber(){
        return number;
    }

    public String getType(){
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

     public String toString(){
        String rep = "";

        
        if (type.contains("+4") || type.contains("WILD") || type.contains("SKIP") || type.contains("+2") || type.contains("REVERSE"))
        {
            rep += "\nCard: " + id;
            rep += "\nType: " + type; 
            rep += "\nColour: " + colour;
        }
        else {
            rep += "\nCard: " + id;
            rep += "\nColour: " + colour;
            rep += "\nNumber: " + number; 
        }
        return rep;
    }
}