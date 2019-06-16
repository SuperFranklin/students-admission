package core.facade;

import core.calculators.ComponentCalculator;
import core.calculators.PostfixCalculator;
import core.converters.InfixExpressionConverter;
import core.converters.PostfixExpressionConverter;
import core.model.Component;
import core.model.ShuntingYardElement;
import core.parsers.ExpressionParser;
import core.utils.TextUtils;

import java.util.List;
import java.util.Map;

public class StudentAdmissionCalculatorFacadeImpl implements StudentAdmissionCalculatorFacade {
    /**
     * @param expression
     * @param parameters - Map of elements: subject, grade in format (A-E) or (4-0)
     * @return true if student is qualified, false otherwise
     */
    @Override
    public boolean isQualified(String expression, Map<String, String> parameters) {
        float qualified = calculateExpression(expression, parameters);
        //convert float value to boolean and return
        return TextUtils.toBolean(qualified);
    }

    /**
     * @param expression
     * @param parameters - Map of elements: subject, grade in format (A-E) or (4-0)
     * @return student points in the ranking
     */
    @Override
    public float calculateRankingPoints(String expression, Map<String, String> parameters) {
        float rankingPoints = calculateExpression(expression, parameters);
        return rankingPoints;
    }

    private float calculateExpression(String expression, Map<String, String> parameters) {
        ExpressionParser expressionParser = new ExpressionParser();
        //parse expression to components with childs
        List<Component> parse = expressionParser.parse(expression);
        //convert all components to infix notation - flattering the structure
        List<ShuntingYardElement> infixNotationComponents = InfixExpressionConverter.convert(parse);
        //convert components to postfix notation
        List<ShuntingYardElement> postfix = PostfixExpressionConverter.postfix(infixNotationComponents);
        //calculate value for non sign components
        List<ShuntingYardElement> calculated = ComponentCalculator.calculate(postfix, parameters);
        //calculate result as float
        return PostfixCalculator.calculate(calculated);
    }
}
