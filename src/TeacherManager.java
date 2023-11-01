import java.util.ArrayList;
import java.util.List;

public class TeacherManager {
    private final List<Teacher> teachers = new ArrayList<>();
    public Read read = new Read();

    public TeacherManager() {
        //여기서 csv 파일 읽어서 과목들을 생성
        List<List<String>> teacherlist = read.readCSV("src/teacher.csv");

        for (List<String> item : teacherlist) {
            Teacher t1 = new Teacher(item.get(0), item.get(1), item.get(2));
            teachers.add(t1);
        }

    }

    public String find(String input) {
        for (Teacher t : teachers) {
            if (t.getName().equals(input)) {
                return t.getCode();
            }
        }
        return "";
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}
