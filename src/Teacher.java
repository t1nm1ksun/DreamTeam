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


    // name 필드의 getter 메서드
    public String getName() {
        return name;
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

    public List<TimeTable> getTimeTables(){
        return timeTables;
    }

    // TODO: 조만간 처리
    public void addTimetable(TimeTable timetable){
        this.timeTables.add(timetable);
    }
    public void deleteTimetable(TimeTable timetable){
        this.timeTables.remove(timetable);
    }
}
