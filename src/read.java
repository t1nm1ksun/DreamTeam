import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
public class read {

    public List<List<String>> readCsvFile() {
        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader bufferedReader = null;
        String filePath = "C:\\Users\\cjja0\\Desktop\\과제 모음집\\2-2\\전기프\\class.csv";
        try {
            bufferedReader = Files.newBufferedReader(Paths.get(filePath));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {

                List<String> stringList = new ArrayList<>();
                String stringArray[] = line.split(",");

                stringList = Arrays.asList(stringArray);
                list.add(stringList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void writeCSV(List<String[]> dataList) {
        BufferedWriter bufferedwrite = null;
        String filePath ="C:\\Users\\cjja0\\Desktop\\과제 모음집\\2-2\\전기프\\class.csv";
        try {
            bufferedwrite = Files.newBufferedWriter(Paths.get(filePath));
            for (int i = 0; i < dataList.size(); i++) {
                String[] data = dataList.get(i);
                String aData = "";
                aData = data[0] + "," + data[1] + "," + data[2];
                bufferedwrite.write(aData);
                bufferedwrite.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedwrite != null) {
                    bufferedwrite.flush();
                    bufferedwrite.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void addClass(){
        Scanner scanner = new Scanner(System.in);
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
}
