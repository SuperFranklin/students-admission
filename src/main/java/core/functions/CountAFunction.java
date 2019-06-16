package core.functions;

import core.utils.TextUtils;
import core.utils.FunctionUtils;
import core.utils.GradeUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountAFunction implements Function {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("[A-Z0-9_]*\\[[a-zA-Z,]*\\]");

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public CountAFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
    }

    /**
     * @return number of A grades
     */
    @Override
    public String calculate() {

        if (FunctionUtils.numOfFunctions(expression) > 1) {
            //oblicza wewnętrzne funkcje i wyniki wstawia do wyrażenia
            expandExpression();
        }
        String argument = getArgument();
        String[] subjects = argument.split(",");
        long count = Arrays.stream(subjects)
                .map(this::grade)
                .map(GradeUtils::toNumber)
                .filter(grade -> grade >= 4)
                .count();

        return Long.toString(count);

    }

    private void expandExpression() {
        Matcher matcher = FUNCTION_PATTERN.matcher(expression);
        matcher.find();
        int start = matcher.start();
        int end = matcher.end();
        String insideFunction = expression.substring(start,end-1);
        Function function = FunctionFactory.getFunction(insideFunction, parameters);
        String result = function.calculate();
        expression = expression.replaceAll(FUNCTION_PATTERN.pattern(), result);
    }

    private String grade(String arg){
        if(TextUtils.isGrade(arg)){
            return arg;
        }
        return parameters.get(arg);
    }

    private String getArgument() {
        String argument = expression.replaceAll("COUNT_A\\[","");
        argument = argument.replaceAll("\\]","");
        return argument;
    }

}
