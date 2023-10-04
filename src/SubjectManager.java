import java.util.ArrayList;
import java.util.List;
public class SubjectManager {
    private List<Subject> subjects;

    public SubjectManager() {
        //여기서 csv 파일 읽어서 과목들을 생성
        subjects = new ArrayList<>();
        Subject sbj = new Subject("수학", "1001");
        Subject sb2 = new Subject("영어", "1002");
        subjects.add(sbj);
        subjects.add(sb2);
    }

    public  String find(String input) {
        for(Subject sbj : subjects) {
            if(sbj.getName().equals(input)) {
                return sbj.getCode();
            }
        }
        return "";
    }
}
