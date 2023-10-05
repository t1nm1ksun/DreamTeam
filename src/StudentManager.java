import java.util.ArrayList;

public class StudentManager {

    private ArrayList<Student> studentList = new ArrayList<Student>();

    /// 학생 정보 리스트 출력 함수
    public static void showStudentList() {
        System.out.println("[학생 정보 리스트]");
        //TODO: 데이터 파일 접근해서 학생 정보 리스트 출력
    }

    /// 학생 등록 함수
    public static void addStudent() {
        System.out.println("[2. 학생 등록을 선택하셨습니다.]");
        System.out.print("학생의 이름을 입력하세요 (* 2~10자, 공백 없이 한글로만 입력하세요 *): ");

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
    public static void editStudent() {
        System.out.println("[3. 학생 정보 변경을 선택하셨습니다.]");
        System.out.print("변경하고 싶은 학생의 아이디를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *) : ");

        //TODO: 넥스트 라인 필요한지 확인
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
                System.out.println("[수업 목록 리스트]");
                System.out.print("추가하려는 수업의 코드를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");

                //TODO: 넥스트 라인 필요한지 확인
                String lectureID = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_ID, CommonPatternError.LECTURE_ID);
                //TODO: 기존 수업코드와 다른지, 중복된 번호인지 확인 후 저장
            } else if(lectureMenuNum.equals("2")) {
                System.out.println("2. 수업 삭제를 선택하셨습니다.");
                System.out.println("[수업 목록 리스트]");
                System.out.print("삭제하려는 수업의 코드를 입력하세요 (* 4자, 공백 없이 숫자로만 입력하세요 *): ");

                //TODO: 넥스트 라인 필요한지 확인
                String lectureID = ScannerUtils.scanWithPattern(CommonPattern.LECTURE_ID, CommonPatternError.LECTURE_ID);
                //TODO: 기존 수업코드가 맞는지, 새로운 번호인지 확인 후 삭제
            }
        }
        else {
            System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3중 하나의 숫자를 선택해 주세요.]");
            System.out.println("[1.이름 2.전화번호 3.듣는수업]");
            System.out.print("변경하고 싶은 학생 정보를 선택하세요 : ");
       //     studentdata_Edit = Main.scanner.nextInt();
        }
    }

    //학생 삭제 함수
    public static void deleteStudent(){
        System.out.println("[4.학생 정보 삭제를 선택하셨습니다.]");
        System.out.print("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
//        Main.scanner.nextLine();
//        String id_Delete = Main.scanner.nextLine();
        System.out.println("해당 학생의 정보가 삭제되었습니다.");
        System.out.println("[오류 : 입력형식이 맞지 않거나 해당 아이디의 학생이 존재하지 않습니다.]");
        System.out.println("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
    }

    //학생 관리 함수
    public static void management_Student(){
        System.out.println("[2. 학생 관리를 선택하셨습니다.]");
        System.out.println("[1.조회 2.등록 3.편집 4.삭제]");
        System.out.print("메뉴를 입력하세요 : ");
//        Main.studentMenu = Main.scanner.nextInt();

        if(Main.manageMenu == 0) {
            //0눌러서 메인메뉴 가는 함수
            //            System.out.println("[사용자가 0을 입력하였습니다. 메인메뉴로 이동합니다.]");
            //            System.out.println("현재까지 입력한 정보는 기억되지 않습니다. 그래도 대메뉴로 이동하시겠습니까? [Y/N] : ");
        }
        if(Main.manageMenu == 1){
//            studentList();
        }
        else if(Main.manageMenu == 2) {
            addStudent();
        }else if(Main.manageMenu == 3) {
//            studentList();
            editStudent();
        }
        else if (Main.manageMenu == 4) {
//            studentList();
            deleteStudent();
        }
        else {
            System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
        }
    }
}
