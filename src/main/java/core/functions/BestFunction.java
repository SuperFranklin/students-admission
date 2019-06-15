package core.functions;

import core.utils.GradeUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestFunction implements Function {

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public BestFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
    }

    @Override
    public String calculate() {
        String methodParameters = expression.split("\\[")[1];
        String[] split = methodParameters.split(",");
        Integer best = Arrays.stream(split).
                map(parameters::get).
                map(GradeUtils::toNumber).
                max(Integer::compareTo).
                get();
        return Integer.toString(best);
    }
}
