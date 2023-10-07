import java.util.Scanner;

public class LectureEditMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();
    public static String input = "";

    public static boolean handle(LectureManager lecturemanager) {
        switch (Main.editMenu) {
            case 1:
                lecturemanager.editDate(input);
                clearEditMenu();
                break;
            case 2:
                clearEditMenu();
                break;
            default:
                if(!lecturemanager.displayLectures()) return false;
                ScannerUtils.print("[1. 수업 시간 변경 2. 나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                Main.editMenu = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.TWO_CHOICE, CommonPatternError.TWO_CHOICE);
                if(Main.editMenu == 1) handle(lecturemanager);
                clearEditMenu();
        }
        return true;
    }

    public static void clearEditMenu() {
        Main.editMenu = -1;
    }
}
