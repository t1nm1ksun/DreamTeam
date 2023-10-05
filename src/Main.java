import java.util.Scanner;
//master branch
public class Main {

    //전역변수 선언 =================================================================
    public static int choice; //메인 메뉴
    public static int classMenu; //수업 관리 메뉴
    public static int studentMenu; //학생 관리 메뉴


    //=============================================================================
    public static void main(String[] args) {
        LectureManager Lm = new LectureManager();
        Lm.displayLectures();
//        Lm.addLecture();
        Lm.deleteLecture();
        Lm.displayLectures();
//        Lm.saveDatafile();
//        Lm.displayLectures();
        ScannerInstance instance = ScannerInstance.getInstance();
        instance.dispose();
    }
}