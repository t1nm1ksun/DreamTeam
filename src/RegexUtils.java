import java.util.regex.Pattern;

public class RegexUtils {
    static boolean checkIsMatchesString(String pattern, String value){
        return Pattern.matches(pattern, value);
    }

    static boolean checkIsNumber(String text){
        return Pattern.matches("^[0-9]+$", text);
    }
}
