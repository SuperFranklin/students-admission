package core.functions;

import core.utils.GradeUtils;

import java.util.Collection;
import java.util.Map;

public class GPAFunction implements SingleResultFunction {

    private String expression;
    Map<String,String> parameters;

    public GPAFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters = parameters;
        if(parameters.containsKey("LOCAL_PREF")){
            parameters.remove("LOCAL_PREF");
        }
    }

    @Override
    public String calculate() {
        if("GPA[all]".equals(expression)){
            return gpa(parameters.keySet());
        }else{
            //TODO add implementation
        }

        return null;
    }

    private String gpa(Collection<String> subjects){
        int amount = 0;
        for(String subject : subjects){
            int grade = GradeUtils.toNumber(parameters.get(subject));
            amount += grade;
        }

        return Float.toString((float) amount / (float) subjects.size());
    }
}
