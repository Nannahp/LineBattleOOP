import java.util.Scanner;

public class UI {
    static Scanner scanner = new Scanner(System.in);

    public static void printString(String text){
        System.out.println(text);
    }
    public static String askForString(){
        return scanner.nextLine();
    }

    public static void showWelcome(){
        System.out.println();
        System.out.println("WELCOME TO LINE-BATTLE! PREPARE YOUR TROOPS!");
        System.out.println();
    }



}
