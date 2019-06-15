package core.functions;

import core.utils.CharUtils;
import core.utils.FunctionUtils;
import core.utils.GradeUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountBFunction implements Function {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("[A-Z0-9_]*\\[[a-zA-Z,]*\\]");

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public CountBFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
    }

    @Override
    public String calculate() {

        if (FunctionUtils.numOfFunctions(expression) > 1) {
            Matcher matcher = FUNCTION_PATTERN.matcher(expression);
            matcher.find();
            int start = matcher.start();
            int end = matcher.end();
            String insideFunction = expression.substring(start,end-1);
            Function function = FunctionFactory.getFunction(insideFunction, parameters);
            String result = function.calculate();
            expression = expression.replaceAll(FUNCTION_PATTERN.pattern(), result);
        }
        String argument = getArgument();
        String[] subjects = argument.split(",");
        long count = Arrays.stream(subjects)
                .map(this::grade)
                .map(GradeUtils::toNumber)
                .filter(grade -> grade >= 3)
                .count();

        return Long.toString(count);

    }

    private String grade(String arg){
        if(CharUtils.isGrade(arg)){
            return arg;
        }
        return parameters.get(arg);
    }

    private String getArgument() {
        String argument = expression.replaceAll("COUNT_B\\[","");
        argument = argument.replaceAll("\\]","");
        return argument;
    }

    private boolean hasFunctionInside(String argument) {
        return argument.contains("[");
    }
}
