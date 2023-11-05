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

    public static void main(String[] args) {

        if (!Read.validateCSVListFormat(
                Arrays.asList(subjectManager, timetableManager, lectureManager, lectureroomManager, studentManager,
                        teacherManager))) {
            return;
        }
        if (!Read.validateCSVRef(timetableManager, lectureroomManager, "1", "0")) {
            return;
        }
        if (!Read.validateCSVRef(lectureManager, timetableManager, "+5", "0")) {
            return;
        }
        if (!Read.validateCSVRef(timetableManager, lectureroomManager, "1", "0")) {
            return;// 타임테이블 : -강의실 코드
        }
        if (!Read.validateCSVRef(lectureManager, subjectManager, "1", "0")) {
            return;//수업 : - 과목 코드
        }
        if (!Read.validateCSVRef(lectureManager, teacherManager, "2", "0")) {
            return;//수업 : - 선생님 코드
        }
        if (!Read.validateCSVRef(lectureManager, timetableManager, "+5", "0")) {
            return;//수업 : - 타임테이블 코드
        }
        if (!Read.validateCSVRef(teacherManager, timetableManager, "+3", "0")) {
            return;//선생 : -타임테이블 코드
        }
        if (!Read.validateCSVRef(teacherManager, subjectManager, "2", "0")) {
            return;//선생 : - 과목코드
        }
        if (!Read.validateCSVRef(subjectManager, teacherManager, "0", "2")) {
            return;//선생 : - 과목코드
        }
        if (!Read.validateCSVRef(studentManager, lectureManager, "+3", "0")) {
            return;// 학생: - 수업코드
        }

        if(!Read.validatePhoneNumberDupliacated(Arrays.asList(studentManager))){
            return;
        }
        if(!Read.validateTimetableIdDupliacated(Arrays.asList(lectureManager, teacherManager))){
            return;
        }

        lectureroomManager.makeRooms();
        timetableManager.makeTimetables();
        lectureManager.makeLectures();
        studentManager.makeStudents();
        subjectManager.makeSubjects();
        teacherManager.makeTeachers();

        if (!lectureManager.checkSameID() || !studentManager.checkSameID()) {
            return; //id체크후 다를시종료
        }

        //TODO 학생,타임테이블체크도 추가

        while (mainMenu != 3) {
            MainMenuHandler.handle(lectureManager, studentManager);
        }

        lectureManager.saveDataFile();
        studentManager.saveDataFile();
        timetableManager.saveDataFile();
        teacherManager.saveDataFile();

        ScannerUtils.print("프로그램이 종료되었습니다.", true);

        ScannerInstance instance = ScannerInstance.getInstance();
        instance.dispose();
    }
}
