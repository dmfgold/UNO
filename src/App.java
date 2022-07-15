
public class App {
    public static void main(String[] args)
    {
        Game game = new Game();

        println("\nWelcome to UNO Classic!");
        println("-----------------------");
        println("Rules:");
        println("\n1. Cards must have same number and/or color to stack");
        println("2. You cannot stack additive action cards (Can't stack +2 and +2 or +4 and +4)");
        println("3. You can only take one card from the deck if you do not have it in your hand");
        println("3a. If the card taken can be played, it must be played");
        println("3b. If the card taken cannot be played, the turn is skipped");
        println("4. You can only place the colors RED, GREEN, BLUE, AND YELLOW");
        println("\nHave fun!");
        println("-----------------------");
        
        game.play();
    }

    public static void println(String string){
        System.out.println(string);
    }
}
