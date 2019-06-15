package core.functions;

import core.utils.GradeUtils;

import java.util.HashMap;
import java.util.Map;

public class BestFunction implements SingleResultFunction {

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public BestFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
    }

    @Override
    public String calculate() {
        int best = 0;
        String methodParameters = expression.split("\\[")[1];
        String[] split = methodParameters.split(",");
        for(String s : split){
            String txtGrade = parameters.get(s);
            int grade = GradeUtils.toNumber(txtGrade);
            if(grade>best){
                best = grade;
            }

        }
        return Integer.toString(best);
    }
}
