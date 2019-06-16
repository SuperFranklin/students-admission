package core.functions;

import core.utils.TextUtils;
import core.utils.FunctionUtils;
import core.utils.GradeUtils;

import java.util.HashMap;
import java.util.Map;

public class GFunction implements Function {

    private String expression;
    Map<String,String> parameters = new HashMap<>();

    public GFunction(String expression, Map<String,String> parameters){
        this.expression = expression;
        this.parameters.putAll(parameters);
    }


    /**
     * @return grade of argument
     */
    public String calculate() {
        int numberOfFunctions = FunctionUtils.numOfFunctions(expression);
        if(numberOfFunctions==1) {
            String argument = parseFunctionArgument();
            int grade = getGrade(argument);
            //zwraca numeryczną wartość oceny jako String!
            return String.valueOf(grade);
        }else {
            //jeżeli są 2 funkcje, oblicz wartość funkcji wewnętrznej
            Function function = getInternalFunction();
            return function.calculate();
        }
    }

    private Function getInternalFunction() {
        String insideFunctionExp = expression.substring(2, expression.length()).split("]")[0];
        return FunctionFactory.getFunction(insideFunctionExp, parameters);
    }

    private int getGrade(String argument) {
        boolean isGrade = TextUtils.isGrade(argument);
        int grade = 0;
        if(isGrade){
            //argumentem funkcji jest ocena
            grade = GradeUtils.toNumber(argument);
        }else{
            //argumentem funkcji jest przedmiot
            grade = GradeUtils.toNumber(parameters.get(argument));
        }
        return grade;
    }


    private String parseFunctionArgument() {
        String argument = expression.replaceAll("G\\[", "");
        argument = argument.replaceAll("]","");
        return argument;
    }
}
