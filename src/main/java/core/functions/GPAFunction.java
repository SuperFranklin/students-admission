package core.functions;

import core.parsers.SubjectParser;
import core.utils.CharUtils;
import core.utils.FunctionUtils;
import core.utils.GradeUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GPAFunction implements Function {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("[A-Z0-9_]*\\[[a-zA-Z,]*\\]");

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public GPAFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
    }

    @Override
    public String calculate() {
        if("GPA[all]".equals(expression)){
            Set<String> subjects = SubjectParser.subjects(parameters);
            return gpa(subjects);
        }else{
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
            return gpa(Arrays.asList(subjects));
        }

    }

    private String getArgument() {
        String argument = expression.replaceAll("GPA\\[","");
        argument = argument.replaceAll("\\]","");
        return argument;
    }

    private String gpa(Collection<String> arguments){
        int amount = 0;
        for(String argument : arguments){
            if(CharUtils.isNumber(argument)){
                amount += Integer.valueOf(argument);
            }
            if(parameters.containsKey(argument)) {
                int grade = GradeUtils.toNumber(parameters.get(argument));
                amount += grade;
            }
        }

        return Float.toString((float) amount / (float) arguments.size());
    }
}
