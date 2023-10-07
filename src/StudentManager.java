import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private ArrayList<Student> studentList = new ArrayList<Student>();


    private List<String[]> saveData = new ArrayList<>(); //프로그램 종료시 저장파일
    private List<Student> student = new ArrayList<>(); // 수업 목록을 저장할 리스트
    private Read read = new Read();

    /**
     * csv로부터 읽어온파일들을 순서대로 lectures에 저장
     * 마지막에 한번에 저장하기 위해 saveData에 순차적 저장
     */
    public StudentManager() {
        List<List<String>> list = read.readCSV("src/student.csv");
        for(List<String> item : list){
            if(item.size()>=3){
                Student s1 = new Student(item.get(0), item.get(1), item.get(2));
                student.add(s1);
            }
        }
        //파일을 읽어서 학생 class들을 만들기
    }



    /// 학생 정보 리스트 출력 함수
    public void showStudentList() {
        System.out.println("[학생 정보 리스트]");
        //TODO: 데이터 파일 접근해서 학생 정보 리스트 출력
        if (student.isEmpty()) {
            ScannerUtils.print("등록된 학생이 없습니다.", true);
        } else {
            System.out.println("등록된 수업 목록:");
            ScannerUtils.print("   [학생ID]     [이름]       [휴대폰 번호]     [수강 중인 수업] ", true);
            for (Student students : student) {
                ScannerUtils.print("|   "+students.getId()+"       ", false);
                ScannerUtils.print(students.getName()+"       ", false);
                ScannerUtils.print(students.getPhoneNum()+"         ", false);
                ScannerUtils.print(students.getLectureList()+"          |", false);
                ScannerUtils.print("", true);
            }
        }
    }

    /// 학생 등록 함수
    public void addStudent() {
        String[] dataList = new String[3];

        System.out.println("[2. 학생 등록을 선택하셨습니다.]");
        System.out.print("등록할 학생의 이름을 입력하세요 (* 2~10자, 공백 없이 한글로만 입력하세요 *): ");
        //TODO: 넥스트 라인 필요한지 확인
        String name = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_NAME, CommonPatternError.STUDENT_NAME);
        //TODO: 입력받은 학생 이름 set 하기
        dataList[1]=name;

        System.out.print("학생의 전화번호를 입력하세요 (* 띄어쓰기나 '-'없이 11개의 숫자를 한 번에 입력하세요 *): ");


        String phoneNum = ScannerUtils.scanWithPattern(CommonPattern.PHONE_NUMBER, CommonPatternError.PHONE_NUMBER);
        //TODO: 이미 등록 되어있는 번호인지 확인, 입력받은 전화번호 set 하기
        dataList[2]=phoneNum;
        // 학생 리스트가 비어있지 않은 경우에만 가장 마지막 학생의 ID에 1을 더한 값을 dataList[0]에 넣습니다.
        if (!student.isEmpty()) {
            // 가장 마지막 학생을 가져옵니다.
            Student lastStudent = student.get(student.size() - 1);
            int lastStudentId = Integer.parseInt(lastStudent.getId());
            dataList[0] = String.valueOf(lastStudentId + 1);
        } else {
            // 학생 리스트가 비어있을 경우, 첫 번째 학생의 ID는 "4001"로 설정합니다.
            dataList[0] = "4001";
        }
        Student student2 = new Student(dataList[0],dataList[1],dataList[2]);
        student.add(student2);
        System.out.println("[학생 등록이 완료되었습니다.]");
    }

    /**학생 아이디로 등록된 학생인지 찾는것*/
    public Student findStudentById(String id) {
        for (Student student : student) {
            if (student.getId().equals(id)) {
                return student; // 학생 ID가 일치하는 학생을 반환합니다.
            }
        }
        return null; // 학생 ID가 일치하는 학생을 찾지 못한 경우 null을 반환합니다.
    }

    /**등록된 번호 읹지 찾는것*/
    public boolean findPhoneNum(String num) {
        for (Student student : student) {
            if (student.getPhoneNum().equals(num)) {
                return true; // 등록 되어있으면 true
            }
        }
        return false; // 등록 안되어있으면 false
    }


    /// 학생 정보 변경 함수

    public void editStudent() {
        System.out.println("[3. 학생 정보 편집을 선택하셨습니다.]");
        showStudentList();
        System.out.print("편집하고 싶은 학생 ID를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");
        String id = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_ID, CommonPatternError.STUDENT_ID);

        // 학생 ID로 학생을 찾습니다.
        Student studentToEdit = findStudentById(id);

        if (studentToEdit != null) {
            System.out.println(studentToEdit.getName() + " 학생의 정보를 변경합니다.");
            System.out.println("[1. 이름  2. 전화번호  3. 수업 목록  4. 나가기]");
            System.out.print("변경하고 싶은 학생 정보를 선택하세요 (* 1~4 중 원하는 메뉴의 숫자 하나를 입력하세요 *): ");
            String menuNum = ScannerUtils.scanWithPattern(CommonPattern.FOUR_CHOICE, CommonPatternError.FOUR_CHOICE);

            // 변경 작업을 수행합니다.
            if (menuNum.equals("1")) {
                // 이름 변경
                System.out.println("[1. 이름 변경을 선택하셨습니다.]");
                System.out.print("새로운 이름을 입력해 주세요: ");
                String newName = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_NAME, CommonPatternError.STUDENT_NAME);
                studentToEdit.setName(newName);
                System.out.println("이름이 변경되었습니다.");
            } else if (menuNum.equals("2")) {
                // 전화번호 변경
                System.out.println("[2. 전화번호 변경을 선택하셨습니다.]");
                System.out.print("새로운 전화번호를 입력해 주세요: ");
                String newPhoneNum = ScannerUtils.scanWithPattern(CommonPattern.PHONE_NUMBER, CommonPatternError.PHONE_NUMBER);
                studentToEdit.setPhoneNum(newPhoneNum);
                System.out.println("전화번호가 변경되었습니다.");
            } else if (menuNum.equals("3")) {
                // 수업 목록 편집
                System.out.println("[3. 듣는 수업 목록 편집을 선택하셨습니다.]");
                // TODO: 수업 추가 또는 삭제 작업 수행
            } else {
                System.out.println("[4. 나가기를 선택하셨습니다.]");
            }
        } else {
            System.out.println("해당 학생을 찾을 수 없습니다.");
        }
    }



    //학생 삭제 함수
    public void deleteStudent() {
        System.out.println("[4. 학생 정보 삭제를 선택하셨습니다.]");
        showStudentList();
        System.out.print("삭제하고 싶은 학생 ID를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");

        String id = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_ID, CommonPatternError.STUDENT_ID);

        // 학생 ID로 학생을 찾습니다.
        Student studentToDelete = findStudentById(id);

        if (studentToDelete != null) {
            // 학생을 학생 리스트에서 삭제합니다.
            student.remove(studentToDelete);
            System.out.println("해당 학생의 정보가 삭제되었습니다.");

            // TODO: 데이터 파일에서도 해당 학생을 삭제하는 코드를 추가하세요.
            // dataFile.delete(id);
        } else {
            System.out.println("[오류: 입력 형식이 맞지 않거나 해당 아이디의 학생이 존재하지 않습니다.]");
        }
    }


    public void saveDataFile() {
        //lectures 들을 알맞은 형식의 데이터로 전환한 뒤 파일에 저장
        for(Student stu : student) {
            String[] tmpData = {stu.getId(),stu.getName(),stu.getPhoneNum()};
            saveData.add(tmpData);
        }
        read.writeStudentCSV(saveData);
    }


    //학생 관리 함수
    public void management_Student(){
        StudentManager sm = new StudentManager();
        StudentMenuHandler.handle(sm);
//        System.out.println(Main.manageMenu);
        System.out.println("탈출");

    }
}
