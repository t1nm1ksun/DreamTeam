import java.util.Arrays;

public class Main {
    
    /**
     * 첫번째 depth (메인메뉴) 선택 값
     */
    public static int mainMenu;
    /**
     * 두번째 depth (관리메뉴) 선택 값
     */
    public static int manageMenu;
    /**
     * 세번째 depth (변경메뉴) 선택 값
     */
    public static int editMenu;

    public static LectureManager lectureManager = new LectureManager();
    public static StudentManager studentManager = new StudentManager();
    public static SubjectManager subjectManager = new SubjectManager();
    private Read read = new Read();
    public static void main(String[] args) {

        if (!lectureManager.checkSameID() || !studentManager.checkSameID()) {
            return; //id체크후 다를시종료
        }
        if(!Read.validateCSVListFormat(Arrays.asList(subjectManager))){
            return;
        }
        //TODO 학생,타임테이블체크도 추가

        while (mainMenu != 3) {
            MainMenuHandler.handle(lectureManager, studentManager);
        }
        lectureManager.saveDataFile();
        studentManager.saveDataFile();


        ScannerUtils.print("프로그램이 종료되었습니다.", true);

        ScannerInstance instance = ScannerInstance.getInstance();
        instance.dispose();
    }
}
