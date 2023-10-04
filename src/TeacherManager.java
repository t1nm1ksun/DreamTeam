import java.util.ArrayList;
import java.util.List;

public class TeacherManager {
    private List<Teacher> teachers;

    public TeacherManager() {
        //여기서 csv 파일 읽어서 과목들을 생성
        teachers = new ArrayList<>();
        Teacher t1 = new Teacher("신민석", "3001");
        Teacher t2 = new Teacher("이기웅", "3002");
        teachers.add(t1);
        teachers.add(t2);
    }

    public  String find(String input) {
        for(Teacher t : teachers) {
            if(t.getName().equals(input)) {
                return t.getCode();
            }
        }
        return "";
    }
}
