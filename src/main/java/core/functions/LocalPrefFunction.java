package core.functions;

import java.util.HashMap;
import java.util.Map;

public class LocalPrefFunction implements Function {

    Map<String,String> parameters = new HashMap<>();

    public LocalPrefFunction(Map<String,String> parameters){
        this.parameters.putAll(parameters);
    }

    @Override
    public String calculate() {
        return parameters.get("LOCAL_PREF");
    }

}
