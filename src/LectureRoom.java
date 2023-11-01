public class LectureRoom {
    private String code;
    private String limit;

    private String count;

    public LectureRoom(String code, String limit, String count) {
        this.code = code;
        this.limit = limit;
        this.count = count;
    }

    public Integer getLeft() {
        ScannerUtils.print("limit : " + limit + " count : " + count, true);
        return Integer.parseInt(limit) - Integer.parseInt(count);
    }

    public boolean isFull() {
        return limit == count;
    }

    public void plusCount() {
        Integer tmp = Integer.parseInt(count) + 1;
        if (Integer.parseInt(limit) >= tmp) {
            this.count = tmp.toString();
        }
    }

    public void minusCount() {
        Integer tmp = Integer.parseInt(count) - 1;
        if (tmp > 0) {
            this.count = tmp.toString();
        }
    }

    public String getCount() {
        return count;
    }

    public String getCode() {
        return code;
    }

    public String getLimit() {
        return limit;
    }
}
