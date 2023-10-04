import java.util.ArrayList;
import java.util.List;

public class LectureManager {
    private List<Lecture> lectures = new ArrayList<>(); // 수업 목록을 저장할 리스트
    private read Read = new read();
    public LectureManager() {
        List<List<String>> list = Read.readCsvFile();
        for(List<String> item : list){
            Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2), item.get(3),item.get(4));
            lectures.add(l1);
        }
        //파일을 읽어서 수업 class들을 만들기
    }

    // 모든 수업 목록을 조회하는 메서드
    public void displayLectures() {
        if (lectures.isEmpty()) {
            ScannerUtils.print("등록된 수업이 없습니다.", true);
        } else {
            System.out.println("등록된 수업 목록:");
            for (Lecture lecture : lectures) {
                ScannerUtils.print("수업 코드: " + lecture.getLectureCode(), true);
                ScannerUtils.print("과목 코드: " + lecture.getSubject(), true);
                ScannerUtils.print("선생님 코드: " + lecture.getTeacher(), true);
                ScannerUtils.print("날짜: " + lecture.getDayOfWeek(), true);
                ScannerUtils.print("시간: " + lecture.getTime(), true);
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
    public void addLecture() {
        SubjectManager sm = new SubjectManager();
        TeacherManager tm = new TeacherManager();
        String newSubject = "";
        String newTeacher = "";
        //과목 정보 입력
        ScannerUtils.print("추가할 과목을 입력해주세요 ", true);
        ScannerUtils.print("1) 수학   2) 영어 : ", false);
        String input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_TIME);
        if(input.equals("1")) newSubject = sm.find("수학");
        else newSubject = sm.find("영어");
        //선생님 정보 입력
        ScannerUtils.print("추가할 선생 입력해주세요 ", true);
        ScannerUtils.print("1) 신민석   2) 이기웅 : ", false);
        input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_TIME);
        if(input.equals("1"))  newTeacher = tm.find("신민석");
        else newTeacher = tm.find("이기웅");

        //요일 정보 입력
        String newDay = "월 수 금";
        //수업 시간 입력
        String newTime = "2";

        //lectures 에 해당 lecture add
        //여기서 수업 코드 추가
        String newLecCode = Integer.toString(2001 + lectures.size());
        Lecture newLec = new Lecture(newSubject, newTeacher, newLecCode, newDay, newTime);

        lectures.add(newLec);
    }

}
