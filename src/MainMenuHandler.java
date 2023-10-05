import java.util.Scanner;

public class MainMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();

    public static void handle(){
        switch (Main.mainMenu) {
            case 1 -> clearMainMenu();
            case 2 -> clearMainMenu();
            case 3 -> clearMainMenu();
            default -> {
                ScannerUtils.print("[1.수업 시간표 관리 2. 학생 관리 3. 종료]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                Main.mainMenu = Integer.parseInt(ScannerUtils.scanWithPattern(CommonPattern.MAIN_MENU, CommonPatternError.MAIN_MENU));
            }
        }
    }

    public static void clearMainMenu(){
        Main.mainMenu = -1;
    }
}