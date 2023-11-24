import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LectureManager implements BaseManager {

    private static Integer maxCode = 2000;
    private int maxLecture = 0; //수업 생성 막기
    private final List<String[]> saveData = new ArrayList<>(); //프로그램 종료 시 저장 파일
    private final List<Lecture> lectures = new ArrayList<>(); // 수업 목록을 저장할 리스트

    @Override
    public String getCsvFilePath() {
        return "src/lecture.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.LECTURE_CODE, CommonPattern.LECTURE_NAME,CommonPattern.SUBJECT_CODE);
    }

    @Override
    public CsvExtraElementOption getExtraElementOption() {
        return new CsvExtraElementOption(true, getCsvFilePath() + "파일의 수업 데이터는 적어도 하나의 타임테이블ID를 갖고 있어야 합니다.");
    }

    @Override
    public boolean checkIsCsvRowsRequired() {
        return false;
    }

    public void makeLectures() {
        List<List<String>> list = Read.readCSV("src/lecture.csv");
        // List<String> regexList = Arrays.asList(CommonPattern.LECTURE_CODE, CommonPattern.SUBJECT_CODE,CommonPattern.TEACHER_ID,CommonPattern.)

        for (List<String> item : list) {
            //csv 파일들을 읽어와서 강의들을 생성함
            if (Integer.parseInt(item.get(0)) >= maxCode) {
                maxCode = max(maxCode, Integer.parseInt(item.get(0)));
            }

            Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2));
            lectures.add(l1);
            maxLecture++;
        }
    }

    public Lecture hasLecture(String lectureCode) {
        for (Lecture lecture : lectures) {
            if (lecture.getLectureCode().equals(lectureCode)) {
                return lecture;
            }
        }
        return null;
    }

    // StudentManager에서 학생이 수강 중인 수업을 조회하기 위한 메서드
    public void displayLecture(String lectureCode) {
        if (!lectures.isEmpty() && hasLecture(lectureCode) != null) {
            //TODO: Lecture 형식에 맞춰서 수정하기, timeTableManager로 수업 정보 가져오기 (창균, 민석)
            ScannerUtils.print("|    " + hasLecture(lectureCode).getLectureCode() + "       ", false);
            ScannerUtils.print(hasLecture(lectureCode).getSubjectCode() + "         ", false);
            ScannerUtils.print(hasLecture(lectureCode).getLectureName() + "         ", false);
            ScannerUtils.print("", true);
        }
    }

    // 모든 수업 목록을 조회하는 메서드
    public boolean displayLectures() {
        if (lectures.isEmpty()) {
            ScannerUtils.print("등록된 수업이 없습니다.", true);
            return false;
        } else {
            System.out.println("[등록된 수업 목록]");
            ScannerUtils.print("수업코드    과목코드     수업이름", true);

            for (Lecture lecture : lectures) {
                ScannerUtils.print(lecture.getLectureCode() + "       ", false);
                ScannerUtils.print(lecture.getSubjectCode() + "       ", false);
                ScannerUtils.print(lecture.getLectureName()+ "       ", false);
                ScannerUtils.print("", true);
            }
        }
        return true;
    }



    public boolean hasSelectedLecture(String lectureCode) {
        for (Lecture lecture : lectures) {
            if (lecture.getLectureCode().equals(lectureCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteLecture() {
        if (!displayLectures()) {
            return false;
        }

        ScannerUtils.print("삭제할 수업 코드를 입력해 주세요: ", false);
        String InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                CommonPatternError.LECTURE_CODE);

        while (!hasSelectedLecture(InputLectureCode)) {
            ScannerUtils.print("존재하지 않는 수업입니다. 다시 입력 바랍니다.", true);
            InputLectureCode = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE,
                    CommonPatternError.LECTURE_CODE);
        }

        for (Lecture lec : lectures) {
            //삭제할 강의가 존재한다면 lectures 에서 삭제함
            if (InputLectureCode.equals(lec.getLectureCode())) {
                if(maxCode == Integer.parseInt(lec.getLectureCode())) {
                    maxCode--;
                }
                // 해당 수업을 가지고 있는 디비젼들 및 디비젼들이 가진 타임테이블 까지 모두 삭제
                Main.divisionManager.deleteDivisionByLectureCode(InputLectureCode);

                lectures.remove(lec);
                break;
            }
        }

        return true;
    }

    public void addLecture() {
        ScannerUtils.print("[2. 수업 추가를 선택하셨습니다.]", true);

        String inputLectureName = "", inputSubjectCode = "";

        // 과목 선택
        for (int i = 0; i < Main.subjectManager.getSubjectss().size(); i++) {
            Subject subject = Main.subjectManager.getSubjectss().get(i);
            ScannerUtils.print((i + 1) + ") " + subject.getName() + "    ", false);
        }
        ScannerUtils.print("\n추가할 수업의 과목을 선택해 주세요: ", false);

        String ChoiceNumber = "^[1-" + Main.subjectManager.getSubjectss().size() + "]$";
        String input = ScannerUtils.scanWithPattern(ChoiceNumber, CommonPatternError.LECTURE_CODE);

        // 과목 코드 저장
        inputSubjectCode = Main.subjectManager.getSubjectss().get(Integer.parseInt(input) - 1).getCode();

        ScannerUtils.print(
                "[" + Main.subjectManager.getSubjectss().get(Integer.parseInt(input) - 1).getName()
                        + "을(를) 선택하셨습니다.]",
                true);

        // 추가할 수업 제목 작성
        ScannerUtils.print("추가할 수업 제목을 입력해주세요", true);
        inputLectureName = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_NAME, CommonPatternError.LECTURE_NAME);

        int cmpCode = 2000;
        boolean isNew = false;
        lectures.sort(Comparator.comparing(Lecture::getLectureCode));

        for (Lecture lecture : lectures) {
            if (!Integer.toString(cmpCode).equals(lecture.getLectureCode())) {
                Lecture newLecture = new Lecture(Integer.toString(cmpCode),inputLectureName, inputSubjectCode);
                lectures.add(newLecture);
                isNew = true;
                break;
            } else {
                cmpCode++;
            }
            maxCode = max(maxCode, Integer.parseInt(lecture.getLectureCode()));
        }

        if (!isNew) {
            maxCode = max(maxCode, cmpCode);
            Lecture newLecture = new Lecture(Integer.toString(cmpCode),inputLectureName, inputSubjectCode);
            lectures.add(newLecture);
        }

    }


    public void editDate() {

    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (Lecture lec : lectures) {
            List<String> tmpData = new ArrayList<>(
                    Arrays.asList(lec.getLectureCode(), lec.getSubjectCode(), lec.getLectureName()));

            int size = tmpData.size();
            String[] data = tmpData.toArray(new String[size]);

            saveData.add(data);
        }

        Read.writeLectureCSV(saveData);
    }

    public int getMaxLecture() {
        return maxLecture;
    }


    public Lecture getLectureByCode(String lectureCode) {

        for (Lecture lecture : lectures) {
            if (lecture.getLectureCode().equals(lectureCode)) {
                return lecture;
            }
        }
        return null;
    }
}
