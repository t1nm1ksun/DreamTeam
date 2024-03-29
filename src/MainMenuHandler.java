public class MainMenuHandler {

    public static void handle(LectureManager lectureManager, StudentManager studentManager, DivisionManager divisionManager) {
        switch (Main.mainMenu) {
            case 1 -> {
                while (Main.mainMenu == 1) {
                    LectureMenuHandler.handle(lectureManager, divisionManager);
                }
            }
            case 2 -> {
                while (Main.mainMenu == 2) {
                    StudentMenuHandler.handle(studentManager);
                }
            }
            case 3 -> {
                clearMainMenu();
            }
            default -> {
                ScannerUtils.print("[1.수업 시간표 관리 2.학생 관리 3.종료]", true);
                ScannerUtils.print("메뉴를 입력하세요: ", false);
                Main.mainMenu = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.THREE_CHOICE,
                        CommonPatternError.THREE_CHOICE);
            }
        }
    }

    public static void clearMainMenu() {
        Main.mainMenu = -1;
    }
}
