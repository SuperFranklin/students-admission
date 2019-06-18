package core.functions;

import java.util.HashMap;
import java.util.Map;

public class LocalPrefFunction implements Function {

    Map<String,String> parameters = new HashMap<>();

    public LocalPrefFunction(Map<String,String> parameters){
        this.parameters.putAll(parameters);
    }

    /**
     * @return value of LOCAL_PREF parameter
     */
    @Override
    public String calculate() {
        return parameters.getOrDefault("LOCAL_PREF","0");
    }

}
