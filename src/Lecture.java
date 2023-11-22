import java.util.List;

public class Lecture {
    private String subjectCode; // 과목 코드
    private String teacher; // 선생님
    private String lectureCode; // 수업 코드
    private final List<TimeTable> timetable; //타임 테이블
    private final String limit; // 강의 정원
    private String count; // 강의 현원

    public  String lectureName; // 수업 이름

    public Lecture(String LectureCode, String subjectCode,String lectureName, String teacher, String limit, String count,
                   List<TimeTable> timetable) {
        this.subjectCode = subjectCode;
        this.lectureName = lectureName;
        this.teacher = teacher;
        this.lectureCode = LectureCode;
        this.limit = limit;
        this.count = count;
        this.timetable = timetable;
    }

    public String getLimit() {
        return this.limit;
    }

    public String getCount() {
        return this.count;
    }

    public void plusCount() {
        int tmp = Integer.parseInt(count) + 1;
//        ScannerUtils.print("추가! : " + tmp, true);
        this.count = Integer.toString(tmp);
    }

    public void showLecture() {
        ScannerUtils.print("수업코드 : " + this.lectureCode, false);
        ScannerUtils.print("    과목코드 : " + this.subjectCode, false);
        ScannerUtils.print("    선생님 :  " + this.teacher, false);
        for (TimeTable table : timetable) {
            table.showTimeTable();
        }
        ScannerUtils.print("", true);
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

    public String getLectureName() {
        return lectureName;
    }

    public List<TimeTable> getTimetable() {
        return timetable;
    }
}
