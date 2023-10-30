public class TimeTable {

    private String roomId;
    private String lecture_days;
    private String lecture_time;

    public TimeTable(String roomId, String lectuer_days, String lectuer_time) {
        this.roomId = roomId;
        this.lecture_days = lectuer_days;
        this.lecture_time = lectuer_time;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getLecture_days() {
        return lecture_days;
    }

    public String showLecture_time() {
        if (lecture_time.equals("1")) {
            return "12:00~14:00";
        } else if (lecture_time.equals("2")) {
            return "14:00~16:00";
        } else if (lecture_time.equals("3")) {
            return "16:00~18:00";
        } else {
            return "18:00~20:00";
        }
    }

    public String getLecture_time() {
        return lecture_time;
    }
}
