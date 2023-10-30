import java.util.ArrayList;
import java.util.List;

public class LectureManager {

    public static Integer maxCode = 2000;
    private int maxLecture = 0; //수업 생성 막기
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일
    private List<Lecture> lectures = new ArrayList<>(); // 수업 목록을 저장할 리스트
    private Read read = new Read();
    private boolean[] timeCheck = new boolean[9];// 이미 고른 시간 체크
    SubjectManager sm = new SubjectManager();
    TeacherManager tm = new TeacherManager();
    LectureRoomManager lr = new LectureRoomManager();
    TimeTableManager ttm = new TimeTableManager();

    /**
     * csv로부터 읽어온파일들을 순서대로 lectures에 저장 마지막에 한번에 저장하기 위해 saveData에 순차적 저장
     */
    public LectureManager() {
        List<List<String>> list = read.readCSV("src/lecture.csv");

        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함
            if (Integer.parseInt(item.get(2)) > maxCode) {
                maxCode = Integer.parseInt(item.get(2));
            }
            List<TimeTable> table = new ArrayList<>();
            for (int i = 5; i < item.size(); i++) {
                for (TimeTable t : ttm.geTimetable()) {
                    if (t.getCode().equals(item.get(i))) {
                        table.add(t);
                        break;
                    }
                }
            }//노가다 table 생성 및 초기화..
            Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2), item.get(3), item.get(4), table);
            lectures.add(l1);

            if (item.get(3).equals("월 수 금")) {
                timeCheck[1 * Integer.parseInt(item.get(4))] = true;
            } else {
                timeCheck[4 + Integer.parseInt(item.get(4))] = true;
            }
            maxLecture++;
        }
    }

    public Lecture hasLecture(String lectureCode) {
        for (Lecture lecture : lectures) {
            if (lecture.getLectureCode().equals(lectureCode)) {
                return lecture;
            }
        }
        return null;
    }

    // StudentManager에서 학생이 수강 중인 수업을 조회하기 위한 메서드
    public void displayLecture(String lectureCode) {
        if (!lectures.isEmpty() && hasLecture(lectureCode) != null) {
            ScannerUtils.print("|    " + hasLecture(lectureCode).getLectureCode() + "       ", false);
            ScannerUtils.print(hasLecture(lectureCode).getSubjectCode() + "         ", false);
            ScannerUtils.print(hasLecture(lectureCode).getTeacher() + "       ", false);
            ScannerUtils.print(hasLecture(lectureCode).getDayOfWeek() + "      ", false);
            ScannerUtils.print(hasLecture(lectureCode).getTime(), true);
        }
    }

    // 모든 수업 목록을 조회하는 메서드
    public boolean displayLectures() {
        if (lectures.isEmpty()) {
            ScannerUtils.print("등록된 수업이 없습니다.", true);
            return false;
        } else {
            System.out.println("등록된 수업 목록:");
            ScannerUtils.print("수업코드     과목코드     선생님 ID    강의실, 날짜 및 시간", true);

            for (Lecture lecture : lectures) {
                ScannerUtils.print(lecture.getLectureCode() + "       ", false);
                ScannerUtils.print(lecture.getSubjectCode() + "       ", false);
                ScannerUtils.print(lecture.getTeacher() + "       ", false);
                for (TimeTable t : lecture.getTimetable()) {
                    ScannerUtils.print(
                            t.getCode() + " " + t.getRoomId() + " " + t.getLecture_days() + " " + t.getLectuer_time()
                                    + " / ", false);
                }
                ScannerUtils.print("", true);
            }
        }
        return true;
    }

    public boolean deleteLecture() {
        if (!displayLectures()) {
            return false;
        }

        ScannerUtils.print("삭제할 수업 코드를 입력해 주세요: ", false);
        String InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                CommonPatternError.LECTURE_CODE);

        while (Integer.parseInt(InputLectureCode) > maxCode) {
            ScannerUtils.print("존재하지 않는 수업 코드입니다. 다시 입력 바랍니다.", true);
            InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                    CommonPatternError.LECTURE_CODE);
        }

        boolean isDeleted = false;

        for (Lecture lec : lectures) {
            //삭제할 강의가 존재한다면 lectures 에서 삭제하고 maxCode를 낮춤
            if (InputLectureCode.equals(lec.getLectureCode())) {
                timeCheck[lec.getDay() * Integer.parseInt(lec.getTime())] = false;
                lectures.remove(lec);
                isDeleted = true;
                maxCode--;
                maxLecture--;
                break;
            }
        }

        if (isDeleted) {
            //삭제가 성공했을시 과목들의 코드를 재할당함
            Integer initCode = 2000;
            for (Lecture lec : lectures) {
                lec.setLectureCode(initCode.toString(initCode++));
            }
        }
        return true;
    }

    public void addLecture() {
        if (maxLecture == 8) {
            ScannerUtils.print("수업이 꽉차 수업 추가가 불가능합니다.", true);
        } else {
            String[] dataList = new String[5];

            String rooms;
            int duplicateTime = 0;

            //과목 정보 입력
            ScannerUtils.print("추가할 과목을 입력해 주세요 ", true);
            for (int i = 0; i < sm.getSubjectss().size(); i++) {
                Subject sj = sm.getSubjectss().get(i);
                ScannerUtils.print((i + 1) + ")" + sj.getName() + "(" + sj.getCode() + ")     ", false);
            }

            String input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_TIME);

            dataList[0] = "100" + (Integer.parseInt(input) - 1);//과목코드 넣는법 변경

            //선생님 정보 입력
            ScannerUtils.print("선생님을 선택해주세요 ", true);
            int tnum = 1;
            int[] whichTeacher = new int[tm.getTeachers().size() + 1];
            for (int i = 0; i < tm.getTeachers().size(); i++) {
                Teacher tj = tm.getTeachers().get(i);
                if (tj.getSubjectCode().equals(dataList[0])) {
                    ScannerUtils.print((tnum) + ")" + tj.getName() + "(" + tj.getCode() + ")     ", false);
                    whichTeacher[++tnum] = i;
                }
            }

            input = ScannerUtils.scanWithPattern(CommonPattern.FOUR_CHOICE, CommonPatternError.FOUR_CHOICE);
            //TODO 여기서 예외출력어떻게할지
            dataList[1] = tm.getTeachers().get(whichTeacher[Integer.parseInt(input)]).getName();
            //선생님 선택
            ScannerUtils.print("강의실을 선택해주세요 ", true);
            for (int i = 0; i < lr.getRoom().size(); i++) {
                LectureRoom room = lr.getRoom().get(i);
                ScannerUtils.print((i + 1) + ")" + room.getCode() + "(제한인원:" + room.getLimit() + ")     ", false);
            }

            input = ScannerUtils.scanWithPattern(CommonPattern.THREE_CHOICE, CommonPatternError.THREE_CHOICE);
            rooms = input; // 저장할 강의실의 목록
            //강의실 목록은 띄워놨습니다

            //수업 코드 동적 할당
            dataList[2] = Integer.toString(++maxCode);

            //요일 정보 입력
            while (true) {
                ScannerUtils.print("수업 요일을 선택해주세요", true);
                ScannerUtils.print("1) 월 수 금   2) 화 목 토   ", false);
                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                duplicateTime = (Integer.parseInt(input) - 1) * 4;
                if (input.equals("1")) {
                    dataList[3] = "월 수 금";
                } else {
                    dataList[3] = "화 목 토";
                }

                //수업 시간 입력
                ScannerUtils.print("수업시간을 입력해주세요", true);
                ScannerUtils.print("1) 14:00~16:00   2) 16:00~18:00   3)18:00~20:00    4)20:00~22:00   ", false);
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
            //TODO timetablemanager에서 boolean으로 timetable여부 확인하고 추가함수있으니까 사용!
            //lectures 에 해당 새로운 강의 추가
            //TODO 여기에 timetable loop로 만든 ArrayList추가하면 될듯하네요!
            Lecture newLec = new Lecture(dataList[0], dataList[1], dataList[2], dataList[3], dataList[4]);
            lectures.add(newLec);
        }
    }

    public void editDate() {
        if (maxLecture == 8) {
            ScannerUtils.print("수업이 꽉차 수업 시간 변경이 불가능합니다.", true);
        } else {
            ScannerUtils.print("변경할 수업 코드를 선택하시오", true);
            LectureEditMenuHandler.input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                    CommonPatternError.LECTURE_CODE);

            while (Integer.parseInt(LectureEditMenuHandler.input) > LectureManager.maxCode) {
                ScannerUtils.print("존재하지 않습니다. 재입력 바랍니다.", true);
                LectureEditMenuHandler.input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                        CommonPatternError.LECTURE_CODE);
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
            System.out.println(LectureEditMenuHandler.input);
            for (Lecture lec : lectures) {
                if (lec.getLectureCode().equals(LectureEditMenuHandler.input)) {
                    timeCheck[Integer.parseInt(lec.getTime()) * lec.getDay()] = false;
                    lec.setTime(newTime);
                    lec.setDayOfWeek(newDate);
                    System.out.println(newTime + newDate);
                }
            }
        }

    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (Lecture lec : lectures) {
            String[] tmpData = {lec.getSubjectCode(), lec.getTeacher(), lec.getLectureCode(), lec.getDayOfWeek(),
                    lec.getTime()};
            saveData.add(tmpData);
        }
        read.writeCSV(saveData);
    }

    public int getMaxLecture() {
        return maxLecture;
    }

    //id같을경우 종료하는 함수
    public boolean checkSameID() {
        String subjectecode = "100";
        String lecturecode = "200";
        String teachercode = "300";
        int i = 0;
        for (Lecture lec : lectures) {
            if (!lec.getLectureCode().equals(lecturecode + (++i))) {
                ScannerUtils.print("특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
                return false;
            }
        }
        i = 0;
        for (Subject sub : sm.getSubjectss()) {
            if (!sub.getCode().equals(subjectecode + (++i))) {
                ScannerUtils.print("특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
                return false;
            }
        }
        i = 0;
        for (Teacher tec : tm.getTeachers()) {
            if (!tec.getCode().equals(teachercode + (++i))) {
                ScannerUtils.print("특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
                return false;
            }
        }
        return true;
    }
}
