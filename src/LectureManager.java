import static java.lang.Math.min;

import java.util.*;

public class LectureManager implements BaseManager {

    private static Integer maxCode = 2000;
    private int maxLecture = 0; //수업 생성 막기
    private final List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일
    private final List<Lecture> lectures = new ArrayList<>(); // 수업 목록을 저장할 리스트
    private final SubjectManager subjectManager = new SubjectManager();
    private final TeacherManager teacherManager = new TeacherManager();
    private final LectureRoomManager lectureRoomManager = new LectureRoomManager();
    private final TimeTableManager timeTableManager = new TimeTableManager();

    @Override
    public String getCsvFilePath() {
        return "src/lecture.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.LECTURE_CODE,CommonPattern.SUBJECT_CODE,CommonPattern.TEACHER_ID,CommonPattern.ROOM_LIMIT,CommonPattern.ROOM_CURRENT_STUDENT,"+"+CommonPattern.TIMETABLE_CODE);
    }
    /**
     * csv로부터 읽어온파일들을 순서대로 lectures에 저장 마지막에 한번에 저장하기 위해 saveData에 순차적 저장
     */
    public LectureManager() {
        List<List<String>> list = Read.readCSV("src/lecture.csv");
       // List<String> regexList = Arrays.asList(CommonPattern.LECTURE_CODE, CommonPattern.SUBJECT_CODE,CommonPattern.TEACHER_ID,CommonPattern.)


        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함
            if (Integer.parseInt(item.get(2)) > maxCode) {
                maxCode = Integer.parseInt(item.get(2));
            }
            List<TimeTable> table = new ArrayList<>();
            for (int i = 5; i < item.size(); i++) {
                for (TimeTable t : timeTableManager.getTimetable()) {
                    if (t.getCode().equals(item.get(i))) {
                        table.add(t);
                        break;
                    }
                }
            }//노가다 table 생성 및 초기화..
            //TODO: Lecture 생성자 형태에 맞춰서 바꾸기 (승범, 성종)- 성공
            Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2), item.get(3), item.get(4), table);
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
                        t.getRoomId() + " " + t.showLectureDays() + " " + t.showLectureTime()
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
            System.out.println("[등록된 수업 목록]");
            ScannerUtils.print("수업코드     과목코드     선생님 ID    강의실, 날짜 및 시간", true);

            //TODO: 여기 틀린 부분 있으면 고치기 (승범, 성종)
            for (Lecture lecture : lectures) {
                ScannerUtils.print(lecture.getLectureCode() + "       ", false);
                ScannerUtils.print(lecture.getSubjectCode() + "       ", false);
                ScannerUtils.print(lecture.getTeacher() + "       ", false);
                for (TimeTable t : lecture.getTimetable()) {
                    ScannerUtils.print(
                            t.getRoomId() + " " + t.showLectureDays() + " " + t.showLectureTime()
                                    + " / ", false);
                }
                ScannerUtils.print("", true);
            }
        }
        return true;
    }


    public void displayTimetable(Lecture lecture) {
        for (TimeTable t : lecture.getTimetable()) {
            String lectureDay = "";
            switch (t.getLectureDays()) {
                case "1": {
                    lectureDay = "월요일";
                    break;
                }
                case "2": {
                    lectureDay = "화요일";
                    break;
                }
                case "3": {
                    lectureDay = "수요일";
                    break;
                }
                case "4": {
                    lectureDay = "목요일";
                    break;
                }
                case "5": {
                    lectureDay = "금요일";
                    break;
                }
                case "6": {
                    lectureDay = "토요일";
                    break;
                }

            }

            ScannerUtils.print(
                    "[" + t.getCode() + "번 타임테이블: " + t.getRoomId() + "강의실 " + lectureDay + " " + t.showLectureTime()
                            + "] ", false);
        }
        ScannerUtils.print("", true);
    }

    public boolean deleteLecture() {
        if (!displayLectures()) {
            return false;
        }

        ScannerUtils.print("삭제할 수업 코드를 입력해 주세요: ", false);
        String InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                CommonPatternError.LECTURE_CODE);

        while (hasLecture(InputLectureCode) == null) {
            ScannerUtils.print("존재하지 않는 수업입니다. 다시 입력 바랍니다.", true);
            InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                    CommonPatternError.LECTURE_CODE);
        }

        for (Lecture lec : lectures) {
            //삭제할 강의가 존재한다면 lectures 에서 삭제함
            if (InputLectureCode.equals(lec.getLectureCode())) {
                // 해당 강의의 timetable 삭제
                for (TimeTable deleteTimeTable : lec.getTimetable()) {
                    timeTableManager.deleteTimeTable(deleteTimeTable.getCode());
                }

                lectures.remove(lec);
                break;
            }
        }

        return true;
    }

    public void addLecture() {
        ScannerUtils.print("[2. 수업 추가를 선택하셨습니다.]", true);

        if (timeTableManager.checkTimeTableMax()) {
            ScannerUtils.print("강의실이 꽉 차 수업을 추가로 등록하실 수 없습니다.", true);
        } else {
            String[] dataList = new String[4];
            List<TimeTable> timetable = new ArrayList<>();

            String room;
            String day;
            String time;

            // 과목 선택
            for (int i = 0; i < subjectManager.getSubjectss().size(); i++) {
                Subject subject = subjectManager.getSubjectss().get(i);
                ScannerUtils.print((i + 1) + ") " + subject.getName() + "    ", false);
            }
            ScannerUtils.print("\n추가할 수업의 과목을 선택해 주세요: ", false);

            // TODO: 여기서 예외 처리 어떻게 할지 (과목 갯수가 달라지면 정규식도 바껴야 함)
            String input = ScannerUtils.scanWithPattern(CommonPattern.TWO_CHOICE, CommonPatternError.TWO_CHOICE);

            // 과목 코드 저장
            dataList[0] = subjectManager.getSubjectss().get(Integer.parseInt(input) - 1).getCode();

            ScannerUtils.print(
                    "[" + subjectManager.getSubjectss().get(Integer.parseInt(input) - 1).getName() + "을(를) 선택하셨습니다.]",
                    true);

            // 선생님 선택
            List<Integer> whichTeacher = new ArrayList<>();
            int count = 0;

            for (int i = 0; i < teacherManager.getTeachers().size(); i++) {
                Teacher teacher = teacherManager.getTeachers().get(i);

                if (teacher.getSubjectCode().equals(dataList[0])) {
                    count++;
                    ScannerUtils.print(count + ") " + teacher.getName() + "    ", false);
                    whichTeacher.add(i);
                }
            }
            ScannerUtils.print("\n추가할 수업의 선생님을 선택해 주세요: ", false);

            String ChoiceNumber = "^[1-" + count + "]$";
            input = ScannerUtils.scanWithPattern(ChoiceNumber, CommonPatternError.TEACHER_ID);
            ScannerUtils.print(
                    "[" + teacherManager.getTeachers().get(whichTeacher.get(Integer.parseInt(input) - 1)).getName()
                            + "을 선택하셨습니다.]", true);

            // 선생님 ID 저장
            dataList[1] = teacherManager.getTeachers().get(whichTeacher.get(Integer.parseInt(input) - 1)).getCode();
            Teacher teacherNow = teacherManager.getTeachers().get(whichTeacher.get(Integer.parseInt(input) - 1));

            // 수업 코드 저장 (근데 수업 삭제하면 수업 코드는 어디부터임?)
//            dataList[2] = Integer.toString(++maxCode);

            // 수업의 최대 수강 인원을 저장
            int lectureLimit = Integer.MAX_VALUE;

            boolean finishFlag;

            do {
                // 강의실 선택
                ScannerUtils.print("   강의실    수용가능인원", true);
                for (int i = 0; i < lectureRoomManager.getRoom().size(); i++) {
                    LectureRoom rooms = lectureRoomManager.getRoom().get(i);
                    ScannerUtils.print((i + 1) + ") " + rooms.getCode() + "    " + rooms.getLimit() + "명", true);
                }

                ScannerUtils.print("수업할 강의실을 선택해 주세요: ", false);

                // TODO: 정규식 변경에 따른 예외 처리는 여기를 보시면 됩니다 ~

                ChoiceNumber = "^[1-" + lectureRoomManager.getRoom().size() + "]$";
                input = ScannerUtils.scanWithPattern(ChoiceNumber, CommonPatternError.LECTURE_ROOM_CHOICE);

                // 강의실 코드 저장
                room = lectureRoomManager.getRoom().get(Integer.parseInt(input) - 1).getCode();

                // 강의실 코드로 가장 작은 수업 정원을 강의의 정원으로 선택
                lectureLimit = min(lectureLimit, Integer.parseInt(lectureRoomManager.getRoomLimit(room)));

                // 수업의 현재 수강 인원, 새로 개설되는 수업이므로 0으로 설정
                String lectureNow = "0";
                dataList[3] = lectureNow;

                timeTableManager.displayTimeTable(room);

                // 수업 요일 선택
                ScannerUtils.print("1) 월요일    2) 화요일    3) 수요일    4) 목요일    5) 금요일    6) 토요일", true);
                ScannerUtils.print("수업 요일을 선택해 주세요: ", false);

                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                day = input;

                // 수업 시간 선택
                ScannerUtils.print("1) 14:00~16:00    2) 16:00~18:00    3) 18:00~20:00    4) 20:00~22:00", true);
                ScannerUtils.print("수업 시간을 입력해 주세요: ", false);

                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);
                time = input;

                // 해당 선생님이 이미 해당 요일&시간에 수업이 있을 때 예외 처리
                if (!teacherNow.findTimeTable(day, time)) {
                    // 추가하려는 수업이 이미 해당 요일&시간에 존재할 때 예외 처리 (강의실만 다르고 요일&시간이 같은 경우 방지)
                    boolean checkDuplicate = false;

                    if (!timetable.isEmpty()) {
                        for (TimeTable timeTable : timetable) {
                            checkDuplicate =
                                    day.equals(timeTable.getLectureDays()) && time.equals(timeTable.getLectureTime());
                        }
                    }

                    if (!checkDuplicate && timeTableManager.findTable(room, day, time)) {
                        String code = timeTableManager.addTimeTable(room, day, time);
                        timetable.add(new TimeTable(code, room, day, time));
                        ScannerUtils.print("해당 타임에 정상적으로 추가되었습니다.", true);
                    } else {
                        ScannerUtils.print("해당 타임에는 이미 수업이 존재합니다.", true);
                    }
                } else {
                    ScannerUtils.print("선택한 선생님이 이미 해당 타임에 수업이 존재합니다.", true);
                }

                ScannerUtils.print("해당 수업 추가를 계속 하려면 1, 이대로 수업 추가를 종료하려면 2를 입력해 주세요.", true);
                input = ScannerUtils.scanWithPattern(CommonPattern.TWO_CHOICE, CommonPatternError.TWO_CHOICE);

                finishFlag = input.equals("1");
            } while (finishFlag);

            if (!timetable.isEmpty()) {
                dataList[2] = String.valueOf(lectureLimit); // 최소 정원으로 수업 정원 설정

                int cmpCode = 2000;
                boolean isNew = false;
                lectures.sort(Comparator.comparing(Lecture::getLectureCode));
                for (Lecture lecture : lectures) {
                    if (!Integer.toString(cmpCode).equals(lecture.getLectureCode())) {
                        Lecture newLecture = new Lecture(Integer.toString(cmpCode),dataList[0], dataList[1],
                                dataList[2], dataList[3],
                                timetable);
                        lectures.add(newLecture);
                        isNew = true;
                        break;
                    } else {
                        cmpCode++;
                    }
                }

                if (!isNew) {
                    Lecture newLecture = new Lecture(Integer.toString(cmpCode), dataList[0], dataList[1],  dataList[2],
                            dataList[3],
                            timetable);
                    lectures.add(newLecture);
                }
            }
        }
    }

    public void editDate() {
        if (timeTableManager.getTimetable().isEmpty()) {
            ScannerUtils.print("등록되어 있는 수업이 없습니다.", true);
        } else {
            ScannerUtils.print("변경할 수업 코드를 선택하시오", true);
            LectureEditMenuHandler.input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                    CommonPatternError.LECTURE_CODE);
            while (Integer.parseInt(LectureEditMenuHandler.input) > LectureManager.maxCode) {
                ScannerUtils.print("존재하지 않습니다. 재입력 바랍니다.", true);
                LectureEditMenuHandler.input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                        CommonPatternError.LECTURE_CODE);
            }

            //타임 테이블 출력
            ScannerUtils.print("변경할 요일의 타임테이블 코드를 입력하세요 (예시: 6000): ", true);

            displayTimetable(hasLecture(LectureEditMenuHandler.input));
            String code_toEdit = ScannerUtils.scanWithPattern(CommonPattern.TIMETABLE_CODE,
                    CommonPatternError.TIMETABLE_CODE); // TODO : 바꿀 타임테이블 입력받기 (민석)
            //받아'
            TimeTable tb_toEdit = null;

            for (TimeTable tab : hasLecture(LectureEditMenuHandler.input).getTimetable()) {
                if (tab.getCode().equals(code_toEdit)) {
                    tb_toEdit = tab;
                    break;
                }
            }

            String room = tb_toEdit.getRoomId();
            String day = tb_toEdit.getLectureDays();
            String time = tb_toEdit.getLectureTime();

            ScannerUtils.print("1) 수업 요일 변경   2) 수업 시간 변경", true);
            ScannerUtils.print("변경할 정보를 선택하세요 : ", true);
            int data_toEdit = ScannerUtils.scanWithPatternIntegerForMenu(CommonPattern.TWO_CHOICE,
                    CommonPatternError.TWO_CHOICE);
            switch (data_toEdit) {
                case 1: {
                    ScannerUtils.print("수업 요일 변경을 선택하셨습니다.", true);
                    // TODO : 타임테이블에서 수업중인 요일을 확인하고 그 요일들 제외한 요일들 중 하나 고르게함
                    /*String[] weeklist = {"월요일","화요일","수요일","목요일","금요일","토요일"};*/
                    ArrayList<String> week = new ArrayList<>(
                            Arrays.asList("1) 월요일 ", "2) 화요일 ", "3) 수요일 ", "4) 목요일 ", "5) 금요일 ", "6) 토요일 "));
                    week.remove(Integer.parseInt(day) - 1);
                    for (String date : week) {
                        ScannerUtils.print(date + " ", false);
                    }
                    ScannerUtils.print("", true);

                    ScannerUtils.print("수업 요일을 선택해 주세요", true);
                    day = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                    break;
                }
                case 2: {
                    ScannerUtils.print("수업 시간 변경을 선택하셨습니다.", true);
                    // TODO : 타임테이블에서 해당 요일중 수업중인 교시 빼고 하나 고르게함
                    ArrayList<String> timeOptions = new ArrayList<>(
                            Arrays.asList("1) 14:00~16:00", "2) 16:00~18:00", "3) 18:00~20:00", "4) 20:00~22:00"));
                    timeOptions.remove(Integer.parseInt(time) - 1);

                    // 변경 가능한 시간 목록 출력
                    for (String timeOption : timeOptions) {
                        ScannerUtils.print(timeOption + " ", false);
                    }
                    ScannerUtils.print("", true);
                    ScannerUtils.print("수업 시간을 입력해 주세요", true);
                    time = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);

                    break;
                }
            }

            //TODO : 타임테이블 빈자리 있으면 그대로 저장 빈자리 없으면 도루묵
            if (timeTableManager.findTable(room, day, time)) {
                //TODO : timetable.day = day  timetable.time = time  이렇게
                tb_toEdit.setLectureDays(day);
                tb_toEdit.setLectureTime(time);
                ScannerUtils.print("수업 시간이 변경되었습니다.", true);
            } else {
                ScannerUtils.print("해당 시간에는 이미 수업이 존재합니다", true);
            }
        }


    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (Lecture lec : lectures) {
            List<String> tmpData = new ArrayList<>(
                    Arrays.asList(lec.getLectureCode(), lec.getSubjectCode(), lec.getTeacher(), lec.getLimit(),
                            lec.getCount()));
            for (TimeTable timeTable : lec.getTimetable()) {
                tmpData.add(timeTable.getCode());
            }

            int size = tmpData.size();
            String[] data = tmpData.toArray(new String[size]);

            saveData.add(data);
        }
        timeTableManager.saveDataFile();
        Read.writeLectureCSV(saveData);
    }

    public int getMaxLecture() {
        return maxLecture;
    }

    //id같을경우 종료하는 함수
    public boolean checkSameID() {
        String subjectecode = "100";
        String lecturecode = "200";
        String teachercode = "300";
        Set<String> checkName = new HashSet<>();

        for (Lecture lec : lectures) {
            checkName.add(lec.getLectureCode());
        }
        if (checkName.size() != lectures.size()) {
            System.out.println(1);
            ScannerUtils.print("특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
            return false;
        }
        checkName.clear();

        for (Subject sub : subjectManager.getSubjectss()) {
            checkName.add(sub.getCode());
        }
        if (checkName.size() != subjectManager.getSubjectss().size()) {
            System.out.println(2);
            ScannerUtils.print("특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
            return false;
        }
        checkName.clear();

        for (Teacher tec : teacherManager.getTeachers()) {
            checkName.add(tec.getCode());
        }
        if (checkName.size() != teacherManager.getTeachers().size()) {
            System.out.println(3);
            ScannerUtils.print("특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
            return false;
        }
        return true;
    }

    public boolean showAddableLectures(List<Lecture> cmpLectures) {
        // 학생이 수강하는 수업과 timeTable이 겹치지 않는 수업만 보여줌
        ScannerUtils.print("[추가 가능한 수업 목록]", true);
        boolean isLectureShown = false;

        // 학생 수업이랑 겹치는 수업들을 체크하는 map
        HashMap<Lecture, Boolean> isRejectedLecture = new HashMap<>();
        for(Lecture lec : lectures) {
            isRejectedLecture.put(lec, false);
        }
        // 전체 강의들 중 학생이 수강중인 강의들의 timeTable과 겹치지 않는 강의들만 출력
        // 즉, 새로 추가할 수 있는 강의들만 출력
        for(Lecture cmpLec : cmpLectures) {
            for(Lecture lecture : lectures) {
                if(cmpLec.getLectureCode().equals(lecture.getLectureCode())) {
                    isRejectedLecture.put(lecture, true);
                    continue;
                }

                if(this.isOverLappedLecture(cmpLec, lecture)) {
                    isRejectedLecture.put(lecture, true);
                }
            }
        }

        // check가 되지 않은 수업들만 출력(수강할 수 있는 수업들)
        for(Lecture lec : isRejectedLecture.keySet()) {
            if(!isRejectedLecture.get(lec)) {
//                ScannerUtils.print(lec, true);
                isLectureShown = true;
                lec.showLecture();
            }
        }
        return isLectureShown;
    }

    public void showLec(String code) {

    }

    public boolean isOverLappedLecture(Lecture lec1, Lecture lec2) {
        // 2개의 강의를 비교하여 시간이 겹치는지 확인
        for(TimeTable table1 : lec1.getTimetable()) {
            for(TimeTable table2 : lec2.getTimetable()) {
                if(timeTableManager.isOverLappedTime(table1, table2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Lecture> getStudentsLectureList(Student stu) {
        List<Lecture> ret = new ArrayList<>();
        for(String stuLec : stu.getLectureList()) {
            for(Lecture lecture : lectures) {
                if(stuLec.equals(lecture.getLectureCode())) {
                    ret.add(lecture);
                }
            }
        }
        return ret;
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
