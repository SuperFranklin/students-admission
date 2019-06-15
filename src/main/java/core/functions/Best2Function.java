package core.functions;

import core.utils.GradeUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Best2Function implements Function {
    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public Best2Function(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
    }

    @Override
    public String calculate() {

        String methodParameters = expression.split("\\[")[1];
        String[] split = methodParameters.split(",");
        List<Integer> descGrades = Arrays.stream(split).
                map(parameters::get).
                map(GradeUtils::toNumber).
                sorted(Integer::compareTo).
                collect(Collectors.toList());
        int length = descGrades.size();
        return descGrades.get(length - 1) + "," + descGrades.get(length -2);
    }
}
