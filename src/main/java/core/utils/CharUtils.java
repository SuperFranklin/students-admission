package core.utils;

import core.model.ShuntingYardElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharUtils {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9.]*$");

    public static boolean isOperational(char c){
        char[] signs = {'<','>','=','&','|'};
        for(char sign : signs){
            if(sign == c){
                return true;
            }
        }
        return false;
    }

    public static boolean isNumber(String s){
        if(s.isEmpty()){
            return false;
        }
        Matcher numberMatcher = NUMBER_PATTERN.matcher(s);

        return numberMatcher.matches();
    }

    public static boolean hasParenthesis(String txt){
        return txt.contains("(");
    }

    public static boolean isGrade(char c){
        String value = Character.toString(c);
        return isGrade(value);
    }

    public static boolean isSign(ShuntingYardElement element){
        String expression = element.getExpression();
        for(char c : expression.toCharArray()){
            if(!CharUtils.isOperational(c)){
                return false;
            }
        }

        return true;
    }

    public static boolean toBolean(float input){
        if(input == 1.0f){
            return true;
        }
        if(input == 0.0f){
            return false;
        }

        throw new IllegalArgumentException("Cannot create bool value from float: " + input);
    }

    public static boolean isGrade(String value){
        if(value.length()!=1){
            return false;
        }
        char c = value.charAt(0);
        char[] signs = {'A','B','C','D','E','0','1','2','3','4','a','b','c','c','e'};
        for(char sign : signs){
            if(sign == c){
                return true;
            }
        }
        return false;
    }
}
