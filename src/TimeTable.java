public class TimeTable {

    private final String code;
    private final String roomId;
    private String divisionDays;
    private String divisionTime;

    public TimeTable(String code, String roomId, String divisionDays, String divisionTime) {
        this.code = code;
        this.roomId = roomId;
        this.divisionDays = divisionDays;
        this.divisionTime = divisionTime;
    }

    public String getCode() {
        return code;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getDivisionDays() {
        return divisionDays;
    }

    public String getDivisionTime() {
        return divisionTime;
    }
    
    public void setDivisionDays(String day) {
        this.divisionDays = day;
    }

    public void setDivisionTime(String time) {
        this.divisionTime = time;
    }

    public void showTimeTable() {
        ScannerUtils.print("    " + this.roomId + " 강의실 ", false);
        ScannerUtils.print(showDivisionDays() + " ", false);
        ScannerUtils.print(showDivisionTime() + " ", false);
        ScannerUtils.print("/ ", false);
    }


    public String showDivisionDays() {
        switch (divisionDays) {
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

    public String showDivisionTime() {
        switch (divisionTime) {
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
