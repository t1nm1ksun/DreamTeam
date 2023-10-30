import java.util.ArrayList;
import java.util.List;

public class TimeTableManager {
    private List<TimeTable> timetables = new ArrayList<>(); // 강의실목록을 저장할 리스트
    private Read read = new Read();
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일

    public static Integer maxTimetable = 5000;

    public TimeTableManager() {
        List<List<String>> list = read.readCSV("src/timetable.csv");

        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함

            TimeTable l1 = new TimeTable(item.get(0), item.get(1), item.get(2), item.get(3));
            timetables.add(l1);
            maxTimetable++;
        }
    }

    public boolean findTable(String day, String lectureTime, String roomId) {//타임테이블 포함여부 판단
        for (TimeTable tab : timetables) {
            if (day.equals(tab.getLecture_days())) {
                if (lectureTime.equals(tab.getLectuer_time())) {
                    if (roomId.equals(tab.getRoomId())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String addTimeTable(String day, String lectureTime, String roomId) {//타임테이블추가
        TimeTable t1 = new TimeTable(Integer.toString(++maxTimetable), day, lectureTime, roomId);
        timetables.add(t1);
        return t1.getCode();
    }

    public void deleteTimeTable(String code) { //timetable삭제
        if (Integer.parseInt(code) > maxTimetable) {
            ScannerUtils.print("존재하지 않는 수업 코드입니다. 다시 입력 바랍니다.", true);
            return;
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

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (TimeTable lec : timetables) {
            String[] tmpData = {lec.getCode(), lec.getLecture_days(), lec.getLectuer_time(), lec.getRoomId()};
            saveData.add(tmpData);
        }
        read.writeCSV(saveData);
    }

    public List<TimeTable> geTimetable() {
        return timetables;
    }
}
