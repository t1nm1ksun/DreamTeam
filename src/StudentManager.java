import java.util.Scanner;

public class StudentManager {

    //학생 정보 리스트 출력 함수
    public static void studentList() {
        System.out.println("[학생 정보 리스트]");
        // 데이터 파일 접근해서 학생 정보 리스트 출력
    }

    //학생 등록 함수
    public static void addStudent() {
        System.out.println("[2.학생 등록을 선택하셨습니다.]");
        System.out.print("학생의 이름을 입력하세요 (*2~10글자의 한글을 입력하세요*) : ");
        // 이전 입력 버퍼를 비우기 위해 빈 라인을 읽습니다.
        Main.scanner.nextLine();
        String student_Name = Main.scanner.nextLine();
//                    System.out.println("올바르지 않은 입력입니다 (*2~10글자의 한글을 입력하세요*) : ");
        System.out.print("학생의 전화번호를 입력하세요 (*공백이나 '-'없이 11개의 숫자를 한번에 입력하세요*) : ");

        String student_Phone = Main.scanner.nextLine();
        System.out.println("[학생 등록이 완료되었습니다.]");
//                    System.out.println("[이미 등록되어 있는 정보입니다.]");
//                  System.out.println("학생의 이름을 입력하세요 (*2~10글자의 한글을 입력하세요*) : ");
//                    System.out.println("오류 : 잘못된 입력입니다. (*공백이나 '-'없이 11개의 숫자를 한번에 입력하세요*) : ");
    }

    //학생 편집 함수
    public static void editStudent(){
        System.out.println("[3.학생 정보 편집을 선택하셨습니다.]");
        System.out.print("편집하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
        Main.scanner.nextLine();
        String id_Edit = Main.scanner.nextLine();
//                    System.out.println("해당 학생의 정보가 삭제되었습니다.");
        System.out.println("[신민석 학생의 정보를 변경합니다.]");
        System.out.println("[1.이름 2.전화번호 3.듣는수업]");
        System.out.print("변경하고 싶은 학생 정보를 선택하세요 : ");
//                    scanner.nextLine();
        int studentdata_Edit = Main.scanner.nextInt();

        if (studentdata_Edit == 1){
            System.out.println("[1.이름변경을 선택하셨습니다.]");
            System.out.print("변경하려는 이름을 입력해 주세요 : ");
            Main.scanner.nextLine();
            String name_Edit = Main.scanner.nextLine();
        } else if (studentdata_Edit == 3) {
            System.out.println("[3.듣는 수업 편집을 선택하셨습니다.]");
            System.out.println("[1.수업 추가 2.수업 삭제]");
            System.out.print("편집할 수업메뉴를 선택하세요 : ");
            int studentdata_class_Edit = Main.scanner.nextInt();
            if(studentdata_class_Edit == 1){
                System.out.println("1.수업 추가를 선택하셨습니다.");
                System.out.println("[수업리스트]");
                System.out.print("수강하려는 수업의 코드를 입력하세요 (*공백없이 4자리 숫자를 입력하세요.*): ");
                int student_Addclass = Main.scanner.nextInt();
                if (student_Addclass<9999 && student_Addclass>0){
                    System.out.println("[수강내역에 수업이 등록되었습니다.]");
                }
                else{
                    System.out.println("[오류 : 없는 수업이거나 잘못된 입력입니다.]");
                    System.out.println("[수업리스트]");
                    System.out.print("수강하려는 수업의 코드를 입력하세요 (*공백없이 4자리 숫자를 입력하세요.*): ");
                    student_Addclass = Main.scanner.nextInt();
                }
            }
            else {
                System.out.println("[오류 : 잘못된 입력입니다. >> 1,2중 하나의 숫자를 선택해 주세요.]");
                System.out.println("[1.수업 추가 2.수업 삭제]");
                System.out.println("편집할 수업메뉴를 선택하세요 : ");
                studentdata_class_Edit = Main.scanner.nextInt();
            }

        }
        else {
            System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3중 하나의 숫자를 선택해 주세요.]");
            System.out.println("[1.이름 2.전화번호 3.듣는수업]");
            System.out.print("변경하고 싶은 학생 정보를 선택하세요 : ");
            studentdata_Edit = Main.scanner.nextInt();
        }
    }

    //학생 삭제 함수
    public static void deleteStudent(){
        System.out.println("[4.학생 정보 삭제를 선택하셨습니다.]");
        System.out.print("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
        Main.scanner.nextLine();
        String id_Delete = Main.scanner.nextLine();
//                    System.out.println("해당 학생의 정보가 삭제되었습니다.");
        System.out.println("[오류 : 입력형식이 맞지 않거나 해당 아이디의 학생이 존재하지 않습니다.]");
        System.out.println("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
    }

    //학생 관리 함수
    public static void management_Student(){
        System.out.println("[2. 학생 관리를 선택하셨습니다.]");
        System.out.println("[1.조회 2.등록 3.편집 4.삭제]");
        System.out.print("메뉴를 입력하세요 : ");
        Main.studentMenu = Main.scanner.nextInt();

        if(Main.studentMenu == 0) {
            //0눌러서 메인메뉴 가는 함수
            //            System.out.println("[사용자가 0을 입력하였습니다. 메인메뉴로 이동합니다.]");
            //            System.out.println("현재까지 입력한 정보는 기억되지 않습니다. 그래도 대메뉴로 이동하시겠습니까? [Y/N] : ");
        }
        if(Main.studentMenu == 1){
            studentList();
        }
        else if(Main.studentMenu == 2) {
            addStudent();
        }else if(Main.studentMenu == 3) {
            studentList();
            editStudent();
        }
        else if (Main.studentMenu == 4) {
            studentList();
            deleteStudent();
        }
        else if(Main.studentMenu == 5) {
            System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
        }
    }
}
