public class StudentManager {

    /// 학생 정보 리스트 출력 함수
    public static void showStudentList() {
        System.out.println("[학생 정보 리스트]");
        //TODO: 데이터 파일 접근해서 학생 정보 리스트 출력
    }

    /// 학생 등록 함수
    public static void addStudent() {
        System.out.println("[2.학생 등록을 선택하셨습니다.]");
        System.out.print("학생의 이름을 입력하세요 (* 2~10자의 한글로 입력하세요 *): ");

        //TODO: 이름 정규식 추가되면 이름으로 수정하기

        // 이전 입력 버퍼를 비우기 위해 빈 라인을 읽습니다.
        String name = ScannerUtils.scanWithPattern(CommonPattern.PHONE, CommonPatternError.PHONE);


        System.out.print("학생의 전화번호를 입력하세요 (* 공백이나 '-'없이 11개의 숫자를 한 번에 입력하세요 *): ");

        String phoneNum = ScannerUtils.scanWithPattern(CommonPattern.PHONE, CommonPatternError.PHONE);
        
        //TODO: 등록되어 있는 학생인지 확인
        System.out.println("[학생 등록이 완료되었습니다.]");

    }

    /// 학생 편집 함수
    public static void editStudent() {
        System.out.println("[3.학생 정보 편집을 선택하셨습니다.]");
        System.out.print("편집하고 싶은 학생 아이디를 입력하세요 (*공백 없는 숫자로만 입력하세요*) : ");
        //TODO: 넥스트 라인 필요한지 확인, id 정규식 추가되면 id로 추가하기
        String id = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_ID, CommonPatternError.STUDENT_ID);

    }

    //학생 삭제 함수
    public static void deleteStudent(){
        System.out.println("[4.학생 정보 삭제를 선택하셨습니다.]");
        System.out.print("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
        String id = ScannerUtils.scanWithPattern(CommonPattern.STUDENT_ID, CommonPatternError.STUDENT_ID);
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
