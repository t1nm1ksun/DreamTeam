import java.util.Scanner;

public class LectureMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();

    public static void handle(LectureManager lectureManager){
        switch (Main.manageMenu) {
            case 1 -> {
                lectureManager.displayLectures();
                clearManageMenu();
            }
            case 2 -> {
                clearManageMenu();
            }
            case 3 -> {
                lectureManager.addLecture();
                clearManageMenu();
            }
            case 4 -> {
                //TODO: lectureManager 인풋 메서드 내부에서 처리하도록
//                lectureManager.deleteLecture();
                clearManageMenu();
            }
            default -> {
                ScannerUtils.print("[1. 조회 2. 편집 3. 등록 4. 삭제]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                Main.manageMenu = Integer.parseInt(ScannerUtils.scanWithPattern(CommonPattern.LECTURE_MENU, CommonPatternError.LECTURE_MENU));
            }
        }
    }

    public static void clearManageMenu(){
        Main.manageMenu = -1;
    }
}
