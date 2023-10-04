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
            System.out.println("등록된 수업이 없습니다.");
        } else {
            System.out.println("등록된 수업 목록:");
            for (Lecture lecture : lectures) {
                System.out.print(lecture.getSubject());
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
