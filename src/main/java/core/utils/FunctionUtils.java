package core.utils;

import org.apache.commons.lang3.StringUtils;

public class FunctionUtils {
    public static int numOfFunctions(String expression) {
        return StringUtils.countMatches(expression, "[");
    }
}
