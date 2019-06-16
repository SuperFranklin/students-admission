package core.model;

import java.util.ArrayList;
import java.util.List;

public class Component implements ShuntingYardElement {
    private String expression;
    private Float value;
    private List<Component> childs = new ArrayList<>();

    public Component(String expression){
        if(expression.length()>0) {
            this.expression = expression;
        }else{
            throw new IllegalArgumentException("Expression cannot be empty!");
        }
    }
    public List<Component> getChilds() {
        return childs;
    }

    public void setChilds(List<Component> childs) {
        this.childs = childs;
    }

    public void addChild(Component component){
        this.childs.add(component);
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

    public boolean hasChilds(){
        return !childs.isEmpty();
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
