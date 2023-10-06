public class Battlefield {

    // Prints all fields and troop positions
    public static void showBattlefield(Troop player, Troop enemy){
        for (int i = -10; i < 11; i++) {
        if (i == player.getPosition()) {
            System.out.print("\u001B[32m" + i + "\u001B[0m "); // \u001B[32m sets green color, \u001B[0m resets the color
        } else if (i == enemy.getPosition()) {
            System.out.print("\u001B[31m" + i + "\u001B[0m "); // \u001B[31m sets red color, \u001B[0m resets the color
        } else {
            // Print other elements in default color
            System.out.print(i + " ");
        }
    }}
    //Gets distance between two positions
    public static int getDistance(int position1, int position2){
        return Math.abs(position1 - position2);
    }
}
