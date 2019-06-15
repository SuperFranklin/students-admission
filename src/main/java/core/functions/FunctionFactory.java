package core.functions;

import java.util.Map;

public class FunctionFactory {
    public static SingleResultFunction getFunction(String expression, Map<String,String> parameters){
        String functionExp = getFunctionExpression(expression);

        if(functionExp.equals("GPA")){
            return new GPAFunction(expression, parameters);
        }
        if(functionExp.equals("BEST")){
            return new BestFunction(expression,parameters);
        }
        if(functionExp.equals("G")){
            return new GFunction(expression,parameters);
        }
        if(functionExp.equals("STAT_P")){
            return new StatPFUnction(parameters);
        }
        if(functionExp.equals("COUNT_C")){
            return new CountCFunction(expression,parameters);
        }
        if(functionExp.equals("COUNT_B")){
            return new CountBFunction(expression,parameters);
        }
        if(functionExp.equals("COUNT_D")){
            return new CountDFunction(expression,parameters);
        }
        if(functionExp.equals("WORST")){
            return new WorstFunction(expression,parameters);
        }
        if(functionExp.equals("W_AVG")){
            return new WAVGFunction(expression,parameters);
        }
        if(functionExp.equals("LOCAL_PREF")){
            return new LocalPrefFunction(parameters);
        }

        return null;
    }

    private static String getFunctionExpression(String expression) {
        String[] split = expression.split("\\[");
        return split[0];
    }
}
