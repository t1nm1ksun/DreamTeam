import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TimeTableManager implements BaseManager {
    public static Integer maxTimetable = 6000;  // 현재까지 만들어진 timetable의 코드 중 최대값
    private final List<TimeTable> timetables = new ArrayList<>(); // 강의실목록을 저장할 리스트
    private final List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일

    @Override
    public String getCsvFilePath() {
        return "src/timetable.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.TIMETABLE_CODE, CommonPattern.ROOM_ID, CommonPattern.LECTURE_DATE,
                CommonPattern.LECTURE_TIME);
    }

    @Override
    public CsvExtraElementOption getExtraElementOption() {
        return new CsvExtraElementOption(false, "");
    }

    @Override
    public boolean checkIsCsvRowsRequired() {
        return false;
    }
    
    public void makeTimetables() {
        List<List<String>> list = Read.readCSV(getCsvFilePath());

        for (List<String> item : list) {
            // csv 파일들을 읽어와서 강의들을 생성함

            TimeTable l1 = new TimeTable(item.get(0), item.get(1), item.get(2), item.get(3));
            // 타임테이블 코드, 강의실 아이, 수업요일, 수업교시
            timetables.add(l1);
        }
    }

    private String displayLectureTime(int lectureTime) {
        if (lectureTime == 0) {
            return "14:00~16:00";
        } else if (lectureTime == 1) {
            return "16:00~18:00";
        } else if (lectureTime == 2) {
            return "18:00~20:00";
        } else {
            return "20:00~22:00";
        }
    }

    public void displayTimeTable(String roomId) {
        String[][] tt = {
                {"X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X"}
        };

        for (TimeTable tab : timetables) {
            if (roomId.equals(tab.getRoomId())) {
                tt[Integer.parseInt(tab.getDivisionTime()) - 1][Integer.parseInt(tab.getDivisionDays()) - 1] = "O";
            }
        }

        ScannerUtils.print(roomId + "번 강의실 ㅣ 월 ㅣ 화 ㅣ 수 ㅣ 목 ㅣ 금 ㅣ 토", true);
        for (int i = 0; i < 4; i++) {
            ScannerUtils.print(displayLectureTime(i) + "   ", false);
            for (int j = 0; j < 6; j++) {
                ScannerUtils.print(tt[i][j] + "    ", false);
            }
            ScannerUtils.print("", true);
        }
        ScannerUtils.print("O: 이미 수업이 존재하는 타임, X: 수업이 존재하지 않음", true);
    }

    public boolean findTable(String roomId, String day, String lectureTime) { // timetable 포함 여부 판단
        for (TimeTable tab : timetables) {
            if (roomId.equals(tab.getRoomId())) {
                if (day.equals(tab.getDivisionDays())) {
                    if (lectureTime.equals(tab.getDivisionTime())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isOverLappedTime(TimeTable table1, TimeTable table2) {
        // 2개의 타임테이블이 겹치는지 확인
        return table1.getDivisionDays().equals(table2.getDivisionDays())
                && table1.getDivisionTime().equals(table2.getDivisionTime());
    }

    public String addTimeTable(String roomId, String day, String lectureTime) {// 타임테이블 추가
        int cmpCode = 6000;
        boolean isNew = false;
        timetables.sort(Comparator.comparing(TimeTable::getCode));
        for (TimeTable table : timetables) {
            if (!Integer.toString(cmpCode).equals(table.getCode())) {
                TimeTable t1 = new TimeTable(Integer.toString(cmpCode), roomId, day, lectureTime);
                timetables.add(t1);
                isNew = true;
                break;
            } else {
                cmpCode++;
            }
        }

        if (!isNew) {
            TimeTable t1 = new TimeTable(Integer.toString(cmpCode), roomId, day, lectureTime);
            timetables.add(t1);
        }

        return Integer.toString(cmpCode);
    }

    public void deleteTimeTable(String code) { // timetable 삭제
        if (hasTimeTable(code) == null) {
            ScannerUtils.print("존재하지 않는 타임테이블 코드입니다. 다시 입력 바랍니다.", true);
        } else {
            for (TimeTable tab : timetables) {
                //삭제할 강의가 존재한다면 lectures 에서 삭제하고 maxCode를 낮춤
                if (code.equals(tab.getCode())) {
                    timetables.remove(tab);
                    maxTimetable--;
                    break;
                    //TODO timetable의 개수 제한 해줘야함
                }
            }
        }
    }

    public TimeTable hasTimeTable(String tableCode) {
        for (TimeTable timetable : timetables) {
            if (timetable.getCode().equals(tableCode)) {
                return timetable;
            }
        }
        return null;
    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (TimeTable lec : timetables) {
            String[] tmpData = {lec.getCode(), lec.getRoomId(), lec.getDivisionDays(), lec.getDivisionTime()};
            saveData.add(tmpData);
        }
        Read.writeTimeTableCSV(saveData);
    }

    public boolean checkTimeTableMax() {
        int timeTableLimit = Main.lectureroomManager.getRoomNumber() * 6 * 4;
        return timeTableLimit == Main.lectureroomManager.getRoomNumber();
    }

    public Integer getTimeTableLimit() {
        return Main.lectureroomManager.getRoomNumber() * 6 * 4;
    }

    public List<TimeTable> getTimetable() {
        return timetables;
    }

}
