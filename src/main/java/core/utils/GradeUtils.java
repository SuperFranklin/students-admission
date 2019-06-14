package core.utils;

public class GradeUtils {

    public static int toNumber(String grade){
        if("A".equals(grade)){
            return 4;
        }
        if("B".equals(grade)){
            return 3;
        }
        if("C".equals(grade)){
            return 2;
        }
        if("D".equals(grade)){
            return 1;
        }
        if("E".equals(grade)){
            return 0;
        }

        return Integer.valueOf(grade);
    }
}
