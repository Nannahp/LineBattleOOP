import java.util.Scanner;

public class UI {
    static Scanner scanner = new Scanner(System.in);

    public static void printString(String text){
        System.out.println(text);
    }
    public static String askForString(){
        return scanner.nextLine();
    }




}
