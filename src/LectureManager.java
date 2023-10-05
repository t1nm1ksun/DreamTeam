import java.util.ArrayList;
import java.util.List;

public class LectureManager {

    public static Integer maxCode = 2000;
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료시 저장파일
    private static List<Lecture> lectures = new ArrayList<>(); // 수업 목록을 저장할 리스트
    private Read read = new Read();

    /**
     * csv로부터 읽어온파일들을 순서대로 lectures에 저장
     * 마지막에 한번에 저장하기 위해 saveData에 순차적 저장
     */
    public LectureManager() {
        List<List<String>> list = read.readCsvFile("src/class.csv");
        for(List<String> item : list){
            if(Integer.parseInt(item.get(2)) > maxCode) maxCode = Integer.parseInt(item.get(2));
            Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2), item.get(3),item.get(4));
            lectures.add(l1);
        }
        //파일을 읽어서 수업 class들을 만들기
    }

    // 모든 수업 목록을 조회하는 메서드
    public static void displayLectures() {
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
    public  void deleteLecture() {
        String InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);
        boolean isDeleted = false;
        displayLectures();
        for(Lecture lec : lectures) {
            if(InputLectureCode.equals(lec.getLectureCode())) {
                //delete
//                saveData.remove(Integer.parseInt(InputLectureCode)-2001);
                lectures.remove(lec);
                isDeleted = true;
                break;
            }
        }
        if(!isDeleted) {
            //삭제하고자 하는 강의가 없음
        } else {
            //삭제 성공!
            Integer initCode = 2000;
            for(Lecture lec : lectures) {
                lec.setLectureCode(initCode.toString(initCode++));
            }
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
        ScannerUtils.print("1) 이승범   2) 신민석    3)김창균   4)이기웅 : ", false);
        input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
        if(input.equals("1"))  dataList[1] = tm.find("이승범");
        else if(input.equals("2")) dataList[1] = tm.find("신민석");
        else if(input.equals("3")) dataList[1] = tm.find("김창균");
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

        //lectures 에 해당 lecture add
        Lecture newLec = new Lecture(dataList[0], dataList[1], dataList[2], dataList[3], dataList[4]);
        lectures.add(newLec);
        //saveData에 해당 lecture add
//        String[] data = new String[]{dataList[0], dataList[1], dataList[2], dataList[3], dataList[4]};
//        saveData.add(data);
    }
    //수업 개수가100개 이상일시 등록 맡기위한 용도
    public int LectureSize(){
        return lectures.size();
    }

    public void editLecture() {
        //수업이 존재하는지 판단해야함!!!!1
       LectureEditMenuHandler.handle();
    }

    public static void editDate(String inputCode) {
        ScannerUtils.print("변경할 수업 요일을 선택하세요", true);
        ScannerUtils.print("1. 월수금 2. 화목토", true);
        String newDate = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
        if(newDate.equals("1")) {
            newDate = "월 수 금";
        } else {
            newDate = "화 목 토";
        }

        ScannerUtils.print("변경할 수업 시간을 선택하세요", true);
        ScannerUtils.print("1. 14-16 2. 16-18 3. 18-20 4.20-22", true);
        String newTime = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);

        for (Lecture lec : lectures) {
            if(lec.getLectureCode().equals(inputCode)) {
                lec.setTime(newTime);
                lec.setDayOfWeek(newDate);
            }
        }

    }
    public void saveDataFile() {
        // 저장된 데이터들을 알맞은 형식의 데이터로 전환한 뒤 저업
        for(Lecture lec : lectures) {
            String[] tmpData = {lec.getSubjectCode(), lec.getTeacher(), lec.getLectureCode(), lec.getDayOfWeek(), lec.getTime()};
            saveData.add(tmpData);
        }
        read.writeCSV(saveData);
    }
}
