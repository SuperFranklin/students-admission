package core.functions;

import core.parsers.SubjectParser;
import core.utils.GradeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CountCFunction implements Function {

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public CountCFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
        if(parameters.containsKey("LOCAL_PREF")){
            this.parameters.remove("LOCAL_PREF");
        }
    }

    @Override
    public String calculate() {
        String argument = getArgument();
        String[] subjects = getSubjects(argument);
        long count = Arrays.stream(subjects)
                .map(subject -> parameters.get(subject))
                .map(GradeUtils::toNumber)
                .filter(grade -> grade >= 2)
                .count();

        return Long.toString(count);
    }

    @NotNull
    private String[] getSubjects(String argument) {
        if(argument.equals("all")){
            Set<String> subjects = SubjectParser.subjects(parameters);
            return subjects.toArray(new String[subjects.size()]);
        }
        return argument.split(",");
    }

    private String getArgument() {
        String argument = expression.replaceAll("COUNT_C\\[","");
        argument = argument.replaceAll("\\]","");
        return argument;
    }
}
