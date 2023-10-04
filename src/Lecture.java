public class Lecture {
    private String subject;     // 과목
    private String teacher;     // 선생님
    private String dayOfWeek;  // 요일
    private String time;        // 수업 시간

    public Lecture(String subject, String teacher, String dayOfWeek, String time) {
        this.subject = subject;
        this.teacher = teacher;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    public void classList(){
        System.out.println("[수업 정보 리스트]");
        // 데이터 파일 접근해서 수업 정보 리스트 출력
        this.subject = "영어";
        this.teacher = "이승범";
        this.dayOfWeek = "월 수 금";
        this.time = "타임 1";
    }
    // 과목 정보 설정
    public void setSubject(String subject) {
        this.subject = subject;
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

    // 과목 정보 반환
    public String getSubject() {
        return subject;
    }

    // 선생님 정보 반환
    public String getTeacher() {
        return teacher;
    }

    // 요일 정보 반환
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    // 수업 시간 정보 반환
    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "과목: " + subject + ", 선생님: " + teacher + ", 요일: " + dayOfWeek + ", 시간: " + time;
    }

}