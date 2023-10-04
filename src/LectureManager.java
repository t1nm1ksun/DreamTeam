import java.util.ArrayList;
import java.util.List;

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
            System.out.println("등록된 수업이 없습니다.");
        } else {
            System.out.println("등록된 수업 목록:");
            for (Lecture lecture : lectures) {
                System.out.print(lecture.getSubject());
            }
        }
    }



}
