package core.utils;

public class GradeUtils {

    public static int toNumber(String grade){
        if("A".equalsIgnoreCase(grade)){
            return 4;
        }
        if("B".equalsIgnoreCase(grade)){
            return 3;
        }
        if("C".equalsIgnoreCase(grade)){
            return 2;
        }
        if("D".equalsIgnoreCase(grade)){
            return 1;
        }
        if("E".equalsIgnoreCase(grade)){
            return 0;
        }

        return Integer.valueOf(grade);
    }
}
