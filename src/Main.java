import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //깃 확인용
        //마스터 브랜치 확인용
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("[1.수업 시간표 관리 2.학생 관리 3.종료]");
            System.out.println("-> 언제든 0을 누르면 현재 화면으로 돌아옵니다");
            System.out.print("메뉴를 입력하세요 : ");
            int choice = scanner.nextInt();

            if (choice == 1) { //수업 시간표 관리
                System.out.println("[1. 수업 시간표 관리를 선택하셨습니다.]");
                System.out.println("[1.조회 2.등록 3.편집 4.삭제]");
                System.out.print("메뉴를 입력하세요 : ");
                int classMenu = scanner.nextInt();

                if (classMenu == 1 || classMenu == 3 || classMenu == 4) {
                    System.out.println("[수업 정보 리스트]");
                    // 수업 정보 리스트 출력 로직을 추가하세요.
                } else if (classMenu == 2) {
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

                }
                else {
                    System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
                }
            } else if (choice == 2) {
                System.out.println("[2. 학생 관리를 선택하셨습니다.]");
                System.out.println("[1. 조회 2. 등록 3. 편집 4. 삭제]");
                System.out.print("메뉴를 입력하세요 : ");
                int student_Menu = scanner.nextInt();

//                if(student_Menu==0){
//                    //0눌러서 메인메뉴 가는건 나중에 함수로 만들어서 뺍시다
//                    System.out.println("[사용자가 0을 입력하였습니다. 메인메뉴로 이동합니다.]");
//                    System.out.println("현재까지 입력한 정보는 기억되지 않습니다. 그래도 대메뉴로 이동하시겠습니까? [Y/N] : ");
//                }
                if(student_Menu==2) {
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
                }else if(student_Menu==3){
                    System.out.println("[3.학생 정보 편집을 선택하셨습니다.]");
                    System.out.print("편집하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
                    scanner.nextLine();
                    String id_Edit = scanner.nextLine();
//                    System.out.println("해당 학생의 정보가 삭제되었습니다.");
                    System.out.println("[신민석 학생의 정보를 변경합니다.]");
                    System.out.println("[1.이름 2.전화번호 3.듣는수업]");
                    System.out.print("변경하고 싶은 학생 정보를 선택하세요 : ");
//                    scanner.nextLine();
                    int data_Edit = scanner.nextInt();
                    System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3중 하나의 숫자를 선택해 주세요.]");
                    System.out.println("[1.이름 2.전화번호 3.듣는수업]");
                    System.out.print("변경하고 싶은 학생 정보를 선택하세요 : ");
                    data_Edit = scanner.nextInt();
                    if (data_Edit==1){
                        System.out.println("[1.이름변경을 선택하셨습니다.]");
                        System.out.print("변경하려는 이름을 입력해 주세요 : ");
                        scanner.nextLine();
                        String name_Edit = scanner.nextLine();
                    }
                }
                else if (student_Menu==4) {
                    System.out.println("[4.학생 정보 삭제를 선택하셨습니다.]");
                    System.out.print("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
                    scanner.nextLine();
                    String id_Delete = scanner.nextLine();
//                    System.out.println("해당 학생의 정보가 삭제되었습니다.");
                    System.out.println("[오류 : 입력형식이 맞지 않거나 해당 아이디의 학생이 존재하지 않습니다.]");
                    System.out.println("삭제하고 싶은 학생 아이디를 입력하세요 (*공백없는 숫자로만 입력하세요*) : ");
                }
                else if(student_Menu==5){
                    System.out.println("[오류 : 잘못된 입력입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
                }

            } else if (choice == 3) {
                System.out.println("[프로그램을 종료합니다.]");
                break; // 무한 루프 종료
            } else {
                System.out.println("오류 : 잘못된 입력 입니다. (1,2,3중 하나의 숫자를 선택해 주세요.) : ");
            }
        }

        scanner.close(); // 프로그램 종료 시 스캐너 닫기
    }
}
