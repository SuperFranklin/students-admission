package core.parsers;

import core.model.Component;
import core.utils.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9.]*$");
    //private static final Pattern DIGIT_GRADE_PATTERN = Pattern.compile("[=><][0-9]");
    private static final Pattern LETTER_GRADE_PATTERN = Pattern.compile("[=><][ABCDE][()&|><=]");

    public List<Component> parse(String expression) {
        expression = expression.trim();
        boolean hasParenthesis = CharUtils.hasParenthesis(expression);
        if (!hasParenthesis) {
            return parseExpWithoutParenthesis(expression);
        } else {
            List<Component> components = parseExpWithParenthesis(expression);
            for (Component component : components) {
                joinChilds(component);
            }

            return components;
        }

    }

    private void joinChilds(Component component) {
        String currentExpression = component.getExpression();
        if (CharUtils.hasParenthesis(currentExpression) && currentExpression.length()>2) {
            ExpressionParser parser = new ExpressionParser();
            //currentExpression = currentExpression.substring(1, currentExpression.length()-1);
            List<Component> childs = parser.parseExpWithParenthesis(currentExpression);
            for(Component child : childs){
                String childExpression = child.getExpression();
                joinChilds(child);

            }
            component.setChilds(childs);
        }else if(currentExpression.length()>3){
            List<Component> childs = parseExpWithoutParenthesis(currentExpression);
            if(childs.size()>1){
                component.setChilds(childs);
            }
        }

    }

    private List<Component> parseExpWithParenthesis(String expression) {
        List<Component> components = new ArrayList<>();
        String currentExpression = "";
        boolean insideParenthesis = false;
        //pozwala ocenić czy jesteśmy wewnątrz komponentu
        int balance = 0;
        for(int i=0; i<expression.length();i++){
            char c = expression.charAt(i);
            if(c=='('){
                balance++;
                if(!insideParenthesis) {
                    components.add(new Component(Character.toString(c)));
                }else {
                    currentExpression+=c;
                }
                insideParenthesis= true;
            }else if(c==')'){
                balance--;
                if(!insideParenthesis) {
                    components.add(new Component(Character.toString(c)));
                }else{
                    if(balance==0){
                        //mamy cały komponent wewnątrz nawiasów
                        components.add(new Component(currentExpression));
                        //czyścimi aktualnie składane wyrażenie
                        currentExpression = "";
                        components.add(new Component(Character.toString(c)));
                        insideParenthesis = false;
                    }else {
                        currentExpression+=c;
                    }
                }
            }else if(!insideParenthesis && CharUtils.isOperational(c)){
                if(!StringUtils.isWhitespace(currentExpression)){
                    components.add(new Component(currentExpression));
                    //czyścimi aktualnie składane wyrażenie
                    currentExpression = "";
                }
                char nextChar = expression.charAt(i+1);
                if(CharUtils.isOperational(nextChar)){
                    components.add(new Component(expression.substring(i,i+2)));
                    i++;
                }else if(CharUtils.isGrade(c)){
                    String result = "";
                    String analyzedExpression = expression.substring(i-1,i+4);
                    Matcher numberMatcher = NUMBER_PATTERN.matcher(analyzedExpression);
                    Matcher letterGradeMatcher = LETTER_GRADE_PATTERN.matcher(analyzedExpression);
                    //jeżeli jest liczbą
                    if(numberMatcher.matches()){
                        String group = numberMatcher.group();
                        //pierwszym znakiem jest (=,>,<)!
                        //ustaw number bez pierwszego znaku
                        result=group.substring(1);
                    }else if(letterGradeMatcher.matches()){
                        String group = letterGradeMatcher.group();
                        //ustaw number bez pierwszego znaku
                        result=group.substring(1);
                    }else if(expression.length()==i){
                        result = Character.toString(c);
                    }
                    components.add(new Component(result));
                    i+=result.length()-1;

                }
                else{
                    components.add(new Component(Character.toString(c)));
                }
            }else{
                currentExpression+=c;
            }
        }
        if(!currentExpression.isEmpty()) {
            components.add(new Component(currentExpression));
        }

        return components;
    }


    private List<Component> parseExpWithoutParenthesis(String expression){
        List<Component> components = new ArrayList<>();
        String currentExpression = "";
        for(int i=0; i<expression.length();i++){
            char c = expression.charAt(i);
            if(!CharUtils.isOperational(c)){
                currentExpression+=c;
            }else {
                components.add(new Component(currentExpression));
                currentExpression = "";
                char nextChar = expression.charAt(i+1);
                if(CharUtils.isOperational(nextChar)){
                    components.add(new Component(expression.substring(i,i+2)));
                    i++;
                }else{
                    components.add(new Component(Character.toString(c)));
                }
            }
        }
        if(!currentExpression.isEmpty()) {
            components.add(new Component(currentExpression));
        }

        return components;
    }

}
