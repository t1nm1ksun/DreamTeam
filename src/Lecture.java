import java.util.List;

public class Lecture {
    private String subjectCode; // 과목 코드

    private String lectureCode; // 수업 코드

    public  String lectureName; // 수업 이름



    public Lecture(String LectureCode,String lectureName, String subjectCode) {
        this.subjectCode = subjectCode;
        this.lectureCode = LectureCode;
        this.lectureName = lectureName;
    }



    public void showLecture() {
        ScannerUtils.print("수업코드 : " + this.lectureCode, false);
        ScannerUtils.print("    수업이름 :  " + this.lectureName, false);
        ScannerUtils.print("    과목코드 : " + this.subjectCode, false);

        ScannerUtils.print("", true);
    }





    // 과목 정보 반환
    public String getSubjectCode() {
        return subjectCode;
    }



    public String getLectureCode() {
        return lectureCode;
    }

    public String getLectureName() {
        return lectureName;
    }


}
