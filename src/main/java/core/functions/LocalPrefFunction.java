package core.functions;

import core.utils.CharUtils;
import core.utils.FunctionUtils;
import core.utils.GradeUtils;

import java.util.Map;

public class LocalPrefFunction implements SingleResultFunction {

    Map<String,String> parameters;

    public LocalPrefFunction(Map<String,String> parameters){
        this.parameters = parameters;
    }

    @Override
    public String calculate() {
        return parameters.get("LOCAL_PREF");
    }

}
