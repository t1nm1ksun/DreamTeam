import java.util.List;

public class Teacher {

    private String code;
    private String name;
    private final String subjectCode;
    private final List<TimeTable> timeTables;

    //과목코드 추가!
    public Teacher(String code, String name, String subjectCode, List<TimeTable> timeTables) {
        this.code = code;
        this.name = name;
        this.subjectCode = subjectCode;
        this.timeTables = timeTables;
    }

    // code 필드의 getter 메서드
    public String getCode() {
        return code;
    }

    // code 필드의 setter 메서드
    public void setCode(String code) {
        this.code = code;
    }

    // name 필드의 getter 메서드
    public String getName() {
        return name;
    }

    // name 필드의 setter 메서드
    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public boolean findTimeTable(String day, String time) {
        if (!timeTables.isEmpty()) {
            for (TimeTable timeTable : timeTables) {
                if (timeTable.getLectureDays().equals(day) && timeTable.getLectureTime().equals(time)) {
                    return true;
                }
            }
        }
        return false;
    }
}
