public class DivisionEditMenuHandler {
    public static String input = "";

    public static boolean handle(DivisionManager divisionManager) {
        switch (Main.divisionEditMenu) {
            case 1:
                divisionManager.editDate();
                clearEditMenu();
                break;
            case 2:
                clearEditMenu();
                break;
            default:

                ScannerUtils.print("[1. 분반 시간 변경 2. 나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요: ", false);
                Main.divisionEditMenu = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.TWO_CHOICE,
                        CommonPatternError.TWO_CHOICE);
                if (Main.divisionEditMenu == 1) {
                    if (!divisionManager.displayDivisions()) {
                        return false;
                    }
                    handle(divisionManager);
                }
                clearEditMenu();
        }

        return true;
    }

    public static void clearEditMenu() {
        Main.divisionEditMenu = -1;
    }
}
