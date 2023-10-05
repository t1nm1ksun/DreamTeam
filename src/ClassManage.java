/*import java.util.Scanner;

public class ClassManage {
    Scanner scanner = new Scanner(System.in);

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
}
 */
