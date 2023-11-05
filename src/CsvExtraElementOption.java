public class CsvExtraElementOption {
    boolean isExtraElementsRequired;
    String errorMessage;

    public CsvExtraElementOption(boolean isExtraElementsRequired, String errorMessage){
        this.isExtraElementsRequired = isExtraElementsRequired;
        this.errorMessage = errorMessage;
    }
}
