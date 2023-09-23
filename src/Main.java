import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("[1.수업 시간표 관리 2.학생 관리 3.종료]");
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
                    int subject = scanner.nextInt();

                    if (subject >= 1 && subject <= 4) {
                        System.out.println("[1.수학 선생님(1) 2.수학 선생님(2) 3.영어 선생님(1) 4.영어 선생님(2)]");
                        System.out.print("선생님을 선택해주세요 : ");
                        int teacher = scanner.nextInt();
                        // 선생님 선택에 따른 로직을 추가하세요.
                    } else {
                        System.out.println("[오류 : 잘못된 입력 입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
                    }
                } else {
                    System.out.println("[오류 : 잘못된 입력 입니다. >> 1,2,3,4중 하나의 숫자를 선택해 주세요.]");
                }
            } else if (choice == 2) {
                System.out.println("[2. 학생 관리를 선택하셨습니다.]");
                System.out.println("[1.조회 2.등록 3.편집 4.삭제]");
                System.out.print("메뉴를 입력하세요 : ");
                int student_Menu = scanner.nextInt();
                // 학생 관리 메뉴를 구현하세요.
                if(student_Menu==2){
                    System.out.println("[2.학생 등록을 선택하셨습니다.]");
                    System.out.print("학생의 이름을 입력하세요 (*2~10글자의 한글을 입력하세요*) : ");
                    // 이전 입력 버퍼를 비우기 위해 빈 라인을 읽습니다.
                    scanner.nextLine();
                    String student_Name = scanner.nextLine();
                    System.out.println("올바르지 않은 입력입니다 (*2~10글자의 한글을 입력하세요*) : ");
                }
            } else if (choice == 3) {
                System.out.println("[프로그램을 종료합니다.]");
                break; // 무한 루프 종료
            } else {
                System.out.println("[오류 : 잘못된 입력 입니다. >> 1,2,3중 하나의 숫자를 선택해 주세요.]");
            }
        }

        scanner.close(); // 프로그램 종료 시 스캐너 닫기
    }
}
