import java.util.Random;

public class Die {
    Random random = new Random();

    public int rollDice(){
        return random.nextInt(1, 7);
    }
}
