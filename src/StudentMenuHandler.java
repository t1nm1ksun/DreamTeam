import java.util.Scanner;

public class StudentMenuHandler {
    ScannerInstance instance = ScannerInstance.getInstance();
    Scanner scanner = instance.getScanner();

    public static void handle(StudentManager studentManager){
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
            default -> {
                ScannerUtils.print("[1. 조회 2. 등록 3. 편집 4. 삭제]", true);
                ScannerUtils.print("메뉴를 입력하세요", true);
                Main.manageMenu = ScannerUtils.scanWithPatternInteger(CommonPattern.FOUR_CHOICE, CommonPatternError.FOUR_CHOICE);
            }
        }
    }

    public static void clearManageMenu(){
        Main.manageMenu = -1;
    }
}