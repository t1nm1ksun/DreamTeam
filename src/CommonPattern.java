public class CommonPattern {
    public static final String PHONE_NUMBER = "^010[0-9]{8}$"; // 전화번호 정규식
    public static final String MAIN_MENU = "^[1-3]$"; // 메인 메뉴 정규식
    public static final String LECTURE_DATE = "^[1-2]$"; // 수업 요일 정규식
    public static final String LECTURE_TIME = "^[1-4]$"; //수업 시간 정규식
    public static final String LECTURE_MENU = "^[1-4]$"; // 수업 메뉴 정규식
    public static final String LECTURE_ID = "^20([0-1][0-9]|2[0-3])$"; // 수업코드 정규식
    public static final String TEACHER_ID = "^300[1-4]$"; // 선생님 ID 정규식
    public static final String STUDENT_ID = "^40[0-9]{2}$"; // 학생 ID 정규식
    public static final String STUDENT_NAME = "^[가-힣]{2,10}$"; // 학생 이름 정규식
}
