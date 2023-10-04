import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerUtils {
    static void print(String text, boolean hasNewLine){
        if(hasNewLine) System.out.println(text);
        else System.out.print(text);
    }

    static String scanWithPattern(String patten, String error) {
        Scanner scanner = new Scanner(System.in);
        String inputText = scanner.next();
        scanner.close();

        return inputText;
    }

    static int scanWithListWhitelist(List<Integer> whiteList){
        Scanner scanner = new Scanner(System.in);
        int inputText = scanner.nextInt();
        scanner.close();

        if(whiteList.contains(inputText)){
            return inputText;
        }
        return inputText;
    }
}