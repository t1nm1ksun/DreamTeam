import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public  class Read {
    /**
     * csv 읽기 파일 List<List<String>> list = Read.readCsvFile(); for(List<String> item : list){ Lecture l1 = new
     * Lecture(item.get(0), item.get(1), item.get(2), item.get(3),item.get(4)); lectures.add(l1); } Lecture class에 있는
     * 것처럼 사용시에 순서대로 초기화됨 사용시 주의점: csv파일 생성 후 연결파일을 반드시 메모장으로!
     *
     * @return List<List < String>> 배열 item
     */
    public static List<List<String>> readCSV(String filePath) {
        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = Files.newBufferedReader(Paths.get(filePath));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {

                List<String> stringList = new ArrayList<>();
                String[] stringArray = line.split(",");

                stringList = Arrays.asList(stringArray);
                list.add(stringList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static boolean validateCSVRef(BaseManager fromManager, BaseManager toManager, String fromIndex, String toIndex){
        boolean hasMultiRef = fromIndex.startsWith("+");
        ArrayList<String> fromPks = new ArrayList<String>();
        ArrayList<String> toPks = new ArrayList<String>();
        List<List<String>> fromList = readCSV(fromManager.getCsvFilePath());
        List<List<String>> toList = readCSV(toManager.getCsvFilePath());
        int toIndexInt = Integer.parseInt(toIndex);

        if(hasMultiRef){
            int fromIndexInt = Integer.parseInt(fromIndex.replace("+",""));
            for (List<String> row : fromList) {
                for (int i = fromIndexInt; i < row.size(); i++) {
                    fromPks.add(row.get(i));
                }
            }
        } else {
            int fromIndexInt = Integer.parseInt(fromIndex);
            for (List<String> row : fromList) {
                fromPks.add(row.get(fromIndexInt));
            }
        }

        for (List<String> row : toList) {
            toPks.add(row.get(toIndexInt));
        }


        for(String fromPk: fromPks){
            if(!toPks.contains(fromPk)) {
                ScannerUtils.print(fromManager.getCsvFilePath() + "파일에서 " + toManager.getCsvFilePath() + "파일을 참조하는데에 있어 " + fromPk + "값이 존재하지 않습니다.",true);
                return false;
            }
        }

        return true;
    }

    public static boolean validateCSVFormat(List<List<String>> list, List<String> regexList, String fileName){
        int itemCount = regexList.size();
        boolean hasExtraRegex = false;

        if(!regexList.stream().filter(regex -> regex.startsWith("+")).findFirst().isEmpty()){
            hasExtraRegex = true;
        }

        if(hasExtraRegex){
            String extraRegex = regexList.get(regexList.size() - 1).replace("+","");

            for(int i = 0; i < list.size(); i++){
                for(int j = 0; j < list.get(i).size(); j++){
                    boolean notExtraCondition = (j < itemCount - 1) && !RegexUtils.checkIsMatchesString(regexList.get(j), list.get(i).get(j));
                    boolean extraCondition = (j >= itemCount - 1) && !RegexUtils.checkIsMatchesString(extraRegex, list.get(i).get(j));
                    if(notExtraCondition || extraCondition){
                        if(notExtraCondition){
                            ScannerUtils.print(fileName + "파일의 " + (i + 1) + " 번 째 줄 / " + + (j + 1) + " 번 째 인자에 오류가 있습니다.",true);
                            ScannerUtils.print("만족해야 하는 정규표현식: " +  regexList.get(j) + " / 현재 인자: "+ list.get(i).get(j), true);
                        }
                        if(extraCondition){
                            ScannerUtils.print(fileName + "파일의 " + (i + 1) + " 번 째 줄 / " + + (j + 1) + " 번 째 인자에 오류가 있습니다.",true);
                            ScannerUtils.print("만족해야 하는 정규표현식: " +  extraRegex + " / 현재 인자: "+ list.get(i).get(j), true);
                        }
                        return false;
                    }
                }
            }
            return true;
        }

        if(!hasExtraRegex){
           for(int i = 0; i < list.size(); i++) {
               if(itemCount != list.get(i).size()){
                   ScannerUtils.print(fileName + "파일의 " + (i + 1) + " 번 째 줄의 인자수가 맞지 않습니다.",true);
                   ScannerUtils.print("필요한 인자의 수: " + itemCount + " / 현재 인자의 수: " + list.get(i).size(), true);
                   return false;
               }
               for(int j = 0; j < regexList.size(); j ++){
                   if(!RegexUtils.checkIsMatchesString(regexList.get(j), list.get(i).get(j))){
                       ScannerUtils.print(fileName + "파일의 " + (i + 1) + " 번 째 줄 / " + + (j + 1) + " 번 째 인자에 오류가 있습니다.",true);
                       ScannerUtils.print("만족해야 하는 정규표현식: " + regexList.get(j) + " / 현재 인자: "+ list.get(i).get(j), true);
                       return false;
                   }
               }
           }
           return true;
        }
        return false;
    }

    public static boolean validateCSVListFormat(List<BaseManager> managerList){
        boolean isValidated = true;

        for(BaseManager baseManager:managerList){
            isValidated = validateCSVFormat(readCSV(baseManager.getCsvFilePath()), baseManager.getRegexList(), baseManager.getCsvFilePath());
            if(!isValidated) break;
        }

        return isValidated;
    }


    /**
     * csv 쓰기 파일 String 배열을 받아서 넣기!
     *
     * @param dataList
     */
    public static void writeLectureCSV(List<String[]> dataList) {
        BufferedWriter bufferedwrite = null;
        String filePath = "src/lecture.csv";
        try {
            bufferedwrite = Files.newBufferedWriter(Paths.get(filePath));
            for (String[] data : dataList) {
                StringBuilder aData = new StringBuilder();
                for (int j = 0; j < data.length; j++) {
                    if (j != data.length - 1) {
                        aData.append(data[j]).append(",");
                    } else {
                        aData.append(data[j]);
                    }
                }
                bufferedwrite.write(aData.toString());
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

    public static void writeTimeTableCSV(List<String[]> dataList) {
        BufferedWriter bufferedwrite = null;
        String filePath = "src/timetable.csv";
        try {
            bufferedwrite = Files.newBufferedWriter(Paths.get(filePath));
            for (String[] data : dataList) {
                StringBuilder aData = new StringBuilder();
                for (int j = 0; j < data.length; j++) {
                    if (j != data.length - 1) {
                        aData.append(data[j]).append(",");
                    } else {
                        aData.append(data[j]);
                    }
                }
//                ScannerUtils.print(aData.toString(), true);
                bufferedwrite.write(aData.toString());
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

    public static void writeStudentCSV(List<String[]> dataList) {
        BufferedWriter bufferedwrite = null;
        String filePath = "src/student.csv";
        try {
            bufferedwrite = Files.newBufferedWriter(Paths.get(filePath));
            for (String[] data : dataList) {
                //                List<String> data = dataList.get(i);
                String aData = "";
                aData = data[0] + "," + data[1] + "," + data[2] + "," + data[3];
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

    public static void writeLectureRoomCSV(List<String[]> dataList) {
        BufferedWriter bufferedwrite = null;
        ScannerUtils.print(Integer.toString(dataList.size()), true);
        String filePath = "src/lecture-room.csv";
        try {
            bufferedwrite = Files.newBufferedWriter(Paths.get(filePath));
            for (String[] data : dataList) {
                //                List<String> data = dataList.get(i);
                String aData = "";
                aData = data[0] + "," + data[1] + "," + data[2];
//                ScannerUtils.print("저장 : " + data[0] + "," + data[1] + "," + data[2], true);
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
