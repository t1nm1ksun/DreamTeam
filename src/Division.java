import java.util.List;

public class Division {
    private String divisionCode; // 과목 코드
    private String lectureCode; // 수업 코드
    private final List<TimeTable> timetable; //타임 테이블
    private final String limit; // 강의 정원
    private String teacher; // 선생님

    public Division(String teacher, String limit, String count,
                      List<TimeTable> timetable){
        this.teacher = teacher;
        this.limit = limit;
        this.timetable = timetable;
    }

    public String getLimit() {
        return this.limit;
    }

    public int getCount() {
        List<Student> takeCoursesStudents = Main.studentManager.getStudentsByDivisionCode(divisionCode);
        return takeCoursesStudents.size();
    }



    // 선생님 정보 설정
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void addTimetable(TimeTable timeTable) {
        this.timetable.add(timeTable);
    }

    // 선생님 정보 반환
    public String getTeacher() {
        return teacher;
    }

    public List<TimeTable> getTimetable() {
        return timetable;
    }


    public void showDivision() {
        ScannerUtils.print("분반코드 : " + this.divisionCode, false);
        ScannerUtils.print("    수업코드 : " + this.lectureCode, false);
        ScannerUtils.print("    선생님 :  " + this.teacher, false);
        for (TimeTable table : timetable) {
            table.showTimeTable();
        }
        ScannerUtils.print("", true);
    }

    // 과목 정보 설정
    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }



    // 수업 코드 정보 설정
    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }



    // 과목 정보 반환
    public String getLectureCode() {
        return lectureCode;
    }



    public String getDivisionCode() {
        return lectureCode;
    }




}

