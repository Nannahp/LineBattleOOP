public class RunProgram {
    public static void main(String[] args) {
        new RunProgram().run();
    }
    //Starts program
    private void run() {
        GameManager gameManager = new GameManager();
        gameManager.initializeGame();
        gameManager.playGame();

    }

}
