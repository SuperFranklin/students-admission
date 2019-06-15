package core.functions;

import core.utils.GradeUtils;

import java.util.HashMap;
import java.util.Map;

public class StatPFUnction implements SingleResultFunction {

    Map<String,String> parameters = new HashMap<>();

    public StatPFUnction(Map<String,String> parameters){
        this.parameters.putAll(parameters);
    }

    @Override
    public String calculate()
    {
        String textGrade = parameters.get("TS");
        int numberGrade = GradeUtils.toNumber(textGrade);

        //returns number grade as String
        return Integer.toString(numberGrade);
    }
}
