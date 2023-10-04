import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LectureManager {
    private List<Lecture> lectures; // 수업 목록을 저장할 리스트

    public LectureManager() {
        //파일을 읽어서 수업 class들을 만들기
        Lecture l1 = new Lecture("영어", "신민석", "월 수 금", "1", "1000");
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
                ScannerUtils.print("타임 : " + lecture.getLectureCode(), true);
                ScannerUtils.print("", true);
            }
        }
    }

    public  void deleteLecture(String InputLectureCode) {
//        String InputLectureCode = ScannerUtils.scanWithPattern("dfs", "Error 유효한 과목 코드 아님");
        boolean isDeleted = false;
        displayLectures();
        for(Lecture lec : lectures) {
            if(InputLectureCode == lec.getLectureCode()) {
                //delete
                lectures.remove(lec);
                isDeleted = true;
                break;
            }
        }
        displayLectures();
        if(!isDeleted) {
            //삭제하고자 하는 강의가 없음
        } else {
            //삭제 성공!
        }
    }

    public void editLecture() {

    }

    public void addLecture() {
        //과목 정보 입력
        String newSubject = "수학;";
        //선생님 정보 입력
        String newTeacher = "김창균";
        //요일 정보 입력
        String newDay = "월 수 금;";
        //수업 시간 입력
        String newTime = "2";

        //lectures 에 해당 lecture add
        //여기서 과목 코드 추가
        String newLecCode = Integer.toString(1000 + lectures.size());
        Lecture newLec = new Lecture(newSubject, newTeacher, newDay, newTime, newLecCode);

        lectures.add(newLec);
    }


}
