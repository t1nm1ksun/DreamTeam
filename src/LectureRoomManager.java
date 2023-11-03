import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LectureRoomManager implements BaseManager{
    private final List<LectureRoom> rooms = new ArrayList<>(); // 강의실목록을 저장할 리스트

    private final HashMap<String, Integer> lectureRoomLimit = new HashMap<>();
    private final HashMap<String, Integer> lectureRoomCount = new HashMap<>();

    private final Read read = new Read();
    private final List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일

    @Override
    public String getCsvFilePath() {
        return "src/lecture-room.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.ROOM_ID,CommonPattern.ROOM_LIMIT);
    }
    public LectureRoomManager() {
        List<List<String>> list = read.readCSV("src/lecture-room.csv");
        List<List<String>> stuList = read.readCSV("src/student.csv");
        Integer num = stuList.size();
        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함

            LectureRoom l1 = new LectureRoom(item.get(0), item.get(1));
            String[] l2 = {item.get(0), item.get(1)};
//            lectureRoomLimit.put(item.get(0), Integer.parseInt(item.get(1)));
//            lectureRoomCount.put(item.get(0), Integer.parseInt(item.get(2)));
//            roomArr.add(l2);
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

    public Integer getMinRoomLimit(Lecture addingLecture) {
        int ret = Integer.MAX_VALUE;
        for (TimeTable table : addingLecture.getTimetable()) {
            ret = min(ret, lectureRoomLimit.get(table.getRoomId()));
        }
        return ret;
    }

    public Integer getNowCount(Lecture addingLecture) {
        Integer ret = 0;
        for (TimeTable table : addingLecture.getTimetable()) {
            ret = lectureRoomCount.get(table.getRoomId());
        }
        return ret;
    }

    public void plusCount(Lecture addingLecture) {
        for (TimeTable table : addingLecture.getTimetable()) {
            for (LectureRoom room : rooms) {
                if (table.getRoomId().equals(room.getCode())) {
                    //room.plusCount();
                }
            }
        }
    }

    public List<LectureRoom> getRoom() {
        return rooms;
    }

    public int getRoomNumber() {
        return this.rooms.size();
    }
}
