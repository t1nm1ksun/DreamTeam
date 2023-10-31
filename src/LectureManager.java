import java.util.ArrayList;
import java.util.List;

public class LectureManager {

    private static Integer maxCode = 2000;
    private int maxLecture = 0; //수업 생성 막기
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일
    private List<Lecture> lectures = new ArrayList<>(); // 수업 목록을 저장할 리스트
    private Read read = new Read();
    SubjectManager sm = new SubjectManager();
    TeacherManager tm = new TeacherManager();
    LectureRoomManager lrm = new LectureRoomManager();
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
            for (int i = 3; i < item.size(); i++) {
                for (TimeTable t : ttm.getTimetable()) {
                    if (t.getRoomId().equals(item.get(i))) {
                        table.add(t);
                        break;
                    }
                }
            }//노가다 table 생성 및 초기화..
            //TODO: Lecture 생성자 형태에 맞춰서 바꾸기 (승범, 성종)- 성공
            Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2),  table);
            lectures.add(l1);
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
            //TODO: Lecture 형식에 맞춰서 수정하기, timeTableManager로 수업 정보 가져오기 (창균, 민석)
            ScannerUtils.print("|    " + hasLecture(lectureCode).getLectureCode() + "       ", false);
            ScannerUtils.print(hasLecture(lectureCode).getSubjectCode() + "         ", false);
            ScannerUtils.print(hasLecture(lectureCode).getTeacher() + "       ", false);
            for (TimeTable t : hasLecture(lectureCode).getTimetable()) {
                ScannerUtils.print(
                        t.getRoomId() + " " + t.getLectureDays() + " " + t.showLectureTime()
                                + " / ", false);
            }
            ScannerUtils.print("", true);
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

            //TODO: 여기 틀린 부분 있으면 고치기 (승범, 성종)
            for (Lecture lecture : lectures) {
                ScannerUtils.print(lecture.getLectureCode() + "       ", false);
                ScannerUtils.print(lecture.getSubjectCode() + "       ", false);
                ScannerUtils.print(lecture.getTeacher() + "       ", false);
                for (TimeTable t : lecture.getTimetable()) {
                    ScannerUtils.print(
                            t.getRoomId() + " " + t.getLectureDays() + " " + t.showLectureTime()
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
            //삭제할 강의가 존재한다면 lectures 에서 삭제함
            if (InputLectureCode.equals(lec.getLectureCode())) {
                lectures.remove(lec);
                isDeleted = true;
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
        if (maxLecture == ttm.getTimetable().size()) {
            ScannerUtils.print("수업이 꽉차 수업 추가가 불가능합니다.", true);
        } else {
            String[] dataList = new String[3];
            List<TimeTable> timetable = new ArrayList<>();

            String room;
            String day;
            String time;

            //과목 정보 입력
            ScannerUtils.print("추가할 과목을 입력해 주세요 ", true);
            for (int i = 0; i < sm.getSubjectss().size(); i++) {
                Subject sj = sm.getSubjectss().get(i);
                ScannerUtils.print((i + 1) + ")" + sj.getName() + "(" + sj.getCode() + ")     ", false);
            }

            String input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_TIME);

            dataList[0] = "100" + (Integer.parseInt(input) - 1);//과목코드 넣는법 변경

            //선생님 정보 입력
            ScannerUtils.print("선생님을 선택해 주세요", true);
            int tnum = 1;
            int[] whichTeacher = new int[tm.getTeachers().size() + 1];
            for (int i = 0; i < tm.getTeachers().size(); i++) {
                Teacher tj = tm.getTeachers().get(i);
                if (tj.getSubjectCode().equals(dataList[0])) {
                    ScannerUtils.print((tnum) + ")" + tj.getName() + "(" + tj.getCode() + ")     ", false);
                    whichTeacher[++tnum] = i;
                }
            }

            // 선생님 선택
            // TODO: 여기서 예외 처리 어떻게 할지 (선생님 숫자가 달라지면 정규식도 바껴야 함)
            input = ScannerUtils.scanWithPattern(CommonPattern.FOUR_CHOICE, CommonPatternError.FOUR_CHOICE);

            dataList[1] = tm.getTeachers().get(whichTeacher[Integer.parseInt(input)]).getName();

            // 수업 코드 동적 할당
            dataList[2] = Integer.toString(++maxCode);

            // 강의실 선택
            ScannerUtils.print("   강의실 번호    수용 가능 인원", true);
            for (int i = 0; i < lrm.getRoom().size(); i++) {
                LectureRoom rooms = lrm.getRoom().get(i);
                ScannerUtils.print((i + 1) + ") " + rooms.getCode() + "         " + rooms.getLimit(), true);
            }
            ScannerUtils.print("수업할 강의실을 선택해 주세요", true);

            // TODO: 여기서 예외 처리 어떻게 할지 (강의실 갯수가 달라지면 정규식도 바껴야 함)
            input = ScannerUtils.scanWithPattern(CommonPattern.THREE_CHOICE, CommonPatternError.THREE_CHOICE);
            room = input;

            boolean check;

            // 요일 정보 입력
            do {
                ttm.displayTimeTable(room);

                ScannerUtils.print("1) 월요일   2) 화요일   3) 수요일   4) 목요일   5) 금요일   6) 토요일", true);
                ScannerUtils.print("수업 요일을 선택해 주세요", true);

                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                day = input;

                //수업 시간 입력
                ScannerUtils.print("1) 14:00~16:00   2) 16:00~18:00   3)18:00~20:00   4)20:00~22:00", true);
                ScannerUtils.print("수업 시간을 입력해 주세요", true);

                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);
                time = input;

                if (ttm.findTable(room, day, time)) {
                    ttm.addTimeTable(code, room, day, time);
                    timetable.add(new TimeTable(code, room, day, time));
                    ScannerUtils.print("수업 시간이 추가되었습니다.", true);
                } else {
                    ScannerUtils.print("해당 시간에는 이미 수업이 존재합니다", true);
                }

                ScannerUtils.print("추가로 다른 요일에 해당 수업을 추가하려면 1, 이대로 수업 추가를 종료하려면 2를 입력해 주세요.", true);
                input = ScannerUtils.scanWithPattern(CommonPattern.TWO_CHOICE, CommonPatternError.TWO_CHOICE);

                check = input.equals("1");
            } while (check);

            Lecture newLec = new Lecture(dataList[0], dataList[1], dataList[2], timetable);

            lectures.add(newLec);
        }
    }

    public void editDate() {
        ScannerUtils.print("변경할 수업 코드를 선택하시오", true);
        LectureEditMenuHandler.input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                CommonPatternError.LECTURE_CODE);
        while (Integer.parseInt(LectureEditMenuHandler.input) > LectureManager.maxCode) {
            ScannerUtils.print("존재하지 않습니다. 재입력 바랍니다.", true);
            LectureEditMenuHandler.input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                    CommonPatternError.LECTURE_CODE);
        }

        //타임 테이블 출력
        ScannerUtils.print("변경할 요일의 수업을 선택하세요", true);
        TimeTable tb_toEdit = ScannerUtils.scanWithPattern(); // TODO : 바꿀 타임테이블 입력받기 (민석)
        //받아
        String room = "";
        String day = "";
        String time = "";

        ScannerUtils.print("1) 수업 요일 변경   2) 수업 시간 변경", true);
        ScannerUtils.print("변경할 정보를 선택하세요 : ", true);
        int data_toEdit = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.TWO_CHOICE,
                CommonPatternError.TWO_CHOICE);
        switch (data_toEdit) {
            case 1: {
                ScannerUtils.print("수업 요일 변경을 선택하셨습니다.", true);
                // TODO : 타임테이블에서 수업중인 요일을 확인하고 그 요일들 제외한 요일들 중 하나 고르게함
                ScannerUtils.print("1) 월요일   2) 화요일   3) 수요일   4) 목요일   5) 금요일   6) 토요일", true);
                ScannerUtils.print("수업 요일을 선택해 주세요", true);
                day = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                break;
            }
            case 2: {
                ScannerUtils.print("수업 시간 변경을 선택하셨습니다.", true);
                // TODO : 타임테이블에서 해당 요일중 수업중인 교시 빼고 하나 고르게함
                ScannerUtils.print("1) 14:00~16:00   2) 16:00~18:00   3)18:00~20:00   4)20:00~22:00", true);
                ScannerUtils.print("수업 시간을 입력해 주세요", true);
                time = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);

                break;
            }
        }

        //TODO : 타임테이블 빈자리 있으면 그대로 저장 빈자리 없으면 도루묵
        if (ttm.findTable(room, day, time)) {
            //TODO : timetable.day = day  timetable.time = time  이렇게
            ScannerUtils.print("수업 시간이 변경되었습니다.", true);
        } else {
            ScannerUtils.print("해당 시간에는 이미 수업이 존재합니다", true);
        }


    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (Lecture lec : lectures) {
            //TODO: Lecture 생성자 형태에 맞춰서 바꾸기 (승범, 성종)
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

    public void showAddableLectures(List<Lecture> cmpLectures) {
        // 학생이 수강하는 수업과 timeTable이 겹치지 않는 수업만 보여줌
        ScannerUtils.print("[추가 가능한 수업 목록]", true);

        // 전체 강의들 중 학생이 수강중인 강의들의 timeTable과 겹치지 않는 강의들만 출력
        // 즉, 새로 추가할 수 있는 강의들만 출력
        for (Lecture lecture : lectures) {
            boolean isAddable = true;
            for (TimeTable tt : lecture.getTimetable()) {
                for (Lecture cmpLecture : cmpLectures) {
                    for (TimeTable cmptt : cmpLecture.getTimetable()) {
                        if ((tt.getRoomId() == cmptt.getRoomId())
                                || (tt.getLectureDays() == cmptt.getLectureDays())
                                || (tt.getLectureTime() == cmptt.getLectureTime())) {
                            isAddable = false;
                            break;
                        }
                        if (!isAddable) {
                            break;
                        }
                    }
                    if (!isAddable) {
                        break;
                    }
                }
            }

            if (isAddable) {
                ScannerUtils.print("수업 코드 : " + lecture.getLectureCode() + " ", false);
                ScannerUtils.print("과목 코드 : " + lecture.getSubjectCode() + " ", false);
                ScannerUtils.print("선생님 : " + lecture.getTeacher() + " ", false);
                ScannerUtils.print("", true);
            }
        }
    }

    public Lecture getLectureByCode(String lectureCode) {

        for (Lecture lecture : lectures) {
            if (lecture.getLectureCode().equals(lectureCode)) {
                return lecture;
            }
        }
        return null;
    }
}
