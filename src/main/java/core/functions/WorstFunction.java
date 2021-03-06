package core.functions;

import core.parsers.SubjectParser;
import core.utils.GradeUtils;

import java.util.HashMap;
import java.util.Map;

public class WorstFunction implements Function {
    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public WorstFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
        if(this.parameters.containsKey("LOCAL_PREF")){
            this.parameters.remove("LOCAL_PREF");
        }
    }

    /**
     * @return worst grade of argument subjects
     */
    @Override
    public String calculate() {
        int worst = 4;
        String[] subjects;
        if(expression.equals("WORST[all")){
            subjects = SubjectParser.subjects(parameters);
        }else {
            String methodParameters = expression.split("\\[")[1];
            subjects = methodParameters.split(",");
        }
        for(String s : subjects){
            String txtGrade = parameters.get(s);
            int grade = GradeUtils.toNumber(txtGrade);
            if(grade<worst){
                worst = grade;
            }
        }
        return Integer.toString(worst);
    }
}
