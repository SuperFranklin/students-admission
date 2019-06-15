package core.converters;

import core.model.ShuntingYardElement;
import core.utils.Operator;

import java.util.*;


/**
 * Implementation of Dijkstra Shunting Yard Algorithm
 */
public class PostfixExpressionConverter {

    /**
     * @param components that implement ShuntingYardElement interface
     * @return elements in postfix notation (Reverse Polish notation)
     */
    public static List<ShuntingYardElement> postfix(List<ShuntingYardElement> components)
    {
        List<ShuntingYardElement> output = new ArrayList<>();
        Deque<ShuntingYardElement> stack  = new LinkedList<>();

        for (ShuntingYardElement token : components) {
            // operator and higher precedence
            if (isOperator(token)) {
                while(!stack.isEmpty() && isHigherPrecedence(token.getExpression(),stack.peek().getExpression()) ){
                    output.add(stack.pop());
                }
                stack.push(token);

                // left parenthesis
            } else if (token.getExpression().equals("(")) {
                stack.push(token);

                // right parenthesis
            } else if (token.getExpression().equals(")")) {
                while ( ! stack.peek().getExpression().equals("("))
                    output.add(stack.pop());
                stack.pop();

                // digit
            } else {
                output.add(token);
            }
        }

        while ( ! stack.isEmpty())
            output.add(stack.pop());

        return output;
    }

    private static boolean isOperator(ShuntingYardElement element){
        return isOperator(element.getExpression());
    }

    private static boolean isOperator(String expression){
        return Arrays.stream(Operator.values()).
                map(operator -> operator.getExpression()).
                filter(o -> o.equals(expression)).
                count() == 1;
    }

    private static boolean isHigherPrecedence(String expression1, String expression2){
        Operator o1 = Operator.fromExpression(expression1);

        if(isOperator(expression2)){
            Operator o2 = Operator.fromExpression(expression2);
            return o1.getPrecedence() > o2.getPrecedence();
        }else{
            return false;
        }
    }

    private static boolean isHigherPrecedence(Operator o1, Operator o2){
        return o1.getPrecedence() > o2.getPrecedence();
    }
}
