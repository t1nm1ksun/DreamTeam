import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.xml.transform.stream.StreamSource;

public class Students {
    protected String name;
    protected String phoneNumber;

    protected  Students() {
        this.name = "신민석";
        this.phoneNumber = "01012345678";
    }

    public  void showStudentsList() {
        //show!
        System.out.println("학생 리스트 출력!");
    }
    public String getName() {
        return this.name;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
