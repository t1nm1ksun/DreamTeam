public class Teacher {
    private String code;
    private String name;

    public Teacher(String name, String code) {
        this.code = code;
        this.name = name;
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
}
