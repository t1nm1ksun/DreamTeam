import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Read {
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

    public static boolean validateCSVRef(BaseManager fromManager, BaseManager toManager, String fromIndex,
                                         String toIndex) {
        boolean hasMultiRef = fromIndex.startsWith("+");
        ArrayList<String> fromPks = new ArrayList<String>();
        ArrayList<String> toPks = new ArrayList<String>();
        List<List<String>> fromList = readCSV(fromManager.getCsvFilePath());
        List<List<String>> toList = readCSV(toManager.getCsvFilePath());
        int toIndexInt = Integer.parseInt(toIndex);

        if (hasMultiRef) {
            int fromIndexInt = Integer.parseInt(fromIndex.replace("+", ""));
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

        for (String fromPk : fromPks) {
            if (!toPks.contains(fromPk)) {
                ScannerUtils.print(
                        fromManager.getCsvFilePath() + "파일에서 " + toManager.getCsvFilePath() + "파일을 참조하는데에 있어 " + fromPk
                                + "값이 존재하지 않습니다.", true);
                return false;
            }
        }

        return true;
    }

    public static boolean validateCSVFormat(List<List<String>> list, List<String> regexList, String fileName,
                                            CsvExtraElementOption extraElementOption, boolean isCsvRowsRequired) {
        int itemCount = regexList.size();
        boolean hasExtraRegex = false;
        int extraElementStartIndex = itemCount - 1;
        List<String> pkList = new ArrayList<String>();

        if (!regexList.stream().filter(regex -> regex.startsWith("+")).findFirst().isEmpty()) {
            hasExtraRegex = true;
        }

        if (isCsvRowsRequired && list.size() == 0) {
            ScannerUtils.print(fileName + "파일은 적어도 한 줄의 데이터가 필요합니다.", true);
            return false;
        }

        if (hasExtraRegex) {
            String extraRegex = regexList.get(regexList.size() - 1).replace("+", "");

            for (int i = 0; i < list.size(); i++) {
                if (!extraElementOption.isExtraElementsRequired && list.get(i).size() < itemCount - 1) {
                    ScannerUtils.print(fileName + "파일의 " + (i + 1) + " 번째 줄의 인자수가 맞지 않습니다.", true);
                    ScannerUtils.print("필요한 인자의 수: " + (itemCount - 1) + " / 현재 인자의 수: " + list.get(i).size(), true);
                    return false;
                }

                if (extraElementOption.isExtraElementsRequired && list.get(i).size() < itemCount) {
                    if (list.get(i).size() < itemCount - 1) {
                        ScannerUtils.print(fileName + "파일의 " + (i + 1) + " 번째 줄의 인자수가 맞지 않습니다.", true);
                        ScannerUtils.print("필요한 인자의 수: " + itemCount + " / 현재 인자의 수: " + list.get(i).size(), true);
                        return false;
                    }

                    ScannerUtils.print(extraElementOption.errorMessage, true);
                    return false;
                }

                String pk = list.get(i).get(0);

                if (pkList.contains(pk)) {
                    ScannerUtils.print(fileName + "파일에 " + pk + " ID/코드가 중복 조회되고 있습니다.", true);
                    return false;
                } else {
                    pkList.add(pk);
                }

                for (int j = 0; j < list.get(i).size(); j++) {
                    boolean notExtraCondition =
                            (j < extraElementStartIndex) && !RegexUtils.checkIsMatchesString(regexList.get(j),
                                    list.get(i).get(j));
                    boolean extraCondition =
                            (j >= extraElementStartIndex) && !RegexUtils.checkIsMatchesString(extraRegex,
                                    list.get(i).get(j));
                    if (notExtraCondition || extraCondition) {
                        if (notExtraCondition) {
                            ScannerUtils.print(
                                    fileName + "파일의 " + (i + 1) + " 번째 줄 / " + +(j + 1) + " 번째 인자에 오류가 있습니다.", true);
                            ScannerUtils.print("만족해야 하는 정규표현식: " + regexList.get(j) + " / 현재 인자: " + list.get(i).get(j),
                                    true);
                        }
                        if (extraCondition) {
                            ScannerUtils.print(
                                    fileName + "파일의 " + (i + 1) + " 번째 줄 / " + +(j + 1) + " 번째 인자에 오류가 있습니다.", true);
                            ScannerUtils.print("만족해야 하는 정규표현식: " + extraRegex + " / 현재 인자: " + list.get(i).get(j),
                                    true);
                        }
                        return false;
                    }
                }
            }
            return true;
        }

        if (!hasExtraRegex) {
            for (int i = 0; i < list.size(); i++) {
                if (itemCount != list.get(i).size()) {
                    ScannerUtils.print(fileName + "파일의 " + (i + 1) + " 번째 줄의 인자수가 맞지 않습니다.", true);
                    ScannerUtils.print("필요한 인자의 수: " + itemCount + " / 현재 인자의 수: " + list.get(i).size(), true);
                    return false;
                }

                String pk = list.get(i).get(0);

                if (pkList.contains(pk)) {
                    ScannerUtils.print(fileName + "파일에 " + pk + " ID/코드가 중복 조회되고 있습니다.", true);
                    return false;
                } else {
                    pkList.add(pk);
                }

                for (int j = 0; j < regexList.size(); j++) {
                    if (!RegexUtils.checkIsMatchesString(regexList.get(j), list.get(i).get(j))) {
                        ScannerUtils.print(fileName + "파일의 " + (i + 1) + " 번째 줄 / " + +(j + 1) + " 번째 인자에 오류가 있습니다.",
                                true);
                        ScannerUtils.print("만족해야 하는 정규표현식: " + regexList.get(j) + " / 현재 인자: " + list.get(i).get(j),
                                true);
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean validateCSVListFormat(List<BaseManager> managerList) {
        boolean isValidated = true;

        for (BaseManager baseManager : managerList) {
            isValidated = validateCSVFormat(readCSV(baseManager.getCsvFilePath()), baseManager.getRegexList(),
                    baseManager.getCsvFilePath(), baseManager.getExtraElementOption(),
                    baseManager.checkIsCsvRowsRequired());
            if (!isValidated) {
                break;
            }
        }

        return isValidated;
    }

    public static boolean validatePhoneNumberDupliacated(List<BaseManager> managerList) {
        for (BaseManager manager : managerList) {
            List<String> phoneNumberList = new ArrayList<String>();
            List<List<String>> csv = readCSV(manager.getCsvFilePath());
            for (List<String> line : csv) {
                for (String item : line) {
                    if (item.startsWith("010")) {
                        if (phoneNumberList.contains(item)) {
                            ScannerUtils.print(manager.getCsvFilePath() + "파일에서 " + item + " 전화번호가 중복 조회되고 있습니다.",
                                    true);
                            return false;
                        }
                        phoneNumberList.add(item);
                    }
                }
            }
        }

        return true;
    }

    public static boolean validateTimetableIdDupliacated(List<BaseManager> managerList) {
        for (BaseManager manager : managerList) {
            List<List<String>> csv = readCSV(manager.getCsvFilePath());
            for (List<String> line : csv) {
                List<String> timeTableId = new ArrayList<String>();
                for (String item : line) {
                    if (item.startsWith("6") && item.length() == 4) {
                        if (timeTableId.contains(item)) {
                            ScannerUtils.print(manager.getCsvFilePath() + "파일에서 데이터 하나에 " + item + " 타임테이블 코드가 중복 조회되고 있습니다.",
                                    true);
                            return false;
                        }
                        timeTableId.add(item);
                    }
                }
            }
        }

        return true;
    }

    public static boolean validateTimetableIdDupliacatedInFile(List<BaseManager> managerList) {
        for (BaseManager manager : managerList) {
            List<List<String>> csv = readCSV(manager.getCsvFilePath());
            List<String> timeTableId = new ArrayList<String>();
            for (List<String> line : csv) {
                for (String item : line) {
                    if (item.startsWith("6") && item.length() == 4) {
                        if (timeTableId.contains(item)) {
                            ScannerUtils.print(manager.getCsvFilePath() + "파일에서 데이터 간 " + item + " 타임테이블 코드가 중복 조회되고 있습니다.",
                                    true);
                            return false;
                        }
                        timeTableId.add(item);
                    }
                }
            }
        }

        return true;
    }

    public static boolean validateSubjectIdDupliacated(List<BaseManager> managerList) {
        for (BaseManager manager : managerList) {
            List<List<String>> csv = readCSV(manager.getCsvFilePath());
            for (List<String> line : csv) {
                List<String> subjectId = new ArrayList<String>();
                for (String item : line) {
                    if (item.startsWith("1") && item.length() == 4) {
                        if (subjectId.contains(item)) {
                            ScannerUtils.print(manager.getCsvFilePath() + "파일에서 데이터 하나에 " + item + " 과목 코드가 중복 조회되고 있습니다.",
                                    true);
                            return false;
                        }
                        subjectId.add(item);
                    }
                }
            }
        }

        return true;
    }

    public static boolean validateStudentHasDuplicatedLecture(StudentManager studentManager, DivisionManager divisionManager){
        List<List<String>> studentCsv = readCSV(studentManager.getCsvFilePath());
        List<List<String>> divisionCsv = readCSV(divisionManager.getCsvFilePath());

        HashMap<String, String> DIVISION_TO_LECTURE_MAPPER = new HashMap<String, String>();


        for(List<String> division: divisionCsv){
            DIVISION_TO_LECTURE_MAPPER.put(division.get(0), division.get(1));
        }

        for(List<String> student: studentCsv){
            List<String> lectureCodeList = new ArrayList<>();
            for(int i = 3; i < student.size(); i++){
                String lectureCode = DIVISION_TO_LECTURE_MAPPER.get(student.get(i));
                if(lectureCodeList.contains(lectureCode)){
                    ScannerUtils.print(studentManager.getCsvFilePath() + "파일에서 ID: " + student.get(0) + " 이름: " + student.get(1) + "인 학생이 " + "수업 코드 " + lectureCode + "인 수업을 여러개 수강 중입니다." ,true);
                    return false;
                }
                lectureCodeList.add(lectureCode);
            }
        }
        return true;
    }

    public static boolean validateDivisionCodeDuplicated(List<BaseManager> managerList){
        for (BaseManager manager : managerList) {
            List<List<String>> csv = readCSV(manager.getCsvFilePath());
            for (List<String> line : csv) {
                List<String> divisionCode = new ArrayList<String>();
                for (String item : line) {
                    if (item.startsWith("7") && item.length() == 4) {
                        if (divisionCode.contains(item)) {
                            ScannerUtils.print(manager.getCsvFilePath() + "파일에서 데이터 하나에 " + item + " 분반 코드가 중복 조회되고 있습니다.",
                                    true);
                            return false;
                        }
                        divisionCode.add(item);
                    }
                }
            }
        }

        return true;
    }

    public static boolean validateTimetableInfoDuplicated(BaseManager manager) {
        List<List<String>> csv = readCSV(manager.getCsvFilePath());
        List<String> infoList = new ArrayList<String>();
        for (int i = 0; i < csv.size(); i++) {
            String info = csv.get(i).get(1) + csv.get(i).get(2) + csv.get(i).get(3);
            if (infoList.contains(info)) {
                int originalInfoIndex = infoList.indexOf(info);
                ScannerUtils.print(manager.getCsvFilePath() + "파일에서 " + (originalInfoIndex + 1) + "번째 줄과 " + (i + 1)
                        + "번 째 줄의 강의실, 요일, 시간 정보가 중복되고 있습니다.", true);
                return false;
            }
            infoList.add(info);
        }
        return true;
    }


    public static boolean validateDivisionHasOverThanRoomLimit(DivisionManager divisionManager, LectureRoomManager lectureRoomManager, TimeTableManager timeTableManager){
        List<List<String>> divisionCsv = readCSV(divisionManager.getCsvFilePath());

        for(List<String> row: divisionCsv){
            List<HashMap<String, Integer>> lectureroomLimitItemList = new ArrayList<>();
            int minLectureroomLimit = Integer.MAX_VALUE;
            HashMap<String, Integer> minLectureroomLimitItem = new HashMap<String, Integer>();
            for(int i = 4; i < row.size(); i++){
                lectureroomLimitItemList.add(getLectureroomLimitFromTimetableId(lectureRoomManager, timeTableManager, row.get(i)));
            }

            for(HashMap<String, Integer> item: lectureroomLimitItemList){
                if(item.get("lectureroomLimit") < minLectureroomLimit){
                    minLectureroomLimit = item.get("lectureroomLimit");
                    minLectureroomLimitItem.clear();
                    minLectureroomLimitItem = item;
                }
            }

            int divisionLimit = Integer.parseInt(row.get(3));

            if(minLectureroomLimitItem.get("lectureroomLimit") < divisionLimit){
                ScannerUtils.print(divisionManager.getCsvFilePath() + "파일에서 분반 코드 " + row.get(0) + "인 분반이 보유한 타임테이블 ID " + minLectureroomLimitItem.get("timetableId")+ "의 강의실 정원보다 해당 분반의 수업 정원이 더 큽니다.", true);
                ScannerUtils.print("분반 코드: " + row.get(0) + " / 분반 정원: " + row.get(3), true);
                ScannerUtils.print("타임테이블 ID " + minLectureroomLimitItem.get("timetableId") + "의 강의실 ID: " + minLectureroomLimitItem.get("lectureroomId") + " / 강의실 정원: " + minLectureroomLimitItem.get("lectureroomLimit"), true);
                ScannerUtils.print("분반의 수업 정원은 강의실 정원인 " + minLectureroomLimitItem.get("lectureroomLimit") + "보다 크지 않아야 합니다.", true);
                return false;
            }
        }

        return true;
    }

    private static HashMap<String, Integer> getLectureroomLimitFromTimetableId(LectureRoomManager lectureRoomManager, TimeTableManager timetableManager, String timetableId){
        List<List<String>> timetableCsv = readCSV(timetableManager.getCsvFilePath());
        List<List<String>> lectureroomCsv = readCSV(lectureRoomManager.getCsvFilePath());

        String lectureroomId = "";
        int lectureroomLimit = 0;

        for(List<String> timetable: timetableCsv){
            if(timetable.get(0).equals(timetableId)) lectureroomId = timetable.get(1);
        }

        for(List<String> lectureroom: lectureroomCsv){
            if(lectureroom.get(0).equals(lectureroomId)) lectureroomLimit = Integer.parseInt(lectureroom.get(1));
        }

        HashMap<String, Integer> checkedValue = new HashMap<String, Integer>();
        checkedValue.put("lectureroomId", Integer.parseInt(lectureroomId));
        checkedValue.put("lectureroomLimit", lectureroomLimit);
        checkedValue.put("timetableId", Integer.parseInt(timetableId));
        return checkedValue;
    }

    public static boolean validateStudentCountOverThanDivisionLimit(StudentManager studentManager, DivisionManager divisionManager){
        List<List<String>> divisionCsv = readCSV(divisionManager.getCsvFilePath());
        List<List<String>> studentCsv = readCSV(studentManager.getCsvFilePath());
        // MEMO: {divisionCode: {divisionLimit: int, studentCount: int}}
        HashMap<String, HashMap> DIVISION_INFO_MAPPER = new HashMap<>();

        for(List<String> division: divisionCsv){
            HashMap<String, Integer> divisionInfo = new HashMap<>();
            divisionInfo.put("divisionLimit", Integer.parseInt(division.get(3)));
            divisionInfo.put("studentCount", 0);
            DIVISION_INFO_MAPPER.put(division.get(0), divisionInfo);
        }

        for(List<String> student: studentCsv){
            for(int i = 3; i < student.size(); i++){
                HashMap<String, Integer> divisionInfo = DIVISION_INFO_MAPPER.get(student.get(i));
                Integer studentCount = divisionInfo.get("studentCount");
                divisionInfo.replace("studentCount", studentCount + 1);
                DIVISION_INFO_MAPPER.replace(student.get(i), divisionInfo);
            }
        }

        for(String divisionCode: DIVISION_INFO_MAPPER.keySet()){
            HashMap<String, Integer> divisionInfo = DIVISION_INFO_MAPPER.get(divisionCode);
            Integer divisionLimit = divisionInfo.get("divisionLimit");
            Integer studentCount = divisionInfo.get("studentCount");
            if(divisionLimit < studentCount){
                ScannerUtils.print("분반코드: "+divisionCode + "인 분반 수업을 듣는 학생이 분반 수업 정원보다 많습니다.", true);
                ScannerUtils.print("분반의 수업 정원: " + divisionLimit + " / 수강 학생 수: " + studentCount, true);
                return false;
            }
        }

        return true;
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

    public static void writeTeacherCSV(List<String[]> dataList) {
        BufferedWriter bufferedwrite = null;
        String filePath = "src/teacher.csv";
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

    public static void writeDivisionCSV(List<String[]> dataList) {
        BufferedWriter bufferedwrite = null;
        String filePath = "src/division.csv";
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
}
