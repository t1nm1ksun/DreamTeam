import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherManager implements BaseManager {
    private final List<Teacher> teachers = new ArrayList<>();

    private final List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일


    @Override
    public String getCsvFilePath() {
        return "src/teacher.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.TEACHER_ID, CommonPattern.TEACHER_NAME, "+" + CommonPattern.SUBJECT_CODE);
    }

    @Override
    public CsvExtraElementOption getExtraElementOption() {
        return new CsvExtraElementOption(true, getCsvFilePath() + "파일의 선생님 데이터는 적어도 하나의 과목 코드를 갖고 있어야 합니다.");
    }

    @Override
    public boolean checkIsCsvRowsRequired() {
        return true;
    }

    public void makeTeachers() {
        List<List<String>> teacherlist = Read.readCSV(getCsvFilePath());

        for (List<String> item : teacherlist) {
            List<String> subjectCodeList = new ArrayList<>();
            for(int i = 2; i < item.size(); i++){
                subjectCodeList.add(item.get(i));
            }
            Teacher t1 = new Teacher(item.get(0), item.get(1), subjectCodeList);
            teachers.add(t1);
        }
    }


    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (Teacher tea : teachers) {
            List<String> subjectCodes = tea.getSubjectCode();
            List<String> tmpData = new ArrayList<>(Arrays.asList(tea.getCode(),tea.getName()));
            tmpData.addAll(subjectCodes);

            int size = tmpData.size();
            String[] data = tmpData.toArray(new String[size]);

            saveData.add(data);
        }

        Read.writeTeacherCSV(saveData);
    }
}

