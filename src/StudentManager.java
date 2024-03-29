import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentManager implements BaseManager {
    private final List<String[]> saveData = new ArrayList<>(); //프로그램 종료시 저장 파일
    private final List<Student> studentList = new ArrayList<>(); // 학생 목록을 저장할 리스트

    @Override
    public String getCsvFilePath() {
        return "src/student.csv";
    }

    @Override
    public List<String> getRegexList() {
        return Arrays.asList(CommonPattern.STUDENT_ID, CommonPattern.STUDENT_NAME, CommonPattern.PHONE_NUMBER,
                "+" + CommonPattern.DIVISION_CODE);
    }

    @Override
    public CsvExtraElementOption getExtraElementOption() {
        return new CsvExtraElementOption(false, "");
    }

    @Override
    public boolean checkIsCsvRowsRequired() {
        return false;
    }

    public void makeStudents() {
        List<List<String>> list = Read.readCSV("src/student.csv");

        for (List<String> item : list) {
            if (item.size() > 3) {
                List<String> lectureList = new ArrayList<>(); // 수업 목록을 저장할 리스트

                for (int i = 3; i < item.size(); i++) {
                    lectureList.add(item.get(i));
                }
                Student s = new Student(item.get(0), item.get(1), item.get(2), lectureList);
                studentList.add(s);
            } else if (item.size() == 3) {
                Student s = new Student(item.get(0), item.get(1), item.get(2));
                studentList.add(s);
            }
        }
        // 파일을 읽어서 학생 class들을 만들기
    }

    /**
     * 학생 정보 리스트 출력 함수
     */
    public void showStudentList() {
        System.out.println("[학생 정보 리스트]");
        // 데이터 파일 접근해서 학생 정보 리스트 출력
        if (studentList.isEmpty()) {
            ScannerUtils.print("등록된 학생이 없습니다.", true);
        } else {
            ScannerUtils.print("   [학생 ID]    [이름]     [휴대폰 번호]     [수강 중인 수업 분반]", true);
            for (Student students : studentList) {
                ScannerUtils.print("|    " + students.getId() + "      ", false);
                ScannerUtils.print(students.getName() + "      ", false);
                ScannerUtils.print(students.getPhoneNum() + "      ", false);
                ScannerUtils.print(students.getDivisionCodes() + "", true);
            }
        }
    }

    /**
     * 학생 등록 함수
     */
    public void addStudent() {
        String[] dataList = new String[3];

        System.out.println("[2. 학생 등록을 선택하셨습니다.]");
        System.out.print("등록할 학생의 이름을 입력하세요 (* 2~10자, 공백 없이 한글로만 입력하세요 *): ");
        String name = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_NAME, CommonPatternError.STUDENT_NAME);
        // 입력 받은 학생 이름 set 하기
        dataList[1] = name;

        while (true) {
            System.out.print("학생의 전화번호를 입력하세요 (* 띄어쓰기나 '-'없이 11개의 숫자를 한 번에 입력하세요 *): ");
            String phoneNum = ScannerUtils.scanWithPattern(CommonPattern.PHONE_NUMBER, CommonPatternError.PHONE_NUMBER);

            // 이미 등록 되어 있는 번호인지 확인 후 입력 받은 전화번호 set 하기
            if (!findPhoneNum(phoneNum)) {
                dataList[2] = phoneNum;
                break;
            } else {
                System.out.println("이미 등록된 번호입니다.");
                break;
            }
        }
        // 비어 있는 ID 중 가장 작은 ID를 할당해 줍니다.
        if (!studentList.isEmpty()) { // 학생 리스트가 비어 있지 않은 경우
            int newStudentId = 0;

            // 4001부터 4100까지의 학생 ID를 순차적으로 검사합니다.
            for (int i = 4000; i < 4100; i++) {
                boolean idExists = false;

                // 현재 학생 리스트에 있는 학생들의 ID와 비교합니다.
                for (Student existingStudent : studentList) {
                    if (existingStudent.getId().equals(String.valueOf(i))) {
                        idExists = true;
                        break; // 이미 사용 중인 ID를 찾으면 루프 종료
                    }
                }

                // 사용 중이지 않은 ID를 찾으면 그 ID를 사용합니다.
                if (!idExists) {
                    newStudentId = i;
                    break; // 사용 가능한 ID를 찾으면 루프 종료
                }
            }
            dataList[0] = String.valueOf(newStudentId);
        } else {
            // 학생 리스트가 비어있을 경우, 첫 번째 학생의 ID는 "4001"로 설정합니다.
            dataList[0] = "4000";
        }
        if (dataList[2] != null) {
            Student newStudent = new Student(dataList[0], dataList[1], dataList[2]);
            studentList.add(newStudent);
            System.out.println("[학생 등록이 완료되었습니다.]");
        }

    }

    /**
     * 학생 아이디로 등록된 학생인지 찾는 함수
     */
    public Student findStudentById(String id) {
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                return student; // 학생 ID가 일치하는 학생을 반환합니다.
            }
        }
        return null; // 학생 ID가 일치하는 학생을 찾지 못한 경우 null을 반환합니다.
    }

    /**
     * 이미 등록된 전화번호인지 찾는 함수
     */
    public boolean findPhoneNum(String num) {
        for (Student student : studentList) {
            if (student.getPhoneNum().equals(num)) {
                return true; // 등록 되어 있으면 true
            }
        }
        return false; // 등록 안 되어 있으면 false
    }

    /**
     * 학생이 수강 중인 수업 리스트를 보여주는 함수
     */
    public List<String> showDivisionList(String studentID) {
        for (Student stu : studentList) {
            if (stu.getId().equals(studentID) && !stu.getDivisionCodes().isEmpty()) {
                return stu.getDivisionCodes();
            }
        }
        return null;
    }

    /**
     * 이미 등록된 수업인지 찾는 함수
     */
    public List<String> findLectureCode(Student studentToEdit, String lectureCode) {
        List<String> lectureList = studentToEdit.getDivisionCodes();

        for (String code : lectureList) {
            if (lectureCode.equals(code)) {
                return lectureList;
            }
        }
        return null;
    }

    /**
     * 학생 정보 변경 함수
     */
    public void editStudent() {
        System.out.println("[3. 학생 정보 편집을 선택하셨습니다.]");
        showStudentList();

        if (!studentList.isEmpty()) {
            System.out.print("편집하고 싶은 학생의 ID를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");
            String id = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_ID, CommonPatternError.STUDENT_ID);

            // 학생 ID로 학생을 찾습니다.
            Student studentToEdit = findStudentById(id);

            if (studentToEdit != null) {
                System.out.println(studentToEdit.getName() + " 학생의 정보를 변경합니다.");
                System.out.println("[1.이름  2.전화번호  3.분반 목록  4.나가기]");
                System.out.print("변경하고 싶은 학생 정보를 선택하세요 (* 1~4 중 원하는 메뉴의 숫자 하나를 입력하세요 *): ");
                String menuNum = ScannerUtils.scanWithPattern(CommonPattern.FOUR_CHOICE,
                        CommonPatternError.FOUR_CHOICE);

                // 변경 작업을 수행합니다.
                switch (menuNum) {
                    case "1" -> {
                        // 이름 변경
                        System.out.println("[1. 이름 변경을 선택하셨습니다.]");
                        System.out.print("새로운 이름을 입력해 주세요: ");
                        String newName = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_NAME,
                                CommonPatternError.STUDENT_NAME);
                        studentToEdit.setName(newName);
                        System.out.println("이름이 변경되었습니다.");
                    }
                    case "2" -> {
                        // 전화번호 변경
                        System.out.println("[2. 전화번호 변경을 선택하셨습니다.]");
                        System.out.print("새로운 전화번호를 입력해 주세요: ");
                        String newPhoneNum = ScannerUtils.scanWithPattern(CommonPattern.PHONE_NUMBER,
                                CommonPatternError.PHONE_NUMBER);
                        // 이미 등록 되어 있는 번호인지 확인 후 입력 받은 전화번호 set 하기
                        if (!findPhoneNum(newPhoneNum)) {
                            studentToEdit.setPhoneNum(newPhoneNum);
                            System.out.println("전화번호가 변경되었습니다.");
                        } else {
                            System.out.println("이미 등록된 번호입니다.");
                        }
                    }
                    case "3" -> {
                        // 수업 목록 편집
                        System.out.println("[3. 듣는 분반 목록 편집을 선택하셨습니다.]");
                        System.out.println("[수강 중인 분반 리스트]");

                        if (showDivisionList(id) != null) {
                            ScannerUtils.print("[분반코드]     [수업코드]     [수업제목]    [선생님 ID]   [정원/현원]   [강의실 및 날짜, 시간]", true);

                            for (String divisionCode : showDivisionList(id)) {
                                Main.divisionManager.displayDivision(divisionCode);
                            }
                        } else {
                            System.out.println("수강 중인 분반이 없습니다.");
                        }

                        // 수업 추가 또는 삭제 작업 수행
                        System.out.println("[1.분반 추가 2.분반 삭제]");
                        System.out.print("수행할 메뉴를 선택하세요 (* 1,2 중 원하는 메뉴의 숫자 하나를 입력하세요 *): ");
                        String lectureMenuNum = ScannerUtils.scanWithPattern(CommonPattern.TWO_CHOICE,
                                CommonPatternError.TWO_CHOICE);
                        if (lectureMenuNum.equals("1")) {
                            System.out.println("1. 분반 추가를 선택하셨습니다.");

                            // 선택한 학생이 수강중인 수업들 리스트
                            List<Division> divisions = new ArrayList<>();
                            boolean isSuccess = true;

                            for (String lectureCode : studentToEdit.getDivisionCodes()) {
                                Division tmpDivision = Main.divisionManager.getDivisionByCode(lectureCode);
                                if (tmpDivision != null) {
                                    // 학생이 듣는 수업 리스트 생성
                                    divisions.add(tmpDivision);
                                } else {
                                    // 학생이 듣는 수업 리스트 생성 실패시
                                    ScannerUtils.print("분반 추가에 실패했습니다!", true);
                                    return;
                                }
                            }

                            if (isSuccess) {
                                // 정보 변경할 학생의 code를 통해 학생이 수강중인 분반들을 가져옴
                                List<Division> studentDivisionList = Main.divisionManager.getStudentsDivisionList(studentToEdit);

                                // 추가할 수 있는 수업 리스트를 보여줌
                                if (!Main.divisionManager.showAddableDivisions(studentDivisionList)) {
                                    ScannerUtils.print("더이상 추가할 수 있는 분반이 없습니다.", true);
                                    break;
                                }

                                ScannerUtils.print("추가하려는 분반의 코드를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ", false);
                                String inputDivisionCode = ScannerUtils.scanWithPattern(CommonPattern.DIVISION_CODE,
                                        CommonPatternError.DIVISION_CODE);
                                if(Main.divisionManager.hasDivision(inputDivisionCode) == null) {
                                    ScannerUtils.print("존재하지 않는 분반입니다.", true);
                                    return;
                                }
                                // 입력한 분반 코드를 이용해 분반 생성
                                Division addingDivision = Main.divisionManager.getDivisionByCode(inputDivisionCode);
                                // 추가하려는 분반이 이미 듣고 있는 분반과 수업 코드가 겹치는지 확인
                                if(checkDivisionSameLecture(addingDivision, studentToEdit)) {
                                    ScannerUtils.print("해당 분반은 이미 듣고 있는 수업의 분반입니다. 다른 수업을 다시 선택해주세요", true);
                                    return;
                                }

                                ScannerUtils.print("선택한 분반 코드: " + inputDivisionCode, true);

                                // 해당 강의실의 제한 인원 수
                                int minLimit = Integer.parseInt(addingDivision.getLimit());
                                // 해당 강의실의 현재 수강 인원 수
                                int nowCount = addingDivision.getCount();

//                                ScannerUtils.print(minLimit + ", " + nowCount, true);
                                
                                String ERRMSG = "";
                                // 선택한 수업에 대해 강의실들의 수강 제한인원을 넘는지 체크
                                if (minLimit < nowCount + 1) {
                                    ScannerUtils.print(minLimit + ", " + nowCount, true);
                                    isSuccess = false;
                                    ERRMSG = "수강인원 초과";
                                }

                                if (isSuccess) {
                                    // 해당 학생의 수업 리스트에 수업 추가
                                    studentToEdit.addDivisionCode(inputDivisionCode);
                                    //lectureRoomManager.saveDataFile();
                                    ScannerUtils.print("성공적으로 추가되었습니다.", true);

                                } else {
                                    ScannerUtils.print("분반 추가에 실패했습니다2" + ERRMSG, true);
                                }
                            } else {
                                ScannerUtils.print("분반 추가에 실패했습니다3", true);
                            }

                        } else if (lectureMenuNum.equals("2")) {
                            System.out.println("2.분반 삭제를 선택하셨습니다.");
                            System.out.println("[수강 중인 분반 리스트]");

                            if (showDivisionList(id) != null) {
                                for (String divisionCode : showDivisionList(id)) {
                                    Main.divisionManager.displayDivision(divisionCode);
                                }

                                System.out.print("삭제하려는 분반 코드를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");
                                String deleteDivisionCode = ScannerUtils.scanWithPattern(CommonPattern.DIVISION_CODE,
                                        CommonPatternError.DIVISION_CODE);

                                if (Main.divisionManager.hasDivision(deleteDivisionCode) != null) { // 존재하는 수업코드인지 확인
                                    if (findLectureCode(studentToEdit, deleteDivisionCode) != null) { // 수강 중이던 수업이 맞는지 확인
                                        studentToEdit.deleteDivisionCode(deleteDivisionCode);
                                        System.out.println("분반이 삭제되었습니다.");
                                    } else {
                                        System.out.println("수강 중인 분반이 아닙니다.");
                                    }
                                } else {
                                    System.out.println("존재하지 않는 분반입니다.");
                                }
                            } else {
                                System.out.println("수강 중인 분반이 없습니다.");
                            }
                        }
                    }
                    default -> System.out.println("[나가기를 선택하셨습니다.]");
                }
            } else {
                System.out.println("존재하지 않는 학생ID 입니다.");
            }
        }
    }

    private boolean checkDivisionSameLecture(Division addingDivision, Student editingStudent) {
        List<Division> studentDivisionList = Main.divisionManager.getStudentsDivisionList(editingStudent);
        for(Division division : studentDivisionList) {
            if(division.getLectureCode().equals(addingDivision.getLectureCode())) return true;
        }
        return false;
    }

    public void checkDeletedDivision(String code) {
        // 학생이 듣는 수업이 삭제되었다면 확인해서 삭제
        for (Student stu : studentList) {
            stu.getDivisionCodes().removeIf(code::equals);
        }
    }

    /**
     * 학생 삭제 함수
     */
    public void deleteStudent() {
        System.out.println("[4. 학생 정보 삭제를 선택하셨습니다.]");
        showStudentList();
        System.out.print("삭제하고 싶은 학생 ID를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");

        String id = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_ID, CommonPatternError.STUDENT_ID);

        // 학생 ID로 학생을 찾습니다.
        Student studentToDelete = findStudentById(id);

        if (studentToDelete != null) {
            // 학생을 학생 리스트에서 삭제합니다.
            studentList.remove(studentToDelete);
            System.out.println("해당 학생의 정보가 삭제되었습니다.");
        } else {
            System.out.println("[오류: 입력 형식이 맞지 않거나 해당 아이디의 학생이 존재하지 않습니다.]");
        }
    }

    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for (Student stu : studentList) {
            List<String> lectureList = stu.getDivisionCodes();
            String lectureListString = String.join(",", lectureList);
            String[] tmpData = {stu.getId(), stu.getName(), stu.getPhoneNum(), lectureListString};
            //TODO: 수업리스트 저장 추가 해야댐
            saveData.add(tmpData);
        }
        Read.writeStudentCSV(saveData);
    }

    public List<Student> getStudentsByDivisionCode(String divisionCode){
        List<Student> students = new ArrayList<>();
        for(Student student: studentList){
            if(student.getDivisionCodes().contains(divisionCode)) students.add(student);
        }
        return students;
    }
}
