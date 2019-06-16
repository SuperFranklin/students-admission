package core.functions;

import core.utils.GradeUtils;
import org.jetbrains.annotations.NotNull;

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

    /**
     * @return best grade from subjects
     */
    @Override
    public String calculate() {
        String[] split = getArguments();
        Integer best = Arrays.stream(split).
                map(parameters::get).
                map(GradeUtils::toNumber).
                max(Integer::compareTo).
                get();
        return Integer.toString(best);
    }

    @NotNull
    private String[] getArguments() {
        String methodParameters = expression.split("\\[")[1];
        return methodParameters.split(",");
    }
}
