package core.facade;

import java.util.Map;

public interface StudentAdmissionCalculatorFacade {
    boolean isQualified(String expression, Map<String,String> parameters);
    float calculateRankingPoints(String expression, Map<String,String> parameters);
}
