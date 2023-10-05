import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
public class Read {

    /**
     *  csv 읽기 파일
     *    List<List<String>> list = Read.readCsvFile();
     *         for(List<String> item : list){
     *             Lecture l1 = new Lecture(item.get(0), item.get(1), item.get(2), item.get(3),item.get(4));
     *             lectures.add(l1);
     *         }
     * Lecture class 에 있는것처럼 사용시에 순서대로 초기화됨
     * 사용시 주의점:csv파일 생성후 연결파일을 반드시 메모장으로!
     * @return List<List<String>> 배열 item
     */
    public List<List<String>> readCsvFile( String filePath) {
        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader bufferedReader = null;

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

    /**
     * csv 쓰기 파일
     *String 배열을 받아서 넣기!
     * @param dataList
     */
    public void writeLectureCSV(List<String[]> dataList) {
        BufferedWriter bufferedwrite = null;
        String filePath ="src/class.csv";
        try {
            bufferedwrite = Files.newBufferedWriter(Paths.get(filePath));
            for(int i = 0; i<dataList.size();i++) {
                String[] data = dataList.get(i);
                String aData = "";
                aData = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4];
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

}
