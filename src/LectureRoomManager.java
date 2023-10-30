import java.util.ArrayList;
import java.util.List;

public class LectureRoomManager {
    private List<LectureRoom> rooms = new ArrayList<>(); // 강의실목록을 저장할 리스트
    private Read read = new Read();
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일

    public LectureRoomManager() {
        List<List<String>> list = read.readCSV("src/lecture-room.csv");

        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함

            LectureRoom l1 = new LectureRoom(item.get(0), item.get(1));
            rooms.add(l1);
        }
    }

    // roomId를 통해 해당 강의실의 수강 제한인원을 파악함
    public Integer checkRoomLeft(String roomId) {
        for(LectureRoom room : rooms) {
            if(room.getCode().equals(roomId)) {
                return room.getLeft();
            }
        }
        return -1;
    }
    public List<LectureRoom> getRoom() {
        return rooms;
    }
}
