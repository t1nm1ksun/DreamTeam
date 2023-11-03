import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private String phoneNum;
    private List<String> lectureList = new ArrayList<String>();

    public Student(String id, String name, String phoneNum, List<String> lectureList) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.lectureList = lectureList;
    }

    public Student(String id) {
        this.id = id;
        this.name = "";
        this.phoneNum = "";
        this.lectureList = null;
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

    public List<String> getLectureList() {
        return lectureList;
    }


    public void addLecture(String lectureCode) {
        this.lectureList.add(lectureCode);
    }

    public void setLectureList(List<String> lectureList) {
        this.lectureList = lectureList;
    }

    public void addLectureList(String lecture) {
        this.lectureList.add(lecture);
    }

    public void deleteLecture(String lectureCode) {
        this.lectureList.remove(lectureCode);
    }
}
