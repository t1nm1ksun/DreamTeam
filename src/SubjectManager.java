import java.util.ArrayList;
import java.util.List;

public class SubjectManager {
    private List<Subject> subjects;
    private Read read = new Read();

    public SubjectManager() {
        //여기서 csv 파일 읽어서 과목들을 생성
        subjects = new ArrayList<>();
        List<List<String>> subjectList = read.readCSV("src/subject.csv");

        for (List<String> item : subjectList) {
            Subject s1 = new Subject(item.get(0), item.get(1));
            subjects.add(s1);
        }
    }

    public String find(String input) {
        for (Subject sbj : subjects) {
            if (sbj.getName().equals(input)) {
                return sbj.getCode();
            }
        }
        return "";
    }

    public List<Subject> getSubjectss() {
        return subjects;
    }
}
