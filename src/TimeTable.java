public class TimeTable {

    private String code;
    private String roomId;
    private String lectureDays;
    private String lectureTime;

    public TimeTable(String code, String roomId, String lectureDays, String lectureTime) {
        this.code = code;
        this.roomId = roomId;
        this.lectureDays = lectureDays;
        this.lectureTime = lectureTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getLectureDays() {
        return lectureDays;
    }

    public String showLectureTime() {
        if (lectureTime.equals("1")) {
            return "12:00~14:00";
        } else if (lectureTime.equals("2")) {
            return "14:00~16:00";
        } else if (lectureTime.equals("3")) {
            return "16:00~18:00";
        } else {
            return "18:00~20:00";
        }
    }

    public String getLectureTime() {
        return lectureTime;
    }
}
