import java.util.Scanner;
//master branch
public class Main {

    //전역변수 선언 =================================================================
<<<<<<< HEAD
    public static String choice; //메인 메뉴
    public static String classMenu; //수업 관리 메뉴
    public static String studentMenu; //학생 관리 메뉴
    public static Scanner scanner = new Scanner(System.in); // 스캐너를 클래스 변수로 선언하고 한 번 열기
    //=============================================================================
    public static void main(String[] args) {
        LectureManager Lm = new LectureManager();
        StudentManager Sm = new StudentManager();
//        Lm.deleteLecture("1000");
=======
    public static int choice; //메인 메뉴
    public static int classMenu; //수업 관리 메뉴
    public static int studentMenu; //학생 관리 메뉴


    //=============================================================================
    public static void main(String[] args) {
        LectureManager Lm = new LectureManager();
>>>>>>> 90b175db37dad50a2cbdaec0a06c85a3ab7fcff7
        Lm.addLecture();
        Lm.displayLectures();
        ScannerInstance instance = ScannerInstance.getInstance();
        instance.dispose();
    }
}