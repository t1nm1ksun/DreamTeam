import java.util.Scanner;
//master branch
public class Main {

    //전역변수 선언 =================================================================
    public static int choice; //메인 메뉴
    public static int classMenu; //수업 관리 메뉴
    public static int studentMenu; //학생 관리 메뉴
    public static Scanner scanner = new Scanner(System.in); // 스캐너를 클래스 변수로 선언하고 한 번 열기


    //함수 선언 =====================================================================
    public static void test(){
        System.out.println("테스트");
    }

    //메인 메뉴 함수
    public static int menu(){

        System.out.println("[1.수업 시간표 관리 2.학생 관리 3.종료]");
        System.out.println("-> 언제든 0을 누르면 현재 화면으로 돌아옵니다");
        System.out.print("메뉴를 입력하세요 : ");
        choice = scanner.nextInt();

        return choice;
    }

    //수업 정보 리스트 출력 함수
    public static void classList(){
        System.out.println("[수업 정보 리스트]");
        // 데이터 파일 접근해서 수업 정보 리스트 출력
    }

    //수업 추가 함수
    public static void addClass(){

        System.out.println("[2.수업 등록을 선택하셨습니다.]");
        System.out.println("[1.수학(1) 2.수학(2) 3.영어(1) 4.영어(2)]");
        System.out.print("개설할 강의를 선택해주세요 : ");
        int class_Subject = scanner.nextInt();

        if (class_Subject >= 1 && class_Subject <= 4) {
            System.out.println("[1.선생님(1) 2.선생님(2) 3.선생님(3) 4.선생님(4)]");
            System.out.print("선생님을 선택해주세요 : ");
            int teacher = scanner.nextInt();
            // 선생님 선택에 따른 로직을 추가하세요.
        } else {
            System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
        }
        System.out.println("[1.월 수 금 2.화 목 토]");
        System.out.print("수업요일을 선택해주세요 : ");
        int day = scanner.nextInt();
        if(day!=1&&day!=2){
            System.out.println("오류 : 잘못된 입력입니다. >> 1,2중 하나의 숫자를 선택해 주세요.");
        }
        System.out.println("[1.14:00-16:00 2.16:00-18:00 3.18:00-20:00 4.20:00-22:00]");
        System.out.print("수업시간을 선택해주세요 : ");
        int class_Time= scanner.nextInt();
        if (class_Time <= 1 && class_Time >= 4){
            System.out.println("오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.");
        }
        System.out.println("수업 등록이 완료되었습니다.");
         // 스캐너를 닫아줌
        }

    //수업 편집 함수
    public static void editClass(){

            System.out.println("[3.수업 편집을 선택하셨습니다.]");
            System.out.println("[수업 리스트]");
            System.out.print("편집할 수업 코드를 입력해주세요 (*공백없이 숫자 4자리를 입력하세요*): ");
            int classcode_Edit = scanner.nextInt();

            if (classcode_Edit<9999 && classcode_Edit>0) {
                System.out.println("[1.수업 시간 변경]"); //아직은 하나만
                System.out.print("변경할 항목을 선택하세요: ");
                int classdata_Edit = scanner.nextInt();
                if(classdata_Edit==1){
                    System.out.println("[1.수업 시간 변경을 선택하셨습니다.]");
                    System.out.println("[1.월 수 금 2.화 목 토]");
                    System.out.print("수업요일을 선택해주세요 : ");
                    int day = scanner.nextInt();
                    if(day!=1&&day!=2){
                        System.out.println("오류 : 잘못된 입력입니다. >> 1,2중 하나의 숫자를 선택해 주세요.");
                    }
                    System.out.println("[1.14:00-16:00 2.16:00-18:00 3.18:00-20:00 4.20:00-22:00]");
                    System.out.print("수업시간을 선택해주세요 : ");
                    int class_Time= scanner.nextInt();
                    if (class_Time <= 1 && class_Time >= 4){
                        System.out.println("오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.");
                    }
                    System.out.println("수업 시간 변경이 완료되었습니다.");
                }
            } else {
                System.out.println("[오류 : 등록된 수업이 없거나 잘못된 입력입니다.]");
            }
             // 스캐너를 닫아줌
        }

    //수업 삭제 함수
    public static void deleteClass(){

        System.out.println("[4.수업 삭제를 선택하셨습니다.]");
        System.out.println("[수업리스트]");
        System.out.print("삭제할 수업 코드를 입력하세요 (*공백없이 숫자 4자리를 입력하세요*): ");
        int classcode_Delete = scanner.nextInt();
        if (classcode_Delete<9999 && classcode_Delete>0){
            System.out.println("수업 삭제가 완료되었습니다.");
        }
        else {
            System.out.println("[오류 : 등록된 수업이 없거나 잘못된 입력입니다.]");
            System.out.println("삭제할 수업 코드를 입력하세요 (*공백없이 숫자 4자리를 입력하세요*): ");
        }

    }

    //수업 시간표 관리 함수
    public static void management_Class(){

        System.out.println("[1. 수업 시간표 관리를 선택하셨습니다.]");
        System.out.println("[1.조회 2.등록 3.편집 4.삭제]");
        System.out.print("메뉴를 입력하세요 : ");
        classMenu = scanner.nextInt();
        if(classMenu==0){
            //0눌러서 메인메뉴 가는 함수
            //            System.out.println("[사용자가 0을 입력하였습니다. 메인메뉴로 이동합니다.]");
            //            System.out.println("현재까지 입력한 정보는 기억되지 않습니다. 그래도 대메뉴로 이동하시겠습니까? [Y/N] : ");
        }
        else if (classMenu == 1) {
            classList();
        } else if (classMenu == 2) {
            addClass();
        } else if (classMenu==3) {
            classList();
            editClass();
        } else if (classMenu==4) {
            classList();
            deleteClass();
        } else if (classMenu==0) {
            //메인 메뉴 실행문?? 아니면 루프 탈출문
        } else {
            System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
        }

    }

    //학생 정보 리스트 출력 함수
    public static void studentList(){
        System.out.println("[학생 정보 리스트]");
        // 데이터 파일 접근해서 학생 정보 리스트 출력
    }

    //학생 등록 함수
    public static void addStudent(){

        System.out.println("[2.학생 등록을 선택하셨습니다.]");
        System.out.print("학생의 이름을 입력하세요 (*2~10글자의 한글을 입력하세요*) : ");
        // 이전 입력 버퍼를 비우기 위해 빈 라인을 읽습니다.
        scanner.nextLine();
        String student_Name = scanner.nextLine();
//                    System.out.println("올바르지 않은 입력입니다 (*2~10글자의 한글을 입력하세요*) : ");
        System.out.print("학생의 전화번호를 입력하세요 (*공백이나 '-'없이 11개의 숫자를 한번에 입력하세요*) : ");

        String student_Phone = scanner.nextLine();
        System.out.println("[학생 등록이 완료되었습니다.]");
//                    System.out.println("[이미 등록되어 있는 정보입니다.]");
//                  System.out.println("학생의 이름을 입력하세요 (*2~10글자의 한글을 입력하세요*) : ");
//                    System.out.println("오류 : 잘못된 입력입니다. (*공백이나 '-'없이 11개의 숫자를 한번에 입력하세요*) : ");

    }

    //학생 편집 함수
    public static void editStudent(){

        System.out.println("[3.학생 정보 편집을 선택하셨습니다.]");
        System.out.print("편집하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
        scanner.nextLine();
        String id_Edit = scanner.nextLine();
//                    System.out.println("해당 학생의 정보가 삭제되었습니다.");
        System.out.println("[신민석 학생의 정보를 변경합니다.]");
        System.out.println("[1.이름 2.전화번호 3.듣는수업]");
        System.out.print("변경하고 싶은 학생 정보를 선택하세요 : ");
//                    scanner.nextLine();
        int studentdata_Edit = scanner.nextInt();

        if (studentdata_Edit==1){
            System.out.println("[1.이름변경을 선택하셨습니다.]");
            System.out.print("변경하려는 이름을 입력해 주세요 : ");
            scanner.nextLine();
            String name_Edit = scanner.nextLine();
        } else if (studentdata_Edit==3) {
            System.out.println("[3.듣는 수업 편집을 선택하셨습니다.]");
            System.out.println("[1.수업 추가 2.수업 삭제]");
            System.out.print("편집할 수업메뉴를 선택하세요 : ");
            int studentdata_class_Edit = scanner.nextInt();
            if(studentdata_class_Edit==1){
                System.out.println("1.수업 추가를 선택하셨습니다.");
                System.out.println("[수업리스트]");
                System.out.print("수강하려는 수업의 코드를 입력하세요 (*공백없이 4자리 숫자를 입력하세요.*): ");
                int student_Addclass=scanner.nextInt();
                if (student_Addclass<9999 && student_Addclass>0){
                    System.out.println("[수강내역에 수업이 등록되었습니다.]");
                }
                else{
                    System.out.println("[오류 : 없는 수업이거나 잘못된 입력입니다.]");
                    System.out.println("[수업리스트]");
                    System.out.print("수강하려는 수업의 코드를 입력하세요 (*공백없이 4자리 숫자를 입력하세요.*): ");
                    student_Addclass=scanner.nextInt();
                }
            }
            else {
                System.out.println("[오류 : 잘못된 입력입니다. >> 1,2중 하나의 숫자를 선택해 주세요.]");
                System.out.println("[1.수업 추가 2.수업 삭제]");
                System.out.println("편집할 수업메뉴를 선택하세요 : ");
                studentdata_class_Edit = scanner.nextInt();
            }

        }
        else {
            System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3중 하나의 숫자를 선택해 주세요.]");
            System.out.println("[1.이름 2.전화번호 3.듣는수업]");
            System.out.print("변경하고 싶은 학생 정보를 선택하세요 : ");
            studentdata_Edit = scanner.nextInt();
        }
         // 스캐너를 닫아줌
    }

    //학생 삭제 함수
    public static void deleteStudent(){

        System.out.println("[4.학생 정보 삭제를 선택하셨습니다.]");
        System.out.print("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
        scanner.nextLine();
        String id_Delete = scanner.nextLine();
//                    System.out.println("해당 학생의 정보가 삭제되었습니다.");
        System.out.println("[오류 : 입력형식이 맞지 않거나 해당 아이디의 학생이 존재하지 않습니다.]");
        System.out.println("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");

    }

    //학생 관리 함수
    public static void management_Student(){

        System.out.println("[2. 학생 관리를 선택하셨습니다.]");
        System.out.println("[1.조회 2.등록 3.편집 4.삭제]");
        System.out.print("메뉴를 입력하세요 : ");
        studentMenu = scanner.nextInt();

        if(studentMenu==0){
            //0눌러서 메인메뉴 가는 함수
            //            System.out.println("[사용자가 0을 입력하였습니다. 메인메뉴로 이동합니다.]");
            //            System.out.println("현재까지 입력한 정보는 기억되지 않습니다. 그래도 대메뉴로 이동하시겠습니까? [Y/N] : ");
        }
        if(studentMenu==1){
            studentList();
        }
        else if(studentMenu==2) {
            addStudent();
        }else if(studentMenu==3){
            studentList();
            editStudent();
        }
        else if (studentMenu==4) {
            studentList();
            deleteStudent();
        }
        else if(studentMenu==5){
            System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
        }

    }

    //=============================================================================
    public static void main(String[] args) {
        while (true) {
            menu(); //메인 메뉴
            if (choice == 1) { //수업 시간표 관리
                management_Class();
            } else if (choice == 2) {
                management_Student();
            } else if (choice == 3) {
                System.out.println("[프로그램을 종료합니다.]");
                break; // 무한 루프 종료
            } else {
                System.out.println("오류 : 잘못된 입력 입니다. (1,2,3중 하나의 숫자를 선택해 주세요.) : ");
            }
        }
        scanner.close();
    }
}
