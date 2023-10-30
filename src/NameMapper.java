import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameMapper {
    private static NameMapper instance;
    private static final Map<Integer, String> LECTURE_TIMES = new HashMap<Integer, String>();
    private static final Map<Integer, String> LECTURE_DATE = new HashMap<Integer, String>();
    private static final Map<Integer, String> TEACHERS = new HashMap<Integer, String>();
    private static final Map<Integer, String> SUBJECTS = new HashMap<Integer, String>();

    private NameMapper() {
        initializeLectureTimes();
        initializeLectureDates();
        initializeTeachers();
        initializeSubjects();
    }

    public static synchronized NameMapper getInstance() {
        if (instance == null) {
            instance = new NameMapper();
        }
        return instance;
    }

    private void initializeLectureTimes() {
        LECTURE_TIMES.clear();
        LECTURE_TIMES.put(1, "14:00~16:00");
        LECTURE_TIMES.put(2, "16:00~18:00");
        LECTURE_TIMES.put(3, "18:00~20:00");
        LECTURE_TIMES.put(4, "20:00~22:00");
    }

    private void initializeLectureDates() {
        //TODO: 여기 바뀐 형식에 맞춰서 수정해 주세용 (기웅이형)
        LECTURE_DATE.clear();
        LECTURE_DATE.put(1, "월 수 금");
        LECTURE_DATE.put(2, "화 목 토");
    }

    private void initializeSubjects() {
        SUBJECTS.clear();

        Read read = new Read();
        List<List<String>> subjectList = read.readCSV("src/subject.csv");

        for (List<String> item : subjectList) {
            SUBJECTS.put(Integer.parseInt(item.get(1)), item.get(0));
        }
    }

    private void initializeTeachers() {
        TEACHERS.clear();

        Read read = new Read();
        List<List<String>> teacherlist = read.readCSV("src/teacher.csv");

        for (List<String> item : teacherlist) {
            TEACHERS.put(Integer.parseInt(item.get(1)), item.get(0));
        }
    }

    public String getLectureTimes(int key) {
        return LECTURE_TIMES.get(key);
    }

    public String getLectureDate(int key) {
        return LECTURE_DATE.get(key);
    }

    public String getTeacher(int key) {
        return TEACHERS.get(key);
    }

    public String getSubject(int key) {
        return SUBJECTS.get(key);
    }

}
