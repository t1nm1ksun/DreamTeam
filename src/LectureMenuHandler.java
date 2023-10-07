import java.util.Scanner;

public class LectureMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();

    public static void handle(LectureManager lectureManager){
        boolean isBreak = false;
        while (!isBreak) {
            switch (Main.manageMenu) {
                case 1 -> {
                    lectureManager.displayLectures();
                    clearManageMenu();
                }
                case 2 -> {
                    lectureManager.displayLectures();
                    LectureEditMenuHandler.handle(lectureManager);
                    ScannerUtils.print("수업이 성공적으로 변경되었습니다!", true);

                    clearManageMenu();
                }
                case 3 -> {
                    lectureManager.addLecture();
                    ScannerUtils.print("수업이 성공적으로 추가되었습니다!", true);

                    clearManageMenu();
                }
                case 4 -> {
                    //TODO: lectureManager 인풋 메서드 내부에서 처리하도록
                    lectureManager.deleteLecture();
                    ScannerUtils.print("수업이 성공적으로 삭제되었습니다!", true);

                    clearManageMenu();
                }
                case 5 -> {
                    clearManageMenu();
                    isBreak = true;
                    Main.mainMenu = -1;
                    break;
                }
                default -> {
                    ScannerUtils.print("[1.조회 2.편집 3.등록 4.삭제 5.나가기]", true);
                    ScannerUtils.print("메뉴를 입력하세요", true);
                    Main.manageMenu = ScannerUtils.scanWithPatternInteger(CommonPattern.FIVE_CHOICE, CommonPatternError.FIVE_CHOICE);
                }
            }
        }
    }

    public static void clearManageMenu(){
        Main.manageMenu = -1;
    }
}
