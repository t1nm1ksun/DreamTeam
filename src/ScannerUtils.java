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
        Scanner scanner = new Scanner(System.in);

        while(true){
            String inputText = scanner.next();

            if(RegexUtils.checkIsMatchesString(pattern, inputText)){
                scanner.close();
                return inputText;
            }

            ScannerUtils.print(error, true);
        }
    }

    /**
     *  int 값을 입력받는 스캐너
     *  pattern의 regex가 맞다면 값을 리턴 받고, 틀리다면 error를 내뿜으며 다시 스캔을 받는다.
     *
     * @param: {@List<Integer> whiteList} 허용되는 integer 값들의 리스트를 넣어줌.
     * @param: {@String error} CommonPatternError의 프로퍼티를 사용하여, 패턴에 맞지 않았을 때 출력할 에러메시지 적용
     *
     * @return: 정상적으로 입력받은 int 값
     * */
    static int scanWithListWhitelist(List<Integer> whiteList, String error){
        Scanner scanner = new Scanner(System.in);

        while(true){
            int inputNumber = scanner.nextInt();
            if(whiteList.contains(inputNumber)){
                scanner.close();
                return inputNumber;
            }
            ScannerUtils.print(error, true);
        }
    }
}