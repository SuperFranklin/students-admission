package core.converters;

import core.model.BasicComponent;
import core.model.Component;
import core.model.ShuntingYardElement;

import java.util.ArrayList;
import java.util.List;

public class InfixExpressionConverter {

    public static List<ShuntingYardElement> convert(List<Component> input){
        List<ShuntingYardElement> result = new ArrayList<>();

        for(Component component : input){
            result.addAll(allChilds(component));
        }

        return result;
    }

    private static List<BasicComponent> allChilds(Component component){
        List<BasicComponent> result = new ArrayList<>();

        if(component.hasChilds()){
            for(Component child : component.getChilds()) {
                result.addAll(allChilds(child));
            }
        }else{
            result.add(new BasicComponent(component));
        }

        return result;
    }
}
