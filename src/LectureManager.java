import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LectureManager {

    public static Integer maxCode = 2000;
    private int maxLecture = 0; //수업 생성 막기
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일
    private List<Lecture> lectures = new ArrayList<>(); // 수업 목록을 저장할 리스트
    private Read read = new Read();
    private boolean[] timeCheck = new boolean[9];// 이미 고른 시간 체크

    /**
     * csv로부터 읽어온파일들을 순서대로 lectures에 저장
     * 마지막에 한번에 저장하기 위해 saveData에 순차적 저장
     */
    public LectureManager() {
        List<List<String>> list = read.readCSV("src/class.csv");

        for(List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함
            if(Integer.parseInt(item.get(2)) > maxCode) {
                maxCode = Integer.parseInt(item.get(2));
            }

            Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2), item.get(3),item.get(4));
            lectures.add(l1);

            if(item.get(3).equals("월 수 금")) {
                timeCheck[1 * Integer.parseInt(item.get(4))] = true;
            }
            else {
                timeCheck[4 + Integer.parseInt(item.get(4))] = true;
            }
            maxLecture++;
        }
    }

    public Lecture hasLecture(String lectureCode) {
        for(Lecture lecture: lectures) {
            if(lecture.getLectureCode().equals(lectureCode)) {
                return lecture;
            }
        }
        return null;
    }

    // StudentManager에서 학생이 수강 중인 수업을 조회하기 위한 메서드
    public void displayLecture(String lectureCode) {
        if(!lectures.isEmpty() && hasLecture(lectureCode) != null) {
            ScannerUtils.print("|    " + hasLecture(lectureCode).getLectureCode()+"       ", false);
            ScannerUtils.print(hasLecture(lectureCode).getSubjectCode()+"         ", false);
            ScannerUtils.print(hasLecture(lectureCode).getTeacher()+"       ", false);
            ScannerUtils.print(hasLecture(lectureCode).getDayOfWeek()+"      ", false);
            ScannerUtils.print(hasLecture(lectureCode).getTime(), true);
        }
    }

    // 모든 수업 목록을 조회하는 메서드
    public boolean displayLectures() {
        if(lectures.isEmpty()) {
            ScannerUtils.print("등록된 수업이 없습니다.", true);
            return false;
        } else {
            System.out.println("등록된 수업 목록:");
            ScannerUtils.print("수업코드     과목코드     선생님코드    날짜      시간", true);

            for (Lecture lecture : lectures) {
                ScannerUtils.print(lecture.getLectureCode()+"       ", false);
                ScannerUtils.print(lecture.getSubjectCode()+"       ", false);
                ScannerUtils.print(lecture.getTeacher()+"       ", false);
                ScannerUtils.print(lecture.getDayOfWeek()+"     ", false);
                ScannerUtils.print(lecture.getTime(), true);
            }
        }
        return true;
    }
    public boolean deleteLecture() {
        if(!displayLectures()) return false;

        ScannerUtils.print("삭제할 수업 코드를 입력해 주세요: ", false);
        String InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);

        while(Integer.parseInt(InputLectureCode) > maxCode) {
            ScannerUtils.print("존재하지 않는 수업 코드입니다. 다시 입력 바랍니다.", true);
            InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);
        }

        boolean isDeleted = false;

        for(Lecture lec: lectures) {
            //삭제할 강의가 존재한다면 lectures 에서 삭제하고 maxCode를 낮춤
            if(InputLectureCode.equals(lec.getLectureCode())) {
                timeCheck[lec.getDay() * Integer.parseInt(lec.getTime())] = false;
                lectures.remove(lec);
                isDeleted = true;
                maxCode--;
                maxLecture--;
                break;
            }
        }

        if(isDeleted) {
            //삭제가 성공했을시 과목들의 코드를 재할당함
            Integer initCode = 2000;
            for(Lecture lec: lectures) {
                lec.setLectureCode(initCode.toString(initCode++));
            }
        }
        return true;
    }
    public void addLecture() {
        if(maxLecture == 8){
            ScannerUtils.print("수업이 꽉차 수업 추가가 불가능합니다.",true);
        }else {
            SubjectManager sm = new SubjectManager();
            TeacherManager tm = new TeacherManager();
            String[] dataList = new String[5];
            int duplicateTime = 0;

            //과목 정보 입력
            ScannerUtils.print("추가할 과목을 입력해 주세요 ", true);
            for (int i = 0; i < sm.getSubjectss().size(); i++) {
                Subject sj = sm.getSubjectss().get(i);
                ScannerUtils.print((i + 1) + ")" + sj.getName() + "(" + sj.getCode() + ")     ", false);
            }

            String input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_TIME);

            if (input.equals("1")) {
                dataList[0] = sm.find("수학");
            } else {
                dataList[0] = sm.find("영어");
            }

            //선생님 정보 입력
            ScannerUtils.print("추가할 선생님을 입력해 주세요 ", true);

            for (int i = 0; i < tm.getTeachers().size(); i++) {
                Teacher tj = tm.getTeachers().get(i);
                ScannerUtils.print((i + 1) + ")" + tj.getName() + "(" + tj.getCode() + ")     ", false);
            }

            input = ScannerUtils.scanWithPattern(CommonPattern.FOUR_CHOICE, CommonPatternError.FOUR_CHOICE);

            if (input.equals("1")) {
                dataList[1] = tm.find("이승범");
            } else if (input.equals("2")) {
                dataList[1] = tm.find("신민석");
            } else if (input.equals("3")) {
                dataList[1] = tm.find("김창균");
            } else {
                dataList[1] = tm.find("이기웅");
            }

            //수업 코드 동적 할당
            dataList[2] = Integer.toString(++maxCode);

            //요일 정보 입력
            while (true) {
                ScannerUtils.print("수강할 날짜를 입력해주세요", true);
                ScannerUtils.print("1) 월 수 금   2) 화 목 토 : ", false);
                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                duplicateTime = (Integer.parseInt(input) - 1) * 4;
                if (input.equals("1")) dataList[3] = "월 수 금";
                else dataList[3] = "화 목 토";

                //수업 시간 입력
                ScannerUtils.print("수강할 시간을 입력해주세요", true);
                ScannerUtils.print("1) 14:00~16:00   2) 16:00~18:00   3)18:00~20:00    4)20:00~22:00 :  ", false);
                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);
                duplicateTime += Integer.parseInt(input);

                if (!timeCheck[duplicateTime]) {
                    dataList[4] = input;
                    timeCheck[duplicateTime] = true;
                    maxLecture++;
                    break;
                } else {
                    ScannerUtils.print("해당 시간에는 이미 수업이 존재합니다", true);
                }
            }

            //lectures 에 해당 새로운 강의 추가
            Lecture newLec = new Lecture(dataList[0], dataList[1], dataList[2], dataList[3], dataList[4]);
            lectures.add(newLec);
        }
    }

    //수업 개수가 100개 이상일 시 등록 막기 위한 용도
    public int LectureSize(){
        return lectures.size();
    }

    public void editDate(String inputCode) {
        if(maxLecture == 8){
            ScannerUtils.print("수업이 꽉차 수업 시간 변경이 불가능합니다.",true);
        }else {
            ScannerUtils.print("변경할 수업 코드를 선택하시오", true);
            String input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);

            while (Integer.parseInt(input) > LectureManager.maxCode) {
                ScannerUtils.print("존재하지 않습니다. 재입력 바랍니다.", true);
                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);
            }

            String newDate, newTime;
            int duplicateTime;
            //변경할 요일 선택
            while (true) {
                ScannerUtils.print("변경할 수업 요일을 선택하세요", true);
                ScannerUtils.print("1) 월 수 금   2) 화 목 토  : ", true);
                newDate = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                duplicateTime = (Integer.parseInt(newDate) - 1) * 4;
                if (newDate.equals("1")) {
                    newDate = "월 수 금";
                } else {
                    newDate = "화 목 토";
                }

                //변경할 시간 선택
                ScannerUtils.print("변경할 수업 시간을 선택하세요", true);
                ScannerUtils.print("1) 14:00~16:00   2) 16:00~18:00   3)18:00~20:00    4)20:00~22:00 : ", true);
                newTime = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);

                duplicateTime += Integer.parseInt(newTime);

                if (!timeCheck[duplicateTime]) {
                    timeCheck[duplicateTime] = true;
                    break;
                } else {
                    ScannerUtils.print("해당 시간에는 이미 수업이 존재합니다", true);
                }
            }
            //변경할 강의를 찾아서 요일과 시간을 바꿔줌
            for (Lecture lec : lectures) {
                if (lec.getLectureCode().equals(inputCode)) {
                    timeCheck[Integer.parseInt(lec.getTime()) * lec.getDay()] = false;
                    lec.setTime(newTime);
                    lec.setDayOfWeek(newDate);
                }
            }
        }

    }
    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for(Lecture lec : lectures) {
            String[] tmpData = {lec.getSubjectCode(), lec.getTeacher(), lec.getLectureCode(), lec.getDayOfWeek(), lec.getTime()};
            saveData.add(tmpData);
        }
        read.writeCSV(saveData);
    }
    public int getMaxLecture(){ return maxLecture; }
}
