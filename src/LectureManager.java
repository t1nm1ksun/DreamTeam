import java.util.ArrayList;
import java.util.List;

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

    public void editLecture(String InputLectureCode) {
        ScannerUtils.print("메뉴를 입력하세요.", true);
        String menu = ScannerUtils.scanWithPattern("^(1|2)$", CommonPatternError.LECTURE_DATE);
        System.out.println(menu + "를 입력받음");
    }

    public void addLecture() {
        SubjectManager sbm = new SubjectManager();
        TeacherManager tm = new TeacherManager();
        String newTeacher = "ㅁ";
        String newSubject = "ㅇ";
        //과목 정보 입력
        //객관식으로 1.수학 2.영어 일때
        String menu = ScannerUtils.scanWithPattern("^(1|2)$", CommonPatternError.LECTURE_DATE);
        if(menu.equals("1")) {
            newSubject = sbm.find("수학");
        } else {
            newSubject = sbm.find("영어");
        }


        //선생님 정보 입력
//        String menu2 = "1";
        String menu2 = ScannerUtils.scanWithPattern("^(1|2)$", CommonPatternError.LECTURE_DATE);
        if(menu2.equals("1")) {
            newTeacher = tm.find("신민석");
        } else {
            newTeacher = tm.find("이기웅");
        }

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
