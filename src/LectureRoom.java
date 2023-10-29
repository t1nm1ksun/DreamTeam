import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LectureRoom {
    private String code;
    private String limit;

    public LectureRoom(String code,String limit) {
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
