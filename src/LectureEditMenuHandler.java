public class LectureEditMenuHandler {
    public static String input = "";

    public static boolean handle(LectureManager lecturemanager) {
        switch (Main.editMenu) {
            case 1:
                lecturemanager.editDate();
                clearEditMenu();
                break;
            case 2:
                clearEditMenu();
                break;
            default:

                ScannerUtils.print("[1. 수업 시간 변경 2. 나가기]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                Main.editMenu = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.TWO_CHOICE,
                        CommonPatternError.TWO_CHOICE);
                if (Main.editMenu == 1) {
                    if (!lecturemanager.displayLectures()) {
                        return false;
                    }
                    handle(lecturemanager);
                }
                clearEditMenu();
        }
        return true;
    }

    public static void clearEditMenu() {
        Main.editMenu = -1;
    }
}
