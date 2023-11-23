import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DivisionManager implements BaseManager {

    private static Integer maxDivisionCode = 8000;
    private int maxDivision = 0; //수업 생성 막기
    private final List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일
    private final List<Division> divisions = new ArrayList<>(); // 분반 목록을 저장할 리스트

    @Override
    public String getCsvFilePath() {
        return "src/division.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.DIVISION_CODE, CommonPattern.LECTURE_CODE, CommonPattern.TEACHER_ID,
                 "+" + CommonPattern.TIMETABLE_CODE);
    }

    @Override
    public CsvExtraElementOption getExtraElementOption() {
        return new CsvExtraElementOption(true, getCsvFilePath() + "파일의 분반 데이터는 적어도 하나의 타임테이블ID를 갖고 있어야 합니다.");
    }

    @Override
    public boolean checkIsCsvRowsRequired() {
        return false;
    }


    public void makeDivisions() {
        List<List<String>> list = Read.readCSV("src/division.csv");

        for (List<String> item : list) {
            //csv 파일들을 읽어와서 분반들을 생성함
            if (Integer.parseInt(item.get(0)) >= maxDivisionCode) {
                maxDivisionCode = max(maxDivisionCode, Integer.parseInt(item.get(0)));
            }
            List<TimeTable> table = new ArrayList<>();
            for (int i = 4; i < item.size(); i++) {
                for (TimeTable t : Main.timetableManager.getTimetable()) {
                    if (t.getCode().equals(item.get(i))) {
                        table.add(t);
                        break;
                    }
                }
            }
            //item0 = 분반 코드, item1 = 수업 코드, item2 = 선생님 ID item3 = 정원 item4 = 타임테이블리스트
            Division d1 = new Division(item.get(0), item.get(1), item.get(2), item.get(3), table);
            divisions.add(d1);
            maxDivision++;
        }
    }

    public Division hasDivision(String divisionCode) {
        for (Division division : divisions) {
            if (division.getDivisionCode().equals(divisionCode)) {
                return division;
            }
        }
        return null;
    }

    // DivisionManager에서 학생이 수강 중인 분반을 조회하기 위한 메서드
    public void displayDivision(String divisionCode) {
        if (!divisions.isEmpty() && hasDivision(divisionCode) != null) {

            ScannerUtils.print("|    " + hasDivision(divisionCode).getDivisionCode() + "       ", false);
            ScannerUtils.print(hasDivision(divisionCode).getLectureCode()+ "         ", false);
            ScannerUtils.print(hasDivision(divisionCode).getTeacher() + "       ", false);
            ScannerUtils.print(hasDivision(divisionCode).getLimit() + "       ", false);
            ScannerUtils.print(hasDivision(divisionCode).getCount() + "       ", false);
            for (TimeTable t : hasDivision(divisionCode).getTimetable()) {
                ScannerUtils.print(
                        t.getRoomId() + " " + t.showDivisionDays() + " " + t.showDivisionTime()
                                + " / ", false);
            }
            ScannerUtils.print("", true);
        }
    }

    // 모든 분반 목록을 조회하는 메서드
    public boolean displayDivisions() {
        if (divisions.isEmpty()) {
            ScannerUtils.print("등록된 분반이 없습니다.", true);
            return false;
        } else {
            System.out.println("[등록된 분반 목록]");
            ScannerUtils.print("분반코드     수업코드    선생님 ID    분반 정원    분반 현원    강의실, 날짜 및 시간", true);

            //TODO: 여기 틀린 부분 있으면 고치기 (승범, 성종)
            for (Division division : divisions) {
                ScannerUtils.print(division.getDivisionCode() + "       ", false);
                ScannerUtils.print(division.getLectureCode() + "       ", false);
                ScannerUtils.print(division.getTeacher() + "       ", false);
                ScannerUtils.print(division.getLimit() + "       ", false);
                ScannerUtils.print(division.getCount() + "       ", false);
                for (TimeTable t : division.getTimetable()) {
                    ScannerUtils.print(
                            t.getRoomId() + " " + t.showDivisionDays() + " " + t.showDivisionTime()
                                    + " / ", false);
                }
                ScannerUtils.print("", true);
            }
        }
        return true;
    }


    public void displayTimetable(Division division) {
        for (TimeTable t : division.getTimetable()) {
            String divisionDay = "";
            switch (t.showDivisionDays()) {
                case "1": {
                    divisionDay = "월요일";
                    break;
                }
                case "2": {
                    divisionDay = "화요일";
                    break;
                }
                case "3": {
                    divisionDay = "수요일";
                    break;
                }
                case "4": {
                    divisionDay = "목요일";
                    break;
                }
                case "5": {
                    divisionDay = "금요일";
                    break;
                }
                case "6": {
                    divisionDay = "토요일";
                    break;
                }

            }

            ScannerUtils.print(
                    "[" + t.getCode() + "번 타임테이블: " + t.getRoomId() + "번 강의실 / " + divisionDay + " / "
                            + t.showDivisionTime()
                            + "] ", true);
        }
    }

    public boolean hasSelectedDivision(String divisionCode) {
        for (Division division : divisions) {
            if (division.getDivisionCode().equals(divisionCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteDivision() {
        if (!displayDivisions()) {
            return false;
        }

        ScannerUtils.print("삭제할 분반 코드를 입력해 주세요: ", false);
        String InputDivisionCode = ScannerUtils.scanWithPattern(CommonPattern.DIVISION_CODE,
                CommonPatternError.DIVISION_CODE);

        while (!hasSelectedDivision(InputDivisionCode)) {
            ScannerUtils.print("존재하지 않는 분반입니다. 다시 입력 바랍니다.", true);
            InputDivisionCode = ScannerUtils.scanWithPattern(CommonPattern.DIVISION_CODE,
                    CommonPatternError.DIVISION_CODE);
        }

        for (Division div : divisions) {
            //삭제할 분반이 존재한다면 divisions 에서 삭제함
            if (InputDivisionCode.equals(div.getDivisionCode())) {
                if(maxDivisionCode == Integer.parseInt(div.getDivisionCode())) {
                    maxDivisionCode--;
                }
                // 학생이 듣는 수업 중에 삭제할 수업이 있다면 삭제
                Main.studentManager.checkDeletedDivision(div.getDivisionCode());
                // 해당 강의의 timetable 삭제
                for (TimeTable deleteTimeTable : div.getTimetable()) {
                    Main.timetableManager.deleteTimeTable(deleteTimeTable.getCode());
                }

                //TODO 부탁해 창균씨 Teacher 클래스에 잇는 deleteTimetable 함수 써줘
                // lec 인자로 선생님에 접근하고 Teacher nowTeacher =
                // for (TimeTable deleteTimeTable : lec.getTimetable()) {
                //                    nowTeacher.deleteTimeTable(deleteTimeTable.getCode());
                //                }
                // 해줘잉

                divisions.remove(div);
                break;
            }
        }

        return true;
    }

    public void addDivision() {
        ScannerUtils.print("[2. 분반 추가를 선택하셨습니다.]", true);

        if (Main.timetableManager.checkTimeTableMax()) {
            ScannerUtils.print("강의실이 꽉 차 분반을 추가로 등록하실 수 없습니다.", true);
        } else {
            String[] dataList = new String[3];
            List<TimeTable> timetable = new ArrayList<>();

            int divisionLimit=2048;

            String room;
            String day;
            String time;

            // 수업 선택
            Main.lectureManager.displayLectures();
            String lecturecode="";
            System.out.println(lecturecode.length());
            do{
                ScannerUtils.print("\n추가할 분반의 수업을 선택해 주세요: ", false);
                String input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);
                lecturecode= Main.lectureManager.hasLecture(input).getLectureCode();
            }while(lecturecode.length()<3);


            // 수업 코드 저장
            dataList[0] = lecturecode;

            ScannerUtils.print(
                    "[" + Main.lectureManager.getLectureByCode(lecturecode).getLectureName()
                            + "수업을(를) 선택하셨습니다.]",
                    true);



            String input;

            // 선생님 선택
            List<Integer> whichTeacher = new ArrayList<>();
            int count = 0;

            for (int i = 0; i < Main.teacherManager.getTeachers().size(); i++) {
                Teacher teacher = Main.teacherManager.getTeachers().get(i);

                if (teacher.getSubjectCode().equals(Main.lectureManager.getLectureByCode(dataList[0]).getSubjectCode())) {
                    count++;
                    ScannerUtils.print(count + ") " + teacher.getName() + "    ", false);
                    whichTeacher.add(i);
                }
            }
            ScannerUtils.print("\n추가할 수업의 선생님을 선택해 주세요: ", false);

            String ChoiceNumber = "^[1-" + count + "]$";
            input = ScannerUtils.scanWithPattern(ChoiceNumber, CommonPatternError.TEACHER_ID);

            ScannerUtils.print(
                    "[" + Main.teacherManager.getTeachers().get(whichTeacher.get(Integer.parseInt(input) - 1)).getName()
                            + "을 선택하셨습니다.]", true);

            // 선생님 ID 저장
            dataList[1] = Main.teacherManager.getTeachers().get(whichTeacher.get(Integer.parseInt(input) - 1))
                    .getCode();
            Teacher teacherNow = Main.teacherManager.getTeachers().get(whichTeacher.get(Integer.parseInt(input) - 1));




            boolean finishFlag;

            do {
                // 강의실 선택
                ScannerUtils.print("   강의실    수용가능인원", true);
                for (int i = 0; i < Main.lectureroomManager.getRoom().size(); i++) {
                    LectureRoom rooms = Main.lectureroomManager.getRoom().get(i);
                    ScannerUtils.print((i + 1) + ") " + rooms.getCode() + "    " + rooms.getLimit() + "명", true);
                }

                ScannerUtils.print("수업할 강의실을 선택해 주세요: ", false);

                // TODO: 정규식 변경에 따른 예외 처리는 여기를 보시면 됩니다 ~

                ChoiceNumber = "^[1-" + Main.lectureroomManager.getRoom().size() + "]$";
                input = ScannerUtils.scanWithPattern(ChoiceNumber, CommonPatternError.LECTURE_ROOM_CHOICE);

                // 강의실 코드 저장
                room = Main.lectureroomManager.getRoom().get(Integer.parseInt(input) - 1).getCode();

                // 강의실 코드로 가장 작은 수업 정원을 강의의 정원으로 선택
                divisionLimit = min(divisionLimit, Integer.parseInt(Main.lectureroomManager.getRoomLimit(room)));



                Main.timetableManager.displayTimeTable(room);

                // 수업 요일 선택
                ScannerUtils.print("1) 월요일    2) 화요일    3) 수요일    4) 목요일    5) 금요일    6) 토요일", true);
                ScannerUtils.print("분반의 수업 요일을 선택해 주세요: ", false);

                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                day = input;

                // 수업 시간 선택
                ScannerUtils.print("1) 14:00~16:00    2) 16:00~18:00    3) 18:00~20:00    4) 20:00~22:00", true);
                ScannerUtils.print("분반의 수업 시간을 입력해 주세요: ", false);

                input = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);
                time = input;

                // 해당 선생님이 이미 해당 요일&시간에 수업이 있을 때 예외 처리
                if (!teacherNow.checkTimeTableAlreadyExists(day, time)) {
                    // 추가하려는 분반이 이미 해당 요일&시간에 존재할 때 예외 처리 (강의실만 다르고 요일&시간이 같은 경우 방지)
                    boolean checkDuplicate = false;

                    if (!timetable.isEmpty()) {
                        for (TimeTable timeTable : timetable) {
                            checkDuplicate =
                                    day.equals(timeTable.getDivisionDays()) && time.equals(timeTable.getDivisionTime());
                        }
                    }

                    if (!checkDuplicate && Main.timetableManager.findTable(room, day, time)) {
                        String code = Main.timetableManager.addTimeTable(room, day, time);
                        TimeTable newTimetable = new TimeTable(code, room, day, time);
                        timetable.add(newTimetable); // "timetable" 객체에 "newTimetable" 객체를 전달
                        teacherNow.addTimetable(newTimetable); // "teacherNow" 객체에 "newTimetable" 객체를 전달
                        ScannerUtils.print("해당 시간에 성공적으로 추가되었습니다.", true);
                    } else {
                        ScannerUtils.print("해당 타임에는 이미 수업이 존재합니다.", true);
                    }
                } else {
                    ScannerUtils.print("선택한 선생님이 이미 해당 타임에 수업이 존재합니다.", true);
                }

                ScannerUtils.print("해당 분반의 추가를 계속 하려면 1, 이대로 분반 추가를 종료하려면 2를 입력해 주세요.", true);
                input = ScannerUtils.scanWithPattern(CommonPattern.TWO_CHOICE, CommonPatternError.TWO_CHOICE);

                finishFlag = input.equals("1");
            } while (finishFlag);
            dataList[2]=Integer.toString(divisionLimit);

            if (!timetable.isEmpty()) {
                //////////// 여기 까지 //////////////////

                int cmpCode = 7000;
                boolean isNew = false;
                divisions.sort(Comparator.comparing(Division::getDivisionCode));
                for (Division division : divisions) {
                    if (!Integer.toString(cmpCode).equals(division.getDivisionCode())) {
                        Division newDivision = new Division(Integer.toString(cmpCode), dataList[0],
                                dataList[1],dataList[2],timetable);
                        divisions.add(newDivision);
                        isNew = true;
                        break;
                    } else {
                        cmpCode++;
                    }
                    maxDivisionCode = max(maxDivisionCode, Integer.parseInt(division.getLectureCode()));
                }

                if (!isNew) {
                    maxDivisionCode = max(maxDivisionCode, cmpCode);
                    Division newDivision = new Division(Integer.toString(cmpCode), dataList[0], dataList[1],dataList[2],timetable);
                    divisions.add(newDivision);
                }
            }
        }
    }

    public void editDate() {
        if (Main.timetableManager.getTimetable().isEmpty()) {
            ScannerUtils.print("등록되어 있는 분반이 없습니다.", true);
        } else {
            ScannerUtils.print("변경할 분반 코드를 입력하세요: ", false);
            DivisionEditMenuHandler.input = ScannerUtils.scanWithPattern(CommonPattern.DIVISION_CODE,
                    CommonPatternError.DIVISION_CODE);
            while (!hasSelectedDivision(DivisionEditMenuHandler.input)) {
                ScannerUtils.print("존재하지 않습니다. 재입력 바랍니다.", true);
                DivisionEditMenuHandler.input = ScannerUtils.scanWithPattern(CommonPattern.DIVISION_CODE,
                        CommonPatternError.DIVISION_CODE);
            }

            //타임 테이블 출력
            displayTimetable(hasDivision(DivisionEditMenuHandler.input));

            ScannerUtils.print("변경할 타임의 타임테이블 코드를 입력하세요 (ex: 6000): ", false);

            String code_toEdit = "";

            boolean has = false;
            while(!has){
                code_toEdit = ScannerUtils.scanWithPattern(CommonPattern.TIMETABLE_CODE,
                        CommonPatternError.TIMETABLE_CODE);
                for(TimeTable timetable : hasDivision(DivisionEditMenuHandler.input).getTimetable()){
                    if(timetable.getCode().equals(code_toEdit)){
                        has=true;
                        break;
                    }
                }
                if(!has){
                ScannerUtils.print("존재하지 않습니다. 재입력 바랍니다.", true);}

            }

            //받아
            TimeTable tb_toEdit = null;

            for (TimeTable tab : hasDivision(DivisionEditMenuHandler.input).getTimetable()) {
                if (tab.getCode().equals(code_toEdit)) {
                    tb_toEdit = tab;
                    break;
                }
            }

            String room = tb_toEdit.getRoomId();
            String day = tb_toEdit.getDivisionDays();
            String time = tb_toEdit.getDivisionTime();

            ScannerUtils.print("1) 분반 요일 변경    2) 분반 시간 변경", true);
            ScannerUtils.print("변경할 정보를 선택하세요: ", false);
            int data_toEdit = Integer.parseInt(
                    ScannerUtils.scanWithPattern(CommonPattern.TWO_CHOICE, CommonPatternError.TWO_CHOICE));
            switch (data_toEdit) {
                case 1: {
                    ScannerUtils.print("분반 요일 변경을 선택하셨습니다.", true);
                    // TODO : 타임테이블에서 수업중인 요일을 확인하고 그 요일들 제외한 요일들 중 하나 고르게함
                    /*String[] weeklist = {"월요일","화요일","수요일","목요일","금요일","토요일"};*/
                    ArrayList<String> week = new ArrayList<>(
                            Arrays.asList("1) 월요일 ", "2) 화요일 ", "3) 수요일 ", "4) 목요일 ", "5) 금요일 ", "6) 토요일 "));
                    week.remove(Integer.parseInt(day) - 1);
                    for (String date : week) {
                        ScannerUtils.print(date + " ", false);
                    }
                    ScannerUtils.print("", true);

                    ScannerUtils.print("분반 요일을 선택해 주세요: ", false);
                    day = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE);
                    break;
                }
                case 2: {
                    ScannerUtils.print("분반 시간 변경을 선택하셨습니다.", true);
                    // TODO : 타임테이블에서 해당 요일중 수업중인 교시 빼고 하나 고르게함
                    ArrayList<String> timeOptions = new ArrayList<>(
                            Arrays.asList("1) 14:00~16:00", "2) 16:00~18:00", "3) 18:00~20:00", "4) 20:00~22:00"));
                    timeOptions.remove(Integer.parseInt(time) - 1);

                    // 변경 가능한 시간 목록 출력
                    for (String timeOption : timeOptions) {
                        ScannerUtils.print(timeOption + " ", false);
                    }
                    ScannerUtils.print("", true);
                    ScannerUtils.print("분반 시간을 입력해 주세요: ", false);
                    time = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME);

                    break;
                }
            }

            //TODO : 타임테이블 빈자리 있으면 그대로 저장 빈자리 없으면 도루묵
            if (Main.timetableManager.findTable(room, day, time)) {
                //TODO : timetable.day = day  timetable.time = time  이렇게
                tb_toEdit.setDivisionDays(day);
                tb_toEdit.setDivisionTime(time);
                ScannerUtils.print("분반 시간이 변경되었습니다.", true);
            } else {
                ScannerUtils.print("해당 시간에는 이미 분반이 존재합니다", true);
            }
        }
    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (Division div : divisions) {
            List<String> tmpData = new ArrayList<>(
                    Arrays.asList(div.getDivisionCode(), div.getLectureCode(),div.getTeacher(),div.getLimit()));
            for (TimeTable timeTable : div.getTimetable()) {
                tmpData.add(timeTable.getCode());
            }

            int size = tmpData.size();
            String[] data = tmpData.toArray(new String[size]);

            saveData.add(data);
        }

        Read.writeDivisionCSV(saveData);
    }



    /*//id같을경우 종료하는 함수
    public boolean checkSameID() {
        String subjectecode = "100";
        String lecturecode = "200";
        String teachercode = "300";
        Set<String> checkName = new HashSet<>();

        for (Division lec : lectures) {
            checkName.add(lec.getLectureCode());
        }
        if (checkName.size() != lectures.size()) {
            ScannerUtils.print("lecture.csv의 특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
            return false;
        }
        checkName.clear();

        for (Subject sub : Main.subjectManager.getSubjectss()) {
            checkName.add(sub.getCode());
        }
        if (checkName.size() != Main.subjectManager.getSubjectss().size()) {
            ScannerUtils.print("subject.csv의 특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
            return false;
        }
        checkName.clear();

        for (Teacher tec : Main.teacherManager.getTeachers()) {
            checkName.add(tec.getCode());
        }
        if (checkName.size() != Main.teacherManager.getTeachers().size()) {
            ScannerUtils.print("teacher.csv의 특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
            return false;
        }
        checkName.clear();
        for (LectureRoom room : Main.lectureroomManager.getRoom()) {
            checkName.add(room.getCode());
        }
        if (checkName.size() != Main.lectureroomManager.getRoom().size()) {
            ScannerUtils.print("lecture-room.csv의 특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
            return false;
        }
        checkName.clear();
        for (TimeTable table : Main.timetableManager.getTimetable()) {
            checkName.add(table.getCode());
        }
        if (checkName.size() != Main.timetableManager.getTimetable().size()) {
            ScannerUtils.print("lecture-room.csv의 특정 ID가 중복 조회되고 있습니다. csv 파일을 확인해주세요.", true);
            return false;
        }
        return true;
    }*/

    public boolean showAddableDivisions(List<Division> cmpDivisions) {
        // 학생이 수강하는 수업과 timeTable이 겹치지 않는 수업만 보여줌
        ScannerUtils.print("[추가 가능한 수업 목록]", true);
        boolean isDivisionShown = false;

        // 학생 수업이랑 겹치는 수업들을 체크하는 map
        HashMap<Division, Boolean> isRejectedDivision = new HashMap<>();
        for (Division division : divisions) {
            isRejectedDivision.put(division, false);
        }
        // 전체 강의들 중 학생이 수강중인 강의들의 timeTable과 겹치지 않는 강의들만 출력
        // 즉, 새로 추가할 수 있는 강의들만 출력
        for (Division cmpDivision : cmpDivisions) {
            for (Division division : divisions) {
                if (cmpDivision.getDivisionCode().equals(division.getDivisionCode())) {
                    isRejectedDivision.put(division, true);
                    continue;
                }

                if (this.isOverLappedDivision(cmpDivision, division)) {
                    isRejectedDivision.put(division, true);
                }
            }
        }

        // check가 되지 않은 수업들만 출력(수강할 수 있는 수업들)
        for (Division division : isRejectedDivision.keySet()) {
            if (!isRejectedDivision.get(division)) {
                isDivisionShown = true;
                division.showDivision();
            }
        }
        return isDivisionShown;
    }


    public boolean isOverLappedDivision(Division division1, Division dicision2) {
        // 2개의 강의를 비교하여 시간이 겹치는지 확인
        for (TimeTable table1 : division1.getTimetable()) {
            for (TimeTable table2 : dicision2.getTimetable()) {
                if (Main.timetableManager.isOverLappedTime(table1, table2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Division> getStudentsDivisionList(Student stu) {
        List<Division> ret = new ArrayList<>();
        for (String stuLec : stu.getDivisionCodes()) {
            for (Division division : divisions) {
                if (stuLec.equals(division.getLectureCode())) {
                    ret.add(division);
                }
            }
        }
        return ret;
    }

    //해당 선생님 id를 가진 선생님이 들어가는 분반들 list return
    public List<Division> getTeachersDivisionList(String teachercode) {
        List<Division> ret = new ArrayList<>();
        for (Division division : divisions){
            if(division.getTeacher().equals(teachercode)){
                ret.add(division);
            }
        }
        return ret;
    }


    public Division getDivisionByCode(String divisionCode) {

        for (Division division : divisions) {
            if (division.getDivisionCode().equals(divisionCode)) {
                return division;
            }
        }
        return null;
    }
    public void deleteDivision(String divisionCode) {
        // 입력 받은 디비젼 코드와 같은 디비젼들을 divisions에서 삭제
        for(Division division : divisions) {
            if(division.getDivisionCode().equals(divisionCode)) {
                divisions.remove(division);

                for (TimeTable table : division.getTimetable()){
                    // 디비젼이 가지고 있던 타임 테이블들을 모두 삭제함
                    Main.timetableManager.deleteTimeTable(table.getCode());
                }
            }
        }
    }
    public Division deleteDivisionByLectureCode(String lectrueCode) {
        // 디비젼 코드를 이용해 deleteDivision을 호출
        for (Division division : divisions) {
            if (division.getLectureCode().equals(lectrueCode)) {
                deleteDivision(division.getDivisionCode());
            }
        }
        return null;
    }

}
