import java.util.ArrayList;

public class Student {
    private String id;
    private String name;
    private String phoneNum;
    private ArrayList<String> lectureList = new ArrayList<String>();

    public Student(String id, String name, String phoneNum, ArrayList<String> lectureList) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.lectureList = lectureList;
    }

    public Student(String id, String name, String phoneNum) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public ArrayList<String> getLectureList() {
        return lectureList;
    }

    public void setLectureList(ArrayList<String> lectureList) {
        this.lectureList = lectureList;
    }
}
