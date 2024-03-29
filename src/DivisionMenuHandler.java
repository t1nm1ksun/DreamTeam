public class DivisionMenuHandler {

    public static void handle(DivisionManager divisionManager) {
        switch (Main.divisionMenu) {
            case 1 -> {
                divisionManager.displayDivisions();
                clearDivisionMenu();
            }
            case 2 -> {
                divisionManager.addDivision();
                clearDivisionMenu();
            }
            case 3 -> {
                divisionManager.editDate();
                clearDivisionMenu();
            }
            case 4 -> {
                if (divisionManager.deleteDivision()) {
                    ScannerUtils.print("수업이 성공적으로 삭제되었습니다!", true);
                }
                clearDivisionMenu();
            }
            case 5 -> {
                escapeToLectureMenu();
            }
            default -> {
                ScannerUtils.print("[1.분반 조회 2.분반 추가 3.분반 시간 관리 4.삭제 5.나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요: ", false);
                Main.divisionMenu = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.FIVE_CHOICE,
                        CommonPatternError.FIVE_CHOICE);
            }
        }
    }

    public static void clearDivisionMenu() {
        Main.divisionMenu = -1;
    }

    public static void escapeToLectureMenu(){
        Main.divisionMenu = -1;
        Main.manageMenu = -1;
    }

  
}