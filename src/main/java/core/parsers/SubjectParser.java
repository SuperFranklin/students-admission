package core.parsers;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubjectParser {

    /**
     * @param params
     * @return subjects from params Map
     */
    public static String[] subjects(Map<String,String> params){
        Set<String> keys = params.keySet();
        if(keys.contains("LOCAL_PREF")){
            keys.remove("LOCAL_PREF");
        }
        if(keys.contains("TS")){
            keys.remove("TS");
        }

        return keys.toArray(new String[keys.size()]);
    }
}
