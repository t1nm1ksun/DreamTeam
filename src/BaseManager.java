import java.util.List;


public interface BaseManager {
    String getCsvFilePath();

    List<String> getRegexList();

    CsvExtraElementOption getExtraElementOption();

    boolean checkIsCsvRowsRequired();
}
