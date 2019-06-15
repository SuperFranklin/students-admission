package core.utils;

import jdk.nashorn.internal.runtime.options.Option;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum Operator {
    AND("&",2), OR("|",2), GREATER(">",1),
    LESS("<",1),GREATER_EQUAL(">=",1),
    LESS_EQUAL("<=",1),EQUAL("=",1);

    private final String expression;
    private final int precedence;

    Operator(String expression, int priority){
        this.expression = expression;
        this.precedence = priority;
    }

    public static Operator fromExpression(final String exp){
        final String pureExpression = exp.replaceAll(" ","");
        Optional<Operator> optional = Arrays.stream(Operator.values()).
                filter(o -> o.getExpression().equals(pureExpression)).findFirst();
        if(!optional.isPresent()){
            throw new NoSuchElementException("No such operator");
        }

        return optional.get();
    }

    public String getExpression(){
        return expression;
    }

    public int getPrecedence() { return precedence; }
}
