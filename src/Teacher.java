public class Teacher {
    private String code;
    private String name;
    private final String subjectCode;

    //과목코드 추가!
    public Teacher(String name, String code, String subjectCode) {
        this.code = code;
        this.name = name;
        this.subjectCode = subjectCode;
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
}
