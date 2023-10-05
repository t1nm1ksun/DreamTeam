import java.util.Scanner;

public class ScannerInstance {
    private static ScannerInstance instance;
    private final Scanner scanner;

    private ScannerInstance(){
        scanner = new Scanner(System.in);
    }

    public Scanner getScanner(){
        return scanner;
    }

    public static synchronized ScannerInstance getInstance() {
        if (instance == null) {
            instance = new ScannerInstance();
        }
        return instance;
    }

    public void dispose(){
        scanner.close();
    }
}
