import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerUtils {
    static void print(String text, boolean hasNewLine){
        if(hasNewLine) System.out.println(text);
        else System.out.print(text);
    }

    /**
     *  String 값을 입력받는 스캐너.
     *  pattern의 regex가 맞다면 값을 리턴 받고, 틀리다면 error를 내뿜으며 다시 스캔을 받는다.
     *
     * @param: {@String pattern} CommonPattern 의 프로퍼티를 사용하여, 해당 regex를 대입
     * @param: {@String error} CommonPatternError의 프로퍼티를 사용하여, 패턴에 맞지 않았을 때 출력할 에러메시지 적용
     *
     * @return: 정상적으로 입력받은 String 값
     * */
    static String scanWithPattern(String pattern, String error) {
        ScannerInstance instance = ScannerInstance.getInstance();
        Scanner scanner = instance.getScanner();

        while(true){
            String inputText = scanner.nextLine();

            if(RegexUtils.checkIsMatchesString(pattern, inputText)){
                return inputText;
            }

            ScannerUtils.print(error, true);
        }
    }

    static int scanWithPatternInteger(String pattern, String error){
        ScannerInstance instance = ScannerInstance.getInstance();
        Scanner scanner = instance.getScanner();

        while(true){
            String inputText = scanner.nextLine();

            if(!RegexUtils.checkIsNumber(inputText)){
                ScannerUtils.print(error, true);
                continue;
            }

            if(RegexUtils.checkIsMatchesString(pattern, inputText)){
                return Integer.parseInt(inputText);
            }

            ScannerUtils.print(error, true);
        }
    }

    static int scanWithPatternIntegerForMenu(String pattern, String error) {
        ScannerInstance instance = ScannerInstance.getInstance();
        Scanner scanner = instance.getScanner();

        while(true){
            String inputText = scanner.nextLine();

            if(!RegexUtils.checkIsNumber(inputText)){
                ScannerUtils.print(error, true);
                continue;
            }

            if(inputText.equals("0")){
                Main.editMenu = -1;
                Main.mainMenu = -1;
                Main.manageMenu = -1;
                return -1;
            }

            if(RegexUtils.checkIsMatchesString(pattern, inputText)){
                return Integer.parseInt(inputText);
            }

            ScannerUtils.print(error, true);
        }
    }
}