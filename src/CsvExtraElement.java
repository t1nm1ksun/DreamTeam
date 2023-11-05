public class CsvExtraElement {
    boolean isExtraElementsRequired;
    String errorMessage;

    public CsvExtraElement(boolean isExtraElementsRequired, String errorMessage){
        this.isExtraElementsRequired = isExtraElementsRequired;
        this.errorMessage = errorMessage;
    }
}
