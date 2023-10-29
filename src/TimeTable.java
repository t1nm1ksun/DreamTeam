
public class TimeTable {
    private String code;
    private String lecture_days;
    private String lecture_time;
    private String roomId;

    public TimeTable(String code, String lectuer_days, String lectuer_time, String roomId) {
        this.code = code;
        this.lecture_time = lectuer_time;
        this.lecture_days = lectuer_days;
        this.roomId = roomId;
    }

    public String getCode() {
        return code;
    }
    public String getLecture_days(){
        return lecture_days;
    }
    public String getLectuer_time(){
        return lecture_time;
    }
    public String getRoomId(){
        return roomId;
    }
}
