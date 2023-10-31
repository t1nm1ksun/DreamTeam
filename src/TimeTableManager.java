import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;

public class TimeTableManager {
    public static Integer maxTimetable = 2000;
    private List<TimeTable> timetables = new ArrayList<>(); // 강의실목록을 저장할 리스트
    private Read read = new Read();
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일

    public TimeTableManager() {
        List<List<String>> list = read.readCSV("src/timetable.csv");

        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함

            TimeTable l1 = new TimeTable(item.get(0), item.get(1), item.get(2), item.get(3));
            timetables.add(l1);
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
                tt[Integer.parseInt(tab.getLectureDays()) - 1][Integer.parseInt(tab.getLectureTime()) - 1] = "O";
            }
        }

        ScannerUtils.print(roomId + "번 강의실의 시간표입니다.", true);
        ScannerUtils.print("    월    화    수    목    금    토", true);
        for (int i = 0; i < 4; i++) {
            ScannerUtils.print("|   ", false);
            for (int j = 0; j < 6; j++) {
                ScannerUtils.print(tt[i][j] + "    ", false);
            }
            ScannerUtils.print("", true);
        }
        ScannerUtils.print("O: 이미 수업이 존재하는 타임, X: 수업이 존재하지 않음", true);
    }

    public boolean findTable(String roomId, String day, String lectureTime) { // 타임테이블 포함여부 판단
        for (TimeTable tab : timetables) {
            if (roomId.equals(tab.getRoomId())) {
                if (day.equals(tab.getLectureDays())) {
                    if (lectureTime.equals(tab.showLectureTime())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addTimeTable(String code, String roomId, String day, String lectureTime) {// 타임테이블 추가
        TimeTable t1 = new TimeTable(code, roomId, day, lectureTime);
        timetables.add(t1);
    }

    public void deleteTimeTable(String code) { // timetable 삭제
        if (parseInt(code) > maxTimetable) {
            ScannerUtils.print("존재하지 않는 수업 코드입니다. 다시 입력 바랍니다.", true);
            return;
        } else {
            for (TimeTable tab : timetables) {
                //삭제할 강의가 존재한다면 lectures 에서 삭제하고 maxCode를 낮춤
                if (code.equals(tab.getRoomId())) {
                    timetables.remove(tab);
                    maxTimetable--;
                    break;
                    //TODO timetable의 개수 제한 해줘야함
                }
            }
        }
    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (TimeTable lec : timetables) {
            //TODO: 이거 timetable 인자 갯수 바뀌면서  바뀐 부분 체크해 주세요 (승범)
            String[] tmpData = {lec.getRoomId(), lec.getLectureDays(), lec.showLectureTime(), lec.getRoomId()};
            saveData.add(tmpData);
        }
        read.writeCSV(saveData);
    }

    public List<TimeTable> getTimetable() {
        return timetables;
    }
}
