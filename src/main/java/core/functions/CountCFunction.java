package core.functions;

import core.utils.GradeUtils;

import java.util.Arrays;
import java.util.Map;

public class CountCFunction implements SingleResultFunction {

    private String expression;
    Map<String,String> parameters;

    public CountCFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters = parameters;
    }

    @Override
    public String calculate() {
        String argument = getArgument();
        String[] subjects = argument.split(",");
        long count = Arrays.stream(subjects)
                .map(subject -> parameters.get(subject))
                .map(GradeUtils::toNumber)
                .filter(grade -> grade >= 2)
                .count();

        return Long.toString(count);
    }

    private String getArgument() {
        String argument = expression.replaceAll("COUNT_C\\[","");
        argument = argument.replaceAll("\\]","");
        return argument;
    }
}
