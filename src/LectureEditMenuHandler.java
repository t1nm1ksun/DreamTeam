import java.util.Scanner;

public class LectureEditMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();

    public static void handle(LectureManager lectureManager){
        switch (Main.editMenu) {
            case 1 -> {
                lectureManager.displayLectures();
                clearManageMenu();
            }
            case 2 -> {
                clearManageMenu();
            }
            default -> {
                ScannerUtils.print("[1. 수업 시간 변경 2. 나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                Main.manageMenu = Integer.parseInt(ScannerUtils.scanWithPattern(CommonPattern.LECTURE_MENU, CommonPatternError.LECTURE_MENU));
            }
        }
    }

    public static void clearManageMenu(){
        Main.manageMenu = -1;
    }
}
