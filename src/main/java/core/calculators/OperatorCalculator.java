package core.calculators;

import core.model.ShuntingYardElement;
import core.utils.CharUtils;
import core.utils.Operator;

public class OperatorCalculator {

    private static final float TRUE = 1.0f;
    private static final float FALSE = 0.0f;

    public static float calculate(ShuntingYardElement firstElement, ShuntingYardElement secondElement, ShuntingYardElement operator){
        String operatorExpression = operator.getExpression();
        if(Operator.AND.getExpression().equals(operatorExpression)){
            boolean first = CharUtils.toBolean(firstElement.getValue());
            boolean second = CharUtils.toBolean(secondElement.getValue());
            if(first && second){
                return TRUE;
            }else{
                return FALSE;
            }
        }
        if(Operator.GREATER_EQUAL.getExpression().equals(operatorExpression)){
            float first = firstElement.getValue();
            float second = secondElement.getValue();

            if(first >= second){
                return TRUE;
            }else{
                return FALSE;
            }
        }
        if(Operator.GREATER.getExpression().equals(operatorExpression)){
            float first = getValue(firstElement);
            float second = secondElement.getValue();

            if(first > second){
                return TRUE;
            }else{
                return FALSE;
            }
        }
        if(Operator.LESS_EQUAL.getExpression().equals(operatorExpression)){
            float first = firstElement.getValue();
            float second = secondElement.getValue();

            if(first <= second){
                return TRUE;
            }else{
                return FALSE;
            }
        }

        if(Operator.LESS.getExpression().equals(operatorExpression)){
            float first = firstElement.getValue();
            float second = secondElement.getValue();

            if(first < second){
                return TRUE;
            }else{
                return FALSE;
            }

        }
        if(Operator.OR.getExpression().equals(operatorExpression)){
            boolean first = CharUtils.toBolean(firstElement.getValue());
            boolean second = CharUtils.toBolean(secondElement.getValue());

            if(first || second){
                return 1.0f;
            }else{
                return 0.0f;
            }
        }
        if(Operator.EQUAL.getExpression().equals(operatorExpression)){
            float first = firstElement.getValue();
            float second = secondElement.getValue();

            if(first == second){
                return 1.0f;
            }else{
                return 0.0f;
            }
        }

        throw new IllegalArgumentException("Illegall operator");
    }

    private static Float getValue(ShuntingYardElement firstElement) {
        return firstElement.getValue();
    }
}
