import java.util.ArrayList;

public class Student {
    private int id;
    private String name;
    private String phoneNum;
    private ArrayList<Integer> lectureList = new ArrayList<Integer>();

    public Student(int id, String name, String phoneNum, ArrayList<Integer> lectureList) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.lectureList = lectureList;
    }

    // Getter와 Setter 메서드 추가 (필요에 따라)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public ArrayList<Integer> getLectureList() {
        return lectureList;
    }

    public void setLectureList(ArrayList<Integer> lectureList) {
        this.lectureList = lectureList;
    }

}
