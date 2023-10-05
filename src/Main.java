public class Main {
    /** 첫번째 depth (메인메뉴) 선택 값 */
    public static int mainMenu;
    /** 두번째 depth (관리메뉴) 선택 값 */
    public static int manageMenu;
    /** 세번째 depth (변경메뉴) 선택 값 */
    public static int editMenu;


    //=============================================================================
    public static void main(String[] args) {
        LectureManager lectureManager = new LectureManager();
        StudentManager studentManager = new StudentManager();

        while (mainMenu != 3){
            MainMenuHandler.handle();
        }

        ScannerInstance instance = ScannerInstance.getInstance();
        instance.dispose();
    }
}
