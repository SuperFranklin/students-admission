package core.calculators;

import com.sun.istack.internal.NotNull;
import core.model.BasicComponent;
import core.model.ShuntingYardElement;
import core.utils.CharUtils;
import core.utils.Operator;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PostfixCalculator {

    public static float calculate(@NotNull List<ShuntingYardElement> elements){
        Deque<ShuntingYardElement> stack = new LinkedList<>();
        for(ShuntingYardElement element : elements) {
            if (CharUtils.isSign(element)) {
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
