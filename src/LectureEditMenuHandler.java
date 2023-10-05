import java.util.Scanner;

public class LectureEditMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();
    public static String input = "";
    public static void handle(){
        while(true) {
            if(Main.editMenu == 1) {
                LectureManager.editDate(input);
                clearEditMenu();
            } else if(Main.editMenu == 2) {
                clearEditMenu();
                break;
            } else {
                LectureManager.displayLectures();
                ScannerUtils.print("변경할 수업 코드를 선택하시오", true);
                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);
                ScannerUtils.print("[1. 수업 시간 변경 2. 나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                Main.editMenu = Integer.parseInt(ScannerUtils.scanWithPattern(CommonPattern.TWO_CHOICE, CommonPatternError.TWO_CHOICE));
            }
        }
    }

    public static void clearEditMenu(){
        Main.editMenu = -1;
    }
}
