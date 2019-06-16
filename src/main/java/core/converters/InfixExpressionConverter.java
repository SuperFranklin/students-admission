package core.converters;

import core.model.BasicComponent;
import core.model.Component;
import core.model.ShuntingYardElement;

import java.util.ArrayList;
import java.util.List;

public class InfixExpressionConverter {

    /**
     * @param components
     * @return list of Shunting yard flatted elements in infix notation. Elements doesn't have childs!
     */
    public static List<ShuntingYardElement> convert(List<Component> components){
        List<ShuntingYardElement> result = new ArrayList<>();

        for(Component component : components){
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
