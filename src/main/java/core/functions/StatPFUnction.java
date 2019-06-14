package core.functions;

import core.utils.GradeUtils;

import java.util.Map;

public class StatPFUnction implements SingleResultFunction {

    Map<String,String> parameters;

    public StatPFUnction(Map<String,String> parameters){
        this.parameters = parameters;
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
