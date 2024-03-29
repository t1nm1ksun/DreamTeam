import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LectureRoomManager implements BaseManager {
    private final List<LectureRoom> rooms = new ArrayList<>(); // 강의실목록을 저장할 리스트

    @Override
    public String getCsvFilePath() {
        return "src/lecture-room.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.ROOM_ID, CommonPattern.ROOM_LIMIT);
    }

    @Override
    public CsvExtraElementOption getExtraElementOption() {
        return new CsvExtraElementOption(false, "");
    }

    @Override
    public boolean checkIsCsvRowsRequired() {
        return true;
    }

    public void makeRooms() {
        List<List<String>> list = Read.readCSV(getCsvFilePath());
        List<List<String>> stuList = Read.readCSV(Main.studentManager.getCsvFilePath());

        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함
            LectureRoom l1 = new LectureRoom(item.get(0), item.get(1));

            rooms.add(l1);
        }
    }

    public String getRoomLimit(String roomCode) {
        for (LectureRoom room : rooms) {
            if (roomCode.equals(room.getCode())) {
                return room.getLimit();
            }
        }
        return "";
    }

    public List<LectureRoom> getRoom() {
        return rooms;
    }

    public int getRoomNumber() {
        return this.rooms.size();
    }
}
