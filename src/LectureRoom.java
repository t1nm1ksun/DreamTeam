public class LectureRoom {

    private final String code;
    private final String limit;

    public LectureRoom(String code, String limit) {
        this.code = code;
        this.limit = limit;
    }

    public String getCode() {
        return code;
    }

    public String getLimit() {
        return limit;
    }
}
