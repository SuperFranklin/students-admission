package core.calculators;

import core.functions.Function;
import core.functions.FunctionFactory;
import core.model.ShuntingYardElement;
import core.utils.TextUtils;
import core.utils.GradeUtils;

import java.util.List;
import java.util.Map;

public class ComponentCalculator {

    public static List<ShuntingYardElement> calculate(List<ShuntingYardElement> components, Map<String, String> parameters){
        for(ShuntingYardElement component : components){
            if(!TextUtils.isSign(component)){
                String expression = component.getExpression().replaceAll(" ","");
                calculatePureFunction(parameters, component, expression);
            }
        }

        return components;
    }

    private static void calculatePureFunction(Map<String, String> parameters, ShuntingYardElement component, String expression) {
        Function function = FunctionFactory.getFunction(expression, parameters);
        if (function != null) {
            String result = function.calculate();
            if(!result.contains(",")) {
                //wynik jednoliczbowy
                component.setValue(result);
            }
        }

        if(TextUtils.isNumber(expression)){
            component.setValue(expression);
        }

        if (TextUtils.isGrade(expression)) {
            int grade = GradeUtils.toNumber(expression);
            component.setValue(Integer.toString(grade));
        }
    }
}
