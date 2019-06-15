package core.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WAVGFunction implements SingleResultFunction {

    private String expression;
    Map<String,String> parameters;

    public WAVGFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters = parameters;
    }

    @Override
    //TODO refactoring
    public String calculate() {
        expression = expression.replaceAll("W_AVG\\[", "");
        expression = expression.substring(0, expression.length() - 1);
        String[] functions = expression.split(",");
        Map<Integer,Float> weightedGrades = new HashMap<>();
        for(String func : functions){
            char weightChar = func.charAt(0);
            Integer weight = Integer.valueOf(Character.toString(weightChar));
            SingleResultFunction function = FunctionFactory.getFunction(func.substring(1), parameters);
            Float grade = Float.valueOf(function.calculate());
            weightedGrades.put(weight, grade);
        }
        return "1";
    }


    //todo key = weight, value = grade!
    private String wavg(Map<Integer,Float> waightedGrades){
        int gradesAmount = 0;
        int weightCount = 0;
        for(Entry<Integer,Float> entry : waightedGrades.entrySet()){
            int weight = entry.getKey();
            float grade = entry.getValue();
            gradesAmount+=grade*weight;
            weightCount+=weight;
        }

        return Float.toString((float) gradesAmount / (float) weightCount);
    }
}
