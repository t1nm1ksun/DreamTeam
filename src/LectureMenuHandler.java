public class LectureMenuHandler {

    public static void handle(LectureManager lectureManager) {

        switch (Main.manageMenu) {
            case 1 -> {
                lectureManager.displayLectures();
                clearManageMenu();
            }
            case 2 -> {
                lectureManager.addLecture();
                clearManageMenu();
            }
            case 3 -> {
                LectureEditMenuHandler.handle(lectureManager);
                clearManageMenu();
            }
            case 4 -> {
                if (lectureManager.deleteLecture()) {
                    ScannerUtils.print("수업이 성공적으로 삭제되었습니다!", true);
                }

                clearManageMenu();
            }
            case 5 -> {
                escapeToMainMenu();
            }
            default -> {
                ScannerUtils.print("[1.조회 2.추가 3.편집 4.삭제 5.나가기]", true);
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
