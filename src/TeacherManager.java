import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherManager implements BaseManager {
    private final List<Teacher> teachers = new ArrayList<>();
    private final Read read = new Read();

    private final List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일


    @Override
    public String getCsvFilePath() {
        return "src/teacher.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.TEACHER_ID, CommonPattern.STUDENT_NAME, CommonPattern.SUBJECT_CODE,
                "+" + CommonPattern.TIMETABLE_CODE);
    }

    @Override
    public CsvExtraElementOption getExtraElementOption() {
        return new CsvExtraElementOption(false, "");
    }

    @Override
    public boolean checkIsCsvRowsRequired() {
        return true;
    }

    public void makeTeachers() {
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

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (Teacher tea : teachers) {
            List<String> tmpData = new ArrayList<>(
                    Arrays.asList(tea.getCode(),tea.getName(),tea.getSubjectCode()));
            for (TimeTable timeTable : tea.getTimeTables()) {
                tmpData.add(timeTable.getCode());
            }

            int size = tmpData.size();
            String[] data = tmpData.toArray(new String[size]);

            saveData.add(data);
        }

        Read.writeTeacherCSV(saveData);
    }
}

