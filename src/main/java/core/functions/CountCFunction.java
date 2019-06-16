package core.functions;

import core.parsers.SubjectParser;
import core.utils.FunctionUtils;
import core.utils.GradeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountCFunction implements Function {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("[A-Z0-9_]*\\[[a-zA-Z,]*\\]");

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public CountCFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
        if(parameters.containsKey("LOCAL_PREF")){
            this.parameters.remove("LOCAL_PREF");
        }
    }


    /**
     * @return number of A,B,C grades
     */
    public String calculate() {
        if (FunctionUtils.numOfFunctions(expression) > 1) {
            //oblicza wewnętrzne funkcje i wyniki wstawia do wyrażenia
            expandExpression();
        }
        String argument = getArgument();
        String[] subjects = getSubjects(argument);
        long count = Arrays.stream(subjects)
                .map(subject -> parameters.get(subject))
                .map(GradeUtils::toNumber)
                .filter(grade -> grade >= 2)
                .count();

        return Long.toString(count);
    }

    private String[] getSubjects(String argument) {
        if(argument.equals("all")){
            return SubjectParser.subjects(parameters);
        }
        return argument.split(",");
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

    private String getArgument() {
        String argument = expression.replaceAll("COUNT_C\\[","");
        argument = argument.replaceAll("\\]","");
        return argument;
    }
}
