package core.model;

import core.utils.TextUtils;

public class BasicComponent implements ShuntingYardElement {
    private String expression;
    private Float value;

    public BasicComponent(String expression){
        if(expression.length()>0) {
            this.expression = expression;
        }else{
            throw new IllegalArgumentException("Expression cannot be empty!");
        }
    }

    public BasicComponent(ShuntingYardElement component){
        this.expression = component.getExpression();
        this.value = component.getValue();
    }

    public boolean isSign(){
        for(char c : expression.toCharArray()){
            if(!TextUtils.isOperational(c)){
                return false;
            }
        }

        return true;
    }

    public String getExpression() {
        return expression;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = Float.valueOf(value);
    }

    @Override
    public void setValue(float value) {
        this.value = value;
    }

    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("expression: " + expression);
        if(value!=null){
            b.append("value: " + value);
        }
        return b.toString();
    }


}
