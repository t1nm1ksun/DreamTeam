import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LectureManager {
    private List<Lecture> lectures; // 수업 목록을 저장할 리스트

    public LectureManager() {
        //파일을 읽어서 수업 class들을 만들기
        Lecture l1 = new Lecture("영어", "신민석", "월 수 금", "타임1");
        lectures = new ArrayList<>();
        lectures.add(l1);
    }


    // 모든 수업 목록을 조회하는 메서드
    public void displayLectures() {
        if (lectures.isEmpty()) {
            ScannerUtils.print("등록된 수업이 없습니다.", true);
        } else {
            ScannerUtils.print("등록된 수업 목록:", true);
            for (Lecture lecture : lectures) {
                ScannerUtils.print("과목명 : " + lecture.getSubject(), true);
                ScannerUtils.print("선생님 : " + lecture.getTeacher(), true);
                ScannerUtils.print("요일 : " + lecture.getDayOfWeek(), true);
                ScannerUtils.print("타임 : " + lecture.getTime(), true);
                ScannerUtils.print("", true);
            }
        }
    }



}
