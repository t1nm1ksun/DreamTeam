import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class LectureRoomManager {
    private List<LectureRoom> rooms = new ArrayList<>(); // 강의실목록을 저장할 리스트

    private List<Boolean> studentList = new ArrayList<>(Collections.nCopies(1000, false));
    private Read read = new Read();
    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일

    public LectureRoomManager() {
        List<List<String>> list = read.readCSV("src/lecture-room.csv");
        List<List<String>> stuList = read.readCSV("src/student.csv");
        Integer num = stuList.size();
        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함

            LectureRoom l1 = new LectureRoom(item.get(0), item.get(1), item.get(2));
            String[] l2 = {item.get(0), item.get(1), item.get(2)};
//            roomArr.add(l2);
            rooms.add(l1);
        }

    }

    // roomId를 통해 해당 강의실의 수강 제한인원을 파악함
    public boolean checkLeft(String roomId, String studentCode) {
        for (LectureRoom room : rooms) {
            if (room.getCode().equals(roomId)) {
                if(room.isFull()) {
                    return true;
                } else {

                    if(!studentList.get(Integer.parseInt(studentCode) - 4000)) {
                        ScannerUtils.print("plus : " + room.getCode() + " stu : " + studentCode, true);
                        room.plusCount();
                        studentList.set(Integer.parseInt(studentCode) - 4000, true);
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (LectureRoom data : rooms) {
            String[] tmpData = {data.getCode(), data.getLimit(), data.getCount()};
            //TODO: 수업리스트 저장 추가 해야댐
            saveData.add(tmpData);
        }
//        ScannerUtils.print(Integer.toString(saveData.size()), true);
        read.writeLectureRoomCSV(saveData);
    }


    public List<LectureRoom> getRoom() {
        return rooms;
    }

    public int getRoomNumber() {
        return this.rooms.size();
    }
}
