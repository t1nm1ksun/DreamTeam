public class CommonPattern {
    /**
     * 수업 코드 정규식
     */
    public static final String LECTURE_CODE = "^20([0-1][0-9]|2[0-3])$"; // 수업 코드 정규식
    /**
     * 선생님 ID 정규식
     */
    public static final String TEACHER_ID = "^300[1-4]$"; // 선생님 ID 정규식
    /**
     * 학생 ID 정규식
     */
    public static final String STUDENT_ID = "^40[0-9]{2}$"; // 학생 ID 정규식
    /**
     * 수업 요일 정규식
     */
    public static final String LECTURE_DATE = "^[1-6]$"; // 수업 요일 정규식
    /**
     * 수업 시간 정규식
     */
    public static final String LECTURE_TIME = "^[1-4]$"; // 수업 시간 정규식
    /**
     * 전화번호 정규식
     */
    public static final String PHONE_NUMBER = "^010[0-9]{8}$"; // 전화번호 정규식
    /**
     * 학생 이름 정규식
     */
    public static final String STUDENT_NAME = "^[가-힣]{2,10}$"; // 학생 이름 정규식
    /**
     * 보기 5개짜리 정규식
     */
    public static final String FIVE_CHOICE = "^[1-5]$"; // 보기 5개짜리 정규식
    /**
     * 보기 4개짜리 정규식
     */
    public static final String FOUR_CHOICE = "^[1-4]$"; // 보기 4개짜리 정규식
    /**
     * 보기 3개짜리 정규식
     */
    public static final String THREE_CHOICE = "^[1-3]$"; // 보기 3개짜리 정규식
    /**
     * 보기 2개짜리 정규식
     */
    public static final String TWO_CHOICE = "^[1-2]$"; // 보기 2개짜리 정규식
    /**
     * 타임테이블 코드 정규식
     */
    public static final String TIMETABLE_CODE = "^(600[0-9]|601[0-9]|60[2-9][0-9]|610[0-9]|611[0-9]|6120)$"; // 타임테이블 정규식

}
