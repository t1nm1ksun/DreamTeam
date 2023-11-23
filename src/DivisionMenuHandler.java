public class DivisionMenuHandler {

    public static void handle(DivisionManager divisionManager) {
        System.out.println("입장");
        System.out.println(Main.divisionMenu);
        switch (Main.divisionMenu) {
            case 1 -> {
                System.out.println("조회 시작");
                divisionManager.displayDivisions();
                System.out.println("조회 끝");
                clearManageMenu();
            }
            case 2 -> {
                divisionManager.addDivision();
                clearManageMenu();
            }
            case 3 -> {
                divisionManager.editDate();
                clearManageMenu();
            }
            case 4 -> {
                if (divisionManager.deleteDivision()) {
                    ScannerUtils.print("수업이 성공적으로 삭제되었습니다!", true);
                }

                clearManageMenu();
            }
            case 5 -> {
                escapeToMainMenu();
            }
            default -> {
                System.out.println("반복");
                ScannerUtils.print("[1.분반 조회 2.분반 추가 3.분반 시간 관리 4.삭제 5.나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요: ", false);
                Main.divisionMenu = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.FIVE_CHOICE,
                        CommonPatternError.FIVE_CHOICE);
                System.out.println("input : " + Main.divisionMenu);
            }
        }
    }

    public static void clearManageMenu() {
        Main.divisionMenu = -1;
    }

    public static void escapeToMainMenu() {
        Main.divisionMenu = -1;
        Main.manageMenu = -1;
        Main.mainMenu = -1;
    }
}