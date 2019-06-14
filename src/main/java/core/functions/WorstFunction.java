package core.functions;

import core.utils.GradeUtils;

import java.util.Map;

public class WorstFunction implements SingleResultFunction {
    private String expression;
    Map<String,String> parameters;

    public WorstFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters = parameters;
    }

    @Override
    public String calculate() {
        int worst = 4;
        String[] subjects;
        if(expression.equals("WORST[all")){
            subjects = parameters.keySet().toArray(new String[0]);
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
