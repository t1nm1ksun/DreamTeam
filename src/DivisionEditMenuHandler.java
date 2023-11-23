public class DivisionEditMenuHandler {
    public static String input = "";

    public static boolean handle(DivisionManager divisionmanager) {
        switch (Main.editMenu) {
            case 1:
                divisionmanager.editDate();
                clearEditMenu();
                break;
            case 2:
                clearEditMenu();
                break;
            default:

                ScannerUtils.print("[1. 분반 시간 변경 2. 나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요: ", false);
                Main.editMenu = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.TWO_CHOICE,
                        CommonPatternError.TWO_CHOICE);
                if (Main.editMenu == 1) {
                    if (!divisionmanager.displayDivisions()) {
                        return false;
                    }
                    handle(divisionmanager);
                }
                clearEditMenu();
        }
        return true;
    }

    public static void clearEditMenu() {
        Main.editMenu = -1;
    }
}