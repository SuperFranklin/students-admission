package core.model;

public interface ShuntingYardElement {
    String getExpression();
    Float getValue();
    void setValue(String value);
    void setValue(float value);
}
