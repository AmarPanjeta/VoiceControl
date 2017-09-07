package speech;

import java.util.HashMap;
import java.util.Map;

public class StringToNumberUtils {
	
    private static final Map<String, Integer> DIGITS =
	        new HashMap<String, Integer>();

	    static {
	        DIGITS.put("zero", 0);
	        DIGITS.put("one", 1);
	        DIGITS.put("two", 2);
	        DIGITS.put("three", 3);
	        DIGITS.put("four", 4);
	        DIGITS.put("five", 5);
	        DIGITS.put("six", 6);
	        DIGITS.put("seven", 7);
	        DIGITS.put("eight", 8);
	        DIGITS.put("nine", 9);
	    }
	
    public static double parseNumber(String[] tokens) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < tokens.length; ++i) {
            if (tokens[i].equals("point"))
                sb.append(".");
            else
                sb.append(DIGITS.get(tokens[i]));
        }

        return Double.parseDouble(sb.toString());
    }

}
