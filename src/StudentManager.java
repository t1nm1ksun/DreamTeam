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
            Student s1 = new Student(item.get(0), item.get(1), item.get(2));
            student.add(s1);
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
            ScannerUtils.print("학생ID    이름    휴대폰 번호    수강 중인 수업 ", true);
            for (Student students : student) {
                ScannerUtils.print(students.getId()+"       ", false);
                ScannerUtils.print(students.getName()+"       ", false);
                ScannerUtils.print(students.getPhoneNum()+"       ", false);
                ScannerUtils.print(students.getLectureList()+"     ", false);
                ScannerUtils.print("", true);
            }
        }
    }

    /// 학생 등록 함수
    public void addStudent() {
        System.out.println("[2. 학생 등록을 선택하셨습니다.]");
        System.out.print("등록할 학생의 이름을 입력하세요 (* 2~10자, 공백 없이 한글로만 입력하세요 *): ");
        //TODO: 넥스트 라인 필요한지 확인
        String name = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_NAME, CommonPatternError.STUDENT_NAME);
        //TODO: 입력받은 학생 이름 set 하기

        System.out.print("학생의 전화번호를 입력하세요 (* 띄어쓰기나 '-'없이 11개의 숫자를 한 번에 입력하세요 *): ");

        //TODO: 넥스트 라인 필요한지 확인
        String phoneNum = ScannerUtils.scanWithPattern(CommonPattern.PHONE_NUMBER, CommonPatternError.PHONE_NUMBER);
        //TODO: 입력받은 전화번호 set 하기

        System.out.println("[학생 등록이 완료되었습니다.]");
    }

    /// 학생 정보 변경 함수

    public void editStudent() {
        System.out.println("[3.학생 정보 편집을 선택하셨습니다.]");
        showStudentList();
        System.out.print("편집하고 싶은 학생 ID를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");
        //TODO: 넥스트 라인 필요한지 확인, id 정규식 추가되면 id로 추가하기

        String id = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_ID, CommonPatternError.STUDENT_ID);

        //TODO: studentList에 접근해서 학생 이름 get 하기
        System.out.print("~~~"); // 학생 이름 임시로
        System.out.println(" 학생의 정보를 변경합니다.]");
        System.out.println("[1. 이름  2. 전화번호  3. 수업 목록  4. 나가기]");
        System.out.print("변경하고 싶은 학생 정보를 선택하세요 (* 1~4 중 원하는 메뉴의 숫자 하나를 입력하세요 *): ");

        //TODO: 넥스트 라인 필요한지 확인
        String menuNum = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_TIME, CommonPatternError.LECTURE_TIME); // 임시 정규식

        if (menuNum.equals("1")) {
            System.out.println("[1. 이름 변경을 선택하셨습니다.]");
            System.out.print("새로운 이름을 입력해 주세요: ");

            //TODO: 넥스트 라인 필요한지 확인
            String newName = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_NAME, CommonPatternError.STUDENT_NAME);
            //TODO: 기존 이름과 다른지, 중복된 이름인지 확인 후 저장
        } else if (menuNum.equals("2")) {
            System.out.println("[2. 전화번호 변경을 선택하셨습니다.]");
            System.out.print("새로운 전화번호를 입력해 주세요: ");

            //TODO: 넥스트 라인 필요한지 확인
            String newPhoneNum = ScannerUtils.scanWithPattern(CommonPattern.PHONE_NUMBER, CommonPatternError.PHONE_NUMBER);
            //TODO: 기존 번호와 다른지, 중복된 번호인지 확인 후 저장
        } else if (menuNum.equals("3")) {
            System.out.println("[3. 듣는 수업 목록 편집을 선택하셨습니다.]");
            System.out.println("[1. 수업 추가 2. 수업 삭제]");
            System.out.print("수행할 메뉴를 선택하세요 (* 1, 2 중 원하는 메뉴의 숫자 하나를 입력하세요 *): ");

            //TODO: 넥스트 라인 필요한지 확인
            String lectureMenuNum = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_DATE, CommonPatternError.LECTURE_DATE); // 임시 정규식
            if(lectureMenuNum.equals("1")) {
                System.out.println("1. 수업 추가를 선택하셨습니다.");
                //TODO: 수업 목록 리스트 보여주기
                System.out.print("추가하려는 수업의 코드를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");

                //TODO: 넥스트 라인 필요한지 확인
                String lectureID = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);
                //TODO: 기존 수업코드와 다른지, 중복된 번호인지 확인 후 저장
            } else if(lectureMenuNum.equals("2")) {
                System.out.println("2. 수업 삭제를 선택하셨습니다.");
                //TODO: 수업 목록 리스트 보여주기
                System.out.print("삭제하려는 수업의 코드를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");

                //TODO: 넥스트 라인 필요한지 확인
                String lectureID = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_CODE, CommonPatternError.LECTURE_CODE);
                //TODO: 기존 수업코드가 맞는지, 새로운 번호인지 확인 후 삭제
            }
        }
        else {
            System.out.println("[4. 나가기를 선택하셨습니다.]");
        }
    }


    //학생 삭제 함수
    public void deleteStudent(){
        System.out.println("[4.학생 정보 삭제를 선택하셨습니다.]");
        showStudentList();
        System.out.print("삭제하고 싶은 학생 ID를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");

        //TODO: 넥스트 라인 필요한지 확인
        String id = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_ID, CommonPatternError.STUDENT_ID);

        //TODO: 데이터 파일에 해당 학생이 있는지 확인
//        if(id in dataFile){
//            dataFile.delete(id)
//            System.out.println("해당 학생의 정보가 삭제되었습니다.");
//        }
//        else{
//            System.out.println("[오류 : 입력형식이 맞지 않거나 해당 아이디의 학생이 존재하지 않습니다.]");
//        }
    }


    //학생 관리 함수
    public void management_Student(){
        StudentManager sm = new StudentManager();
        StudentMenuHandler.handle(sm);
//        System.out.println(Main.manageMenu);
        System.out.println("탈출");

    }
}
