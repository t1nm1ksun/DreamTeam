import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherManager implements BaseManager {
    private  List<Teacher> teachers = new ArrayList<>();
    private final Read read = new Read();


    @Override
    public String getCsvFilePath() {
        return "src/teacher.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.TEACHER_ID, CommonPattern.STUDENT_NAME, CommonPattern.SUBJECT_CODE,
                "+" + CommonPattern.TIMETABLE_CODE);
    }

    public TeacherManager() {
        //여기서 csv 파일 읽어서 과목들을 생성

    }

    public void makeTeachers(){
        List<List<String>> teacherlist = read.readCSV("src/teacher.csv");

        for (List<String> item : teacherlist) {
            List<TimeTable> table = new ArrayList<>();

            for (int i = 3; i < item.size(); i++) {
                for (TimeTable t : Main.timetableManager.getTimetable()) {
                    if (t.getCode().equals(item.get(i))) {
                        table.add(t);
                        break;
                    }
                }
            }

            Teacher t1 = new Teacher(item.get(0), item.get(1), item.get(2), table);
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
