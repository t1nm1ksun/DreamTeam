import java.util.ArrayList;
import java.util.List;

public class Lecture {
    private String subjectCode;     // 과목 코드
    private String teacher;     // 선생님
    private String lectureCode; // 수업 코드
    private List<TimeTable> timetable = new ArrayList<>();

    public Lecture(String subjectCode, String teacher, String LectureCode, List<TimeTable> timetable) {
        this.subjectCode = subjectCode;
        this.teacher = teacher;
        this.lectureCode = LectureCode;
        this.timetable = timetable;
    }

    // 과목 정보 설정
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    // 선생님 정보 설정
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    // 수업 코드 정보 설정
    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }

    public void addTimetable(TimeTable timeTable) {
        this.timetable.add(timeTable);
    }

    // 과목 정보 반환
    public String getSubjectCode() {
        return subjectCode;
    }

    // 선생님 정보 반환
    public String getTeacher() {
        return teacher;
    }

    public String getLectureCode() {
        return lectureCode;
    }

    public List<TimeTable> getTimetable() {
        return timetable;
    }
}
