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

    public String getCode() {
        return code;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getLectureDays() {
        return lectureDays;
    }

    public void setLectureDays(String day){
        this.lectureDays=day;

    }
    public void setLectureTime(String time){
        this.lectureTime=time;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public String showLectureTime() {
        if (lectureTime.equals("1")) {
            return "14:00~16:00";
        } else if (lectureTime.equals("2")) {
            return "16:00~18:00";
        } else if (lectureTime.equals("3")) {
            return "18:00~20:00";
        } else {
            return "20:00~22:00";
        }
    }

    public String showLectureDays() {
        switch (lectureDays) {
            case "1" -> {
                return "월요일";
            }
            case "2" -> {
                return "화요일";
            }
            case "3" -> {
                return "수요일";
            }
            case "4" -> {
                return "목요일";
            }
            case "5" -> {
                return "금요일";
            }
            case "6" -> {
                return "토요일";
            }
        }
        return null;
    }
}
