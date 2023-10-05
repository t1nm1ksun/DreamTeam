import java.util.Scanner;

public class StudentMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();

    public static void handle(){
        switch (Main.manageMenu) {
            case 1 -> clearManageMenu();
            case 2 -> clearManageMenu();
            case 3 -> clearManageMenu();
            default -> {
                ScannerUtils.print("[1. 조회 2. 편집 3. 등록 4. 삭제]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                // TODO: commonPattern, commonPatternError에 값 추가
                Main.manageMenu = Integer.parseInt(ScannerUtils.scanWithPattern(CommonPattern.FOUR_MENU, CommonPatternError.FOUR_MENU));
            }
        }
    }

    public static void clearManageMenu(){
        Main.manageMenu = -1;
    }
}
