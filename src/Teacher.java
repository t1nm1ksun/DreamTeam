import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private String code;
    private String name;
    private final List<String> subjectCode;
    //과목코드 추가!
    public Teacher(String code, String name, List<String> subjectCode) {
        this.code = code;
        this.name = name;
        this.subjectCode = subjectCode;
    }

    // code 필드의 getter 메서드
    public String getCode() {
        return code;
    }


    // name 필드의 getter 메서드
    public String getName() {
        return name;
    }


    public List<String> getSubjectCode() {
        return subjectCode;
    }

    public List<TimeTable> getTimeTables(){
        List<Division> teachersDivisions = Main.divisionManager.getTeachersDivisionList(code);
        List<TimeTable> timeTables = new ArrayList<>();

        for(Division teacherDivision: teachersDivisions){
            timeTables.addAll(teacherDivision.getTimetable());
        }
        return timeTables;
    }

    public boolean checkTimeTableAlreadyExists(String day, String time) {
        List<TimeTable> timeTables = getTimeTables();

        if (!timeTables.isEmpty()) {
            for (TimeTable timeTable : timeTables) {
                if (timeTable.getDivisionDays().equals(day) && timeTable.getDivisionTime().equals(time)) {
                    return true;
                }
            }
        }

        return false;
    }

}
