public class StudentMenuHandler {
    
    public static void handle(StudentManager studentManager) {
        switch (Main.manageMenu) {
            case 1 -> {
                studentManager.showStudentList();
                clearManageMenu();
            }
            case 2 -> {
                studentManager.addStudent();
                clearManageMenu();
            }
            case 3 -> {
                studentManager.editStudent();
                clearManageMenu();
            }
            case 4 -> {
                studentManager.deleteStudent();
                clearManageMenu();
            }
            case 5 -> {
                escapeToMainMenu();
            }
            default -> {
                ScannerUtils.print("[1.조회 2.등록 3.편집 4.삭제 5.나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요: ", false);
                Main.manageMenu = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.FIVE_CHOICE,
                        CommonPatternError.FIVE_CHOICE);
            }
        }
    }

    public static void clearManageMenu() {
        Main.manageMenu = -1;
    }

    public static void escapeToMainMenu() {
        Main.manageMenu = -1;
        Main.mainMenu = -1;
    }
}
