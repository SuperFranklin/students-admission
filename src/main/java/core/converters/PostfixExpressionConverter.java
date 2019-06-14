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
        return Arrays.stream(Operator.values()).
                map(operator -> operator.getExpression()).
                filter(o -> o.equals(element.getExpression())).
                count() == 1;
    }

    private static boolean isHigherPrecedence(Operator o1, Operator o2){
        return o1.getPrecedence() > o2.getPrecedence();
    }
}
