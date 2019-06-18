package core.functions;

import core.utils.GradeUtils;

import java.util.HashMap;
import java.util.Map;

public class StatPFUnction implements Function {

    Map<String,String> parameters = new HashMap<>();

    public StatPFUnction(Map<String,String> parameters){
        this.parameters.putAll(parameters);
    }

    /**
     * @return return TS parameter value
     */
    @Override
    public String calculate()
    {
        String textGrade = parameters.getOrDefault("TS","0");
        int numberGrade = GradeUtils.toNumber(textGrade);

        //returns number grade as String
        return Integer.toString(numberGrade);
    }
}
