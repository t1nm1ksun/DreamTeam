import java.util.ArrayList;
import java.util.List;
public class Lecture {
    private String subjectCode;     // 과목 코드
    private String teacher;     // 선생님
    private String lectureRoom;//강의실
    private String dayOfWeek;  // 요일
    private String time;        // 수업 시간
    private String LectureCode; // 수업 코드
    private List<TimeTable> timetable = new ArrayList<>();

    public Lecture(String subjectCode, String teacher, String LectureCode, String dayOfWeek, String time,List<TimeTable> timetable) {
        this.subjectCode = subjectCode;
        this.teacher = teacher;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.LectureCode = LectureCode;
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

    // 요일 정보 설정
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    // 수업 시간 정보 설정
    public void setTime(String time) {
        this.time = time;
    }

    // 수업 코드 정보 설정
    public void setLectureCode(String lectureCode) {
        this.LectureCode = lectureCode;
    }

    // 과목 정보 반환
    public String getSubjectCode() {
        return subjectCode;
    }

    // 선생님 정보 반환
    public String getTeacher() {
        return teacher;
    }

    // 요일 정보 반환
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDay() {
        if(dayOfWeek.equals("월 수 금")) {
            return 1;
        } else {
            return 2;
        }
    }

    // 수업 시간 정보 반환
    public String getTime() {
        return time;
    }

    public String getLectureCode() {
        return LectureCode;
    }
    public String getlectureRoom() {
        return lectureRoom;
    }
    public List<TimeTable> getTimetable() {
        return timetable;
    }
}
