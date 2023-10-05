import java.util.Scanner;

public class LectureEditMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();
    public static String input = "";
    public static void handle(){
//        while(true) {
            switch (Main.editMenu) {
                case 1 -> {
                    LectureManager.editDate();
//                clearManageMenu();
                }
                case 2 -> {
                    break;
                }
                default -> {
                    ScannerUtils.print("변경할 수업 코드를 선택하시오", true);
                    input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_ID, CommonPatternError.LECTURE_ID);
                    ScannerUtils.print("[1. 수업 시간 변경 2. 나가기]", true);
                    ScannerUtils.print("메뉴를 입력하세요", true);
                    Main.editMenu = Integer.parseInt(ScannerUtils.scanWithPattern(CommonPattern.LECTURE_MENU, CommonPatternError.LECTURE_MENU));
                }
            }
//            clearManageMenu();
//        }
    }

    public static void clearManageMenu(){
        Main.manageMenu = -1;
    }
}
