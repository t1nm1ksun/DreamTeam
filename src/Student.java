import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private String phoneNum;
    private List<String> divisionCodes = new ArrayList<String>();

    public Student(String id, String name, String phoneNum, List<String> divisionCodes) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.divisionCodes = divisionCodes;
    }

    public Student(String id) {
        this.id = id;
        this.name = "";
        this.phoneNum = "";
        this.divisionCodes = null;
    }
    
    public Student(String id, String name, String phoneNum) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String getId() {
        return id;
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

    public List<String> getDivisionCodes() {
        return divisionCodes;
    }


    public void addDivisionCode(String divisionCode) {
        this.divisionCodes.add(divisionCode);
    }



    public void deleteDivisionCode(String divisionCode) {
        this.divisionCodes.remove(divisionCode);
    }
}
