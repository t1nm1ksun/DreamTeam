import java.util.ArrayList;
import java.util.List;

public class TimeTableManager {
    private List<TimeTable> timetables = new ArrayList<>(); // 강의실목록을 저장할 리스트
    private Read read = new Read();
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일

    public TimeTableManager(){
        List<List<String>> list = read.readCSV("src/lecture-room.csv");

        for(List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함

            TimeTable l1 = new TimeTable(item.get(0), item.get(1),item.get(2), item.get(3));
            timetables.add(l1);
        }
    }
    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for(TimeTable lec : timetables) {
            String[] tmpData = {lec.getCode(), lec.getLecture_days(), lec.getLectuer_time(), lec.getRoomId()};
            saveData.add(tmpData);
        }
        read.writeCSV(saveData);
    }
}
