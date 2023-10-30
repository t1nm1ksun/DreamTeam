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

    public String getLecture_days() {
        return lecture_days;
    }

    public String getLectuer_time() {
        if (lecture_time.equals("1")) {
            return "12:00~14:00";
        } else if (lecture_time.equals("2")) {
            return "14:00~16:00";
        } else if (lecture_time.equals("23")) {
            return "16:00~18:00";
        } else {
            return "18:00~20:00";
        }

    }

    public String getRoomId() {
        return roomId;
    }
}
