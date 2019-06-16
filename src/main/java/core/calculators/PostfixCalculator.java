package core.calculators;

import core.model.BasicComponent;
import core.model.ShuntingYardElement;
import core.utils.TextUtils;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PostfixCalculator {

    /**
     * @param elements in postfix notation
     * @return shunting yard elements with calculated values
     */
    public static float calculate(List<ShuntingYardElement> elements){
        Deque<ShuntingYardElement> stack = new LinkedList<>();
        for(ShuntingYardElement element : elements) {
            if (TextUtils.isSign(element)) {
                ShuntingYardElement second = stack.pop();
                ShuntingYardElement first = stack.pop();
                float result = OperatorCalculator.calculate(first, second, element);
                ShuntingYardElement resultElement = new BasicComponent(Float.toString(result));
                resultElement.setValue(result);
                stack.push(resultElement);
            } else {
                stack.push(element);
            }
        }

        return stack.pop().getValue();
    }




}
