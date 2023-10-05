import java.util.ArrayList;
import java.util.List;

public class LectureManager {
    //프로그램 종료시 저장파일
    private List<String[]> saveData = new ArrayList<>();
    private List<Lecture> lectures = new ArrayList<>(); // 수업 목록을 저장할 리스트
    private read Read = new read();

    /**
     * csv로부터 읽어온파일들을 순서대로 lectures에 저장
     * 마지막에 한번에 저장하기 위해 saveData에 순차적 저장
     */
    public LectureManager() {
        List<List<String>> list = Read.readCsvFile();
        for(List<String> item : list) {
            Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2), item.get(3),item.get(4));
            lectures.add(l1);
            String[] data = new String[]{item.get(0), item.get(1), item.get(2), item.get(3),item.get(4)};
            saveData.add(data);
        }
        //파일을 읽어서 수업 class들을 만들기
    }

    // 모든 수업 목록을 조회하는 메서드
    public void displayLectures() {
        if (lectures.isEmpty()) {
            ScannerUtils.print("등록된 수업이 없습니다.", true);
        } else {
            System.out.println("등록된 수업 목록:");
            ScannerUtils.print("수업코드     과목코드     선생님코드    날짜      시간", true);
            for (Lecture lecture : lectures) {
                ScannerUtils.print(lecture.getLectureCode()+"       ", false);
                ScannerUtils.print(lecture.getSubjectCode()+"       ", false);
                ScannerUtils.print(lecture.getTeacher()+"       ", false);
                ScannerUtils.print(lecture.getDayOfWeek()+"     ", false);
                ScannerUtils.print(lecture.getTime(), false);
                ScannerUtils.print("", true);
            }
        }
    }
    public  void deleteLecture(String InputLectureCode) {
//        String InputLectureCode = ScannerUtils.scanWithPattern("dfs", "Error 유효한 과목 코드 아님");
        boolean isDeleted = false;
        displayLectures();
        for(Lecture lec : lectures) {
            if(InputLectureCode.equals(lec.getLectureCode())) {
                //delete
                saveData.remove(Integer.parseInt(InputLectureCode)-2001);
                lectures.remove(Integer.parseInt(InputLectureCode)-2001);
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
        String[] dataList = new String[5];
        //과목 정보 입력
        ScannerUtils.print("추가할 과목을 입력해주세요 ", true);
        ScannerUtils.print("1) 수학   2) 영어 : ", false);
        String input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_TIME);
        if(input.equals("1")) dataList[0] = sm.find("수학");
        else dataList[0] = sm.find("영어");

        //선생님 정보 입력
        ScannerUtils.print("추가할 선생 입력해주세요 ", true);
        ScannerUtils.print("1) 신민석   2) 이기웅 : ", false);
        input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
        if(input.equals("1"))  dataList[1] = tm.find("신민석");
        else dataList[1] = tm.find("이기웅");

        //여기서 수업 코드 추가
        dataList[2] = Integer.toString(2001 + lectures.size());

        //요일 정보 입력
        ScannerUtils.print("수강할 날짜를 입력해주세요", true);
        ScannerUtils.print("1) 월 수 금   2) 화 목 토  : ", false);
        input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
        if(input.equals("1"))  dataList[3] = "월 수 금";
        else dataList[3] = "화 목 토";

        //수업 시간 입력
        ScannerUtils.print("수강할 시간을 입력해주세요", true);
        ScannerUtils.print("1) 14:00~16:00   2) 16:00~18:00   3)18:00~20:00    4)20:00~22:00 :  ", false);
        input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);
        dataList[4] = input;

        //데이터 저장 순서 : 과목코드,선생님코드,수업코드,요일,시간
        //lectures 에 해당 lecture add
        Lecture newLec = new Lecture(dataList[0], dataList[1], dataList[2], dataList[3], dataList[4]);
        lectures.add(newLec);
        //saveData에 해당 lecture add
        String[] data = new String[]{dataList[0], dataList[1], dataList[2], dataList[3], dataList[4]};
        saveData.add(data);
    }

    public void saveData() {
        for(Lecture lec : lectures) {
            String[] data = new String[]{lec.getSubjectCode(), lec.getTeacher(), lec.getLectureCode(), lec.getDayOfWeek(), lec.getTime()};
            saveData.add(data);
        }
        // csv에 데이터를 저장함
    }

}
