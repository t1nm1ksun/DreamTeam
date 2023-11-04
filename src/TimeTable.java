public class TimeTable {

    private final String code;
    private final String roomId;
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

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureDays(String day) {
        this.lectureDays = day;
    }

    public void setLectureTime(String time) {
        this.lectureTime = time;
    }

    public void showTimeTable() {
        ScannerUtils.print("    " + this.roomId + " 강의실 ", false);
        ScannerUtils.print(showLectureDays() + " ", false);
        ScannerUtils.print(showLectureTime() + " ", false);
        ScannerUtils.print("/ ", false);
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
    
    public String showLectureTime() {
        switch (lectureTime) {
            case "1" -> {
                return "14:00~16:00";
            }
            case "2" -> {
                return "16:00~18:00";
            }
            case "3" -> {
                return "18:00~20:00";
            }
            case "4" -> {
                return "20:00~22:00";
            }
        }
        return null;
    }
}
