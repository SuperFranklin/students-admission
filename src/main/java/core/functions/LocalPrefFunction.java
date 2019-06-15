package core.functions;

import core.utils.CharUtils;
import core.utils.FunctionUtils;
import core.utils.GradeUtils;

import java.util.HashMap;
import java.util.Map;

public class LocalPrefFunction implements SingleResultFunction {

    Map<String,String> parameters = new HashMap<>();

    public LocalPrefFunction(Map<String,String> parameters){
        this.parameters.putAll(parameters);
    }

    @Override
    public String calculate() {
        return parameters.get("LOCAL_PREF");
    }

}
