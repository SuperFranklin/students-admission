package core.functions;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WAVGFunction implements Function {
    
    private static final Pattern NUMBER_AT_THE_BEGINNING = Pattern.compile("(?m)^(\\d+)");

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public WAVGFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
    }

    /**
     * @return weighted average
     */
    @Override
    public String calculate() {
        expression = expression.replaceAll("W_AVG\\[", "");
        expression = expression.substring(0, expression.length() - 1);
        String[] functionsExpressions = expression.split(",");
        List<Pair<Integer, Float>> weightedGrades = getWeightedGrades(functionsExpressions);
        return wavg(weightedGrades);
    }

    private List<Pair<Integer, Float>> getWeightedGrades(String[] functionsExpressions) {
        List<Pair<Integer, Float>> weightedGrades = new ArrayList<>();
        for(String functionExpression : functionsExpressions){
            Integer weight = getWeight(functionExpression);
            int numberOfDigits = String.valueOf(weight).length();
            //z wyrażenia funkcji musimy usunąć liczbę oznaczającą mnożnik!
            Float grade = getGrade(functionExpression.substring(numberOfDigits));
            weightedGrades.add(Pair.of(weight, grade));
        }
        return weightedGrades;
    }

    private Float getGrade(String functionExpression) {
        Function function = FunctionFactory.getFunction(functionExpression, parameters);
        return Float.valueOf(function.calculate());
    }

    private Integer getWeight(String functionExpression) {
        Matcher weightMatcher = NUMBER_AT_THE_BEGINNING.matcher(functionExpression);
        weightMatcher.find();
        int start = weightMatcher.start();
        int end = weightMatcher.end();
        String weightText = functionExpression.substring(start,end);
        return Integer.valueOf(weightText);
    }


    // key = weight, value = grade!
    private String wavg(List<Pair<Integer, Float>> waightedGrades){
        int gradesAmount = 0;
        int weightCount = 0;
        for(Pair<Integer,Float> entry : waightedGrades){
            int weight = entry.getKey();
            float grade = entry.getValue();
            gradesAmount+=grade*weight;
            weightCount+=weight;
        }

        return Float.toString((float) gradesAmount / (float) weightCount);
    }
}
