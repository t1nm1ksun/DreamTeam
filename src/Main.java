import java.sql.Time;
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

    public static TimeTableManager timetableManager = new TimeTableManager();
    public static LectureManager lectureManager = new LectureManager();
    public static StudentManager studentManager = new StudentManager();
    public static SubjectManager subjectManager = new SubjectManager();
    public static LectureRoomManager lectureroomManager = new LectureRoomManager();
    public static TeacherManager teacherManager = new TeacherManager();
    private Read read = new Read();
    public static void main(String[] args) {

        if (!lectureManager.checkSameID() || !studentManager.checkSameID()) {
            return; //id체크후 다를시종료
        }
        if(!Read.validateCSVListFormat(Arrays.asList(subjectManager,timetableManager,lectureManager,lectureroomManager,studentManager,teacherManager ))){
            return;
        }
        if(!Read.validateCSVRef(timetableManager, lectureroomManager, "1", "0")) return;
        if(!Read.validateCSVRef(lectureManager, timetableManager, "+5", "0")) return;


        //TODO 학생,타임테이블체크도 추가

        while (mainMenu != 3) {
            MainMenuHandler.handle(lectureManager, studentManager);
        }
        lectureManager.saveDataFile();
        studentManager.saveDataFile();
        timetableManager.saveDataFile();

        ScannerUtils.print("프로그램이 종료되었습니다.", true);

        ScannerInstance instance = ScannerInstance.getInstance();
        instance.dispose();
    }
}
