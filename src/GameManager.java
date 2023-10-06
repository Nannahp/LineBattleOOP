import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    Troop player;
    Troop enemy;
    Die dice = new Die();
    Troop currentPlayer;
    boolean gameOver;
    Random random = new Random();

    //Creates players and moves them forward to place them on the field.
    public void initializeGame(){
        this.player = new Troop(PlayerId.PLAYER);
        this.enemy = new Troop(PlayerId.ENEMY);
        UI.showWelcome();
        player.moveForward(dice);
        enemy.moveForward(dice);
        currentPlayer = player;

    }
    // Runs game with a while-loop
    public void playGame(){
        while (!checkWin()) {
            //Battlefield.showBattlefield(player, enemy); Shows positions, mostly for testing
            UI.printString("\n"+currentPlayer.getPlayerId() + "'S TURN");
                String move = initializeMove();
                makeMove(move, currentPlayer);
                currentPlayer = (currentPlayer == player) ? enemy : player;
            }
    }
    // makes player or enemy choose a move and returns the choice as a string that can be evaluated makeMove()
    public String initializeMove(){
        if (currentPlayer == player){
            player.getScout().scout(player, enemy);
            createPlayerOptions();
            return UI.askForString();
        }
        else {return chooseEnemyTurn(createEnemyOptions());}
    }
    //Takes a string and calls the right method to make a move
    public void makeMove(String move, Troop troop) {
        Troop adversary = (troop.getPlayerId() == PlayerId.PLAYER) ? enemy : player;
        switch (move.toLowerCase()) {
            case "f" -> troop.moveForward(dice);
            case "a" -> troop.attack(dice,adversary);
            case "r" -> troop.moveBack(dice);
            case "b" -> troop.getBomb().placeBomb(troop);
            case "d" -> troop.getBomb().detonateBomb(troop, adversary);
            case "s" -> showStats();
            case "q" -> gameOver = troop.surrender();
            default -> UI.printString("This is not the time for jokes, Commander! We are at war! LOOK OUT!");
        }}

    // Creates a list of options that enemy-AI can choose from based on conditions.
    public ArrayList<String> createEnemyOptions(){
        ArrayList<String> availableMoves = new ArrayList<>();
        if (enemy.getPosition() != 10) {
            availableMoves.add("f");
        }
        if (enemy.getPosition() != -10) {
            availableMoves.add("r");
        }
        availableMoves.add("a");
        if (enemy.getInventory()!= null && enemy.getPosition() > 0) {
            availableMoves.add("b");
        }
        if (enemy.getBomb().getIfPlaced()&& Battlefield.getDistance(enemy.getPosition(), enemy.getBomb().getPosition()) > 6 && enemy.getPosition()  < 0) {
            availableMoves.add("d");
        }
        availableMoves.add("s");
        availableMoves.add("q");

        return availableMoves;
    }
    // Shows options for player based on conditions
    public void createPlayerOptions() {
        UI.printString("\nAwaiting orders, sir!");
        if (player.getPosition() != -10) {
            UI.printString("F: Forward!");
        }
        if (player.getPosition()!= 10) {
            UI.printString("R: Retreat");
        }
        UI.printString("A: Attack");
        if (player.getInventory()!=null && player.getPosition() < 0) {
            UI.printString("B: Place Bomb");
        }
        if (player.getBomb().getIfPlaced() && Battlefield.getDistance(player.getPosition(), player.getBomb().getPosition()) > 6 && player.getPosition() > 0) {
            UI.printString("D: Detonate Bomb");
        }
        UI.printString("S: Status\n" +
                "Q: Give up");
    }
    // Ai chooses move based on some simple logic
    public String chooseEnemyTurn(ArrayList<String> options) {
        int playerDistance = Battlefield.getDistance(player.getPosition(), enemy.getPosition());
        int bombDistance = Battlefield.getDistance(player.getPosition(), enemy.getBomb().getPosition());

        if(options.contains("d") && bombDistance <=5 ||options.contains("d") && enemy.getBomb().getPosition() ==10){
            return "d";} //detonate bomb if player is close OR bomb is placed in player camp
        else if (playerDistance <= 3 && enemy.getFirePower() < 150 || enemy.getBomb().getIfPlaced()) {
            return "r"; // Retreat if firepower is low and the player is close. or if bomb has been placed
        } else if(options.contains("b") && random.nextInt(2) ==1){
            return "b"; // place bomb with a slight randomness
        } else if (playerDistance <= 5 && enemy.getFirePower() > 250) {
            return "a"; // Attack if close to the player.
        } else if (options.contains("f")) {
            return "f"; // Move forward if possible.
        } else if (enemy.getSoldiers() < 3 && enemy.getFirePower() < 150){
            return "q"; //Surrenders if only 3 soldiers left and limited firepower.
        } else {
            return "r"; // If all else fails, retreat.
        }
    }
        // Mostly used to show inventory in showStats
        public int evaluateInventory(Troop troop){
            if(troop.getInventory() !=null) return 1;
            else return  0;
        }
        //Mostly used to show bomb-placements in showStats
        public String evaluateBombPlacement(Troop troop){
        if(troop.getBomb().getIfPlaced()) return String.valueOf(troop.getBomb().getPosition());
        else return "Not placed";}

        // Not using UI, because I haven't created a printf method
        public void showStats() {
            System.out.printf("\n%22s %12s", "Player", "Enemy");
            System.out.printf("\nPosition %10d %13d ", player.getPosition(), enemy.getPosition());
            System.out.printf("\nFirepower %9d %13d ", player.getFirePower(), enemy.getFirePower());
            System.out.printf("\nSoldiers %10d %13d ", player.getSoldiers(), enemy.getSoldiers());
            System.out.printf("\nBombs %13d %13d ", evaluateInventory(player), evaluateInventory(enemy));
            if (player.getBomb().getIfPlaced() || enemy.getBomb().getIfPlaced()) {
                System.out.printf("\nBomb positions %4S %13S ", evaluateBombPlacement(player), evaluateBombPlacement(enemy));
            }
            System.out.println();
        }
    public boolean checkWin() {
        if(gameOver) return true;     //Checks if anyone has surrendered
        else if (player.getSoldiers() <= 0) { //if no soldiers left then opponent has won
            UI.printString("\nEnemy has won!\nDEFEAT");
            return true;
        } else if (enemy.getSoldiers() <= 0) {
            System.out.println("\nWe have won the battle, Commander! \nTime to drink!!\nVICTORY");
            return true;
        } else return false;
    }
}

