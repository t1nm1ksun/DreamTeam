import java.util.Scanner;

public class MainMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();

    public static void handle(LectureManager lectureManager, StudentManager studentManager){
        switch (Main.mainMenu) {
            case 1 -> {
                while(true){
                    LectureMenuHandler.handle(lectureManager);
                }
            }
            case 2 -> {
                while(true){
                    StudentMenuHandler.handle();
                }
            }
            case 3 -> clearMainMenu();
            default -> {
                ScannerUtils.print("[1.수업 시간표 관리 2. 학생 관리 3. 종료]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                Main.mainMenu = Integer.parseInt(ScannerUtils.scanWithPattern(CommonPattern.THREE_MENU, CommonPatternError.THREE_MENU));
            }
        }
    }

    public static void clearMainMenu(){
        Main.mainMenu = -1;
    }
}