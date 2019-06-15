package core.calculators;

import core.functions.Function;
import core.functions.FunctionFactory;
import core.model.ShuntingYardElement;
import core.utils.CharUtils;
import core.utils.GradeUtils;

import java.util.List;
import java.util.Map;

public class ComponentCalculator {

    public static List<ShuntingYardElement> calculate(List<ShuntingYardElement> components, Map<String, String> parameters){
        for(ShuntingYardElement component : components){
            if(!CharUtils.isSign(component)){
                String expression = component.getExpression().replaceAll(" ","");
                calculatePureFunction(parameters, component, expression);
            }
        }

        return components;
    }

    private static void calculatePureFunction(Map<String, String> parameters, ShuntingYardElement component, String expression) {
        Function function = FunctionFactory.getFunction(expression, parameters);
        if (function != null) {
            String calculate = function.calculate();
            //if more than one result
            if(!calculate.contains(",")) {
                component.setValue(calculate);
            }
        }

        if(CharUtils.isNumber(expression)){
            component.setValue(expression);
        }

        if (CharUtils.isGrade(expression)) {
            int grade = GradeUtils.toNumber(expression);
            component.setValue(Integer.toString(grade));
        }
    }
}
