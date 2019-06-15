import core.calculators.ComponentCalculator;
import core.calculators.PostfixCalculator;
import core.converters.PostfixExpressionConverter;
import core.facade.StudentAdmissionCalculatorFacade;
import core.facade.StudentAdmissionCalculatorFacadeImpl;
import core.model.Component;
import core.model.ShuntingYardElement;
import core.parsers.ExpressionParser;
import core.converters.InfixExpressionConverter;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StudentAdmissionCalculatorFacadeTests {


    private Map<String, String> mockExampleMap(){
        Map<String, String> result = new HashMap<>();
        result.put("Ph","A");
        result.put("CC","C");
        result.put("LL","B");
        result.put("AE","C");
        result.put("AM","C");
        result.put("GM","A");
        result.put("Hi","C");
        result.put("AC","C");
        result.put("BS","B");
        result.put("Ec","C");
        result.put("TS","1");
        result.put("Gr","D");
        result.put("LS","E");

        return result;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    //*************************TEST PRZYJĘCIA NA STUDIA******************************************
    @Test
    public void testWithoutParenthesis1(){
        Map<String, String> params = new HashMap<>();
        params.put("Ph","A");
        params.put("CC","B");
        params.put("LL","C");
        params.put("AE","A");
        params.put("GM","A");
        String expression = "GPA[all]>2.5";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }

    @Test
    public void testWithoutParenthesis2(){
        Map<String, String> params = new HashMap<>();
        params.put("Ph","D");
        params.put("CC","B");
        params.put("LL","C");
        params.put("AE","D");
        params.put("GM","A");
        String expression = "GPA[all]>2.5";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }
    @Test
    public void testWithoutParenthesis3(){
        Map<String, String> params = new HashMap<>();
        params.put("Ph","C");
        params.put("CC","B");
        params.put("LL","B");
        params.put("AE","C");
        //GPA IS 2.5 should not qualified
        String expression = "GPA[all]>2.5";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }
    @Test
    public void testWithoutParenthesis4(){
        Map<String, String> params = new HashMap<>();
        params.put("Ph","C");
        params.put("CC","B");
        params.put("LL","B");
        params.put("AE","B");
        //GPA IS 2.75 should be qualified
        String expression = "GPA[all]>2.5";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }
    @Test
    public void testWithoutParenthesis5(){
        Map<String, String> params = new HashMap<>();
        params.put("Ph","C");
        params.put("CC","C");
        params.put("LL","B");
        params.put("AE","D");
        //GPA IS 2.0 should be qualified
        String expression = "GPA[all]>=2.0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }
    @Test
    public void testWithoutParenthesis6(){
        Map<String, String> params = new HashMap<>();
        params.put("Ph","C");
        params.put("CC","C");
        params.put("LL","B");
        params.put("AE","D");
        String expression = "G[WORST[all]]>=C";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }
    @Test
    public void testWithoutParenthesis7(){
        Map<String, String> params = new HashMap<>();
        params.put("Ph","C");
        params.put("CC","C");
        params.put("LL","B");
        params.put("AE","A");
        String expression = "G[WORST[all]]>=C";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }
    @Test
    public void testWithoutParenthesis8(){
        Map<String, String> params = new HashMap<>();
        params.put("AE","B");
        params.put("CC","C");
        params.put("LL","B");
        params.put("GM","D");
        params.put("Hi","C");
        params.put("Ec","B");
        params.put("AC","A");
        params.put("BS","B");
        params.put("AM","C");
        String expression = "GPA[all]>=1.76 & G[BEST[LL,AE]]>=C & G[BEST[AM,GM]]>=C & G[BEST[Hi,Ec,AC,BS]]>=D";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }

    @Test
    public void testWithParenthesis8(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","B");
        params.put("Ch","C");
        params.put("Bi","B");
        params.put("AM","C");
        params.put("AS","C");
        params.put("Ph","C");
        String expression = "(G[WORST[LL,AM,Ph,AS,Ch,Bi]]>=C)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }

    @Test
    public void testWithParenthesis9(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","B");
        params.put("Ch","C");
        params.put("Bi","D");
        params.put("AM","C");
        params.put("AS","C");
        params.put("Ph","C");
        String expression = "(G[WORST[LL,AM,Ph,AS,Ch,Bi]]>=C)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }

    @Test
    public void testWithParenthesis10(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","B");
        params.put("Ch","C");
        params.put("Bi","D");
        params.put("AM","C");
        params.put("ICT","C");
        params.put("Gr","A");
        params.put("Hi","C");
        params.put("BS","D");
        params.put("LS","C");
        params.put("AC","B");
        params.put("Ec","C");
        params.put("Ph","A");
        params.put("GM","E");
        params.put("TS","C");

        String expression = "((COUNT_B[LL,BEST[ICT,Gr,Hi,BS,LS,AC,Ec]]>=3))&(G[GM]>=B)|(G[AM]>=C)&STAT_P[TS]>0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }

    @Test
    public void testWithParenthesis11(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","B");
        params.put("Ch","C");
        params.put("Bi","D");
        params.put("AM","C");
        params.put("ICT","C");
        params.put("Gr","A");
        params.put("Hi","C");
        params.put("BS","D");
        params.put("LS","C");
        params.put("AC","B");
        params.put("Ec","C");
        params.put("Ph","A");
        params.put("GM","E");
        params.put("TS","C");

        String expression = "(((G[LL]>=B)&((COUNT_C[GM,Gr,Ec,BS,LS,Hi]>=4)|(G[AM]>=C))))&STAT_P[TS]>0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }

    @Test
    public void testWithParenthesis12(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","C");
        params.put("Ch","C");
        params.put("Bi","D");
        params.put("AM","C");
        params.put("ICT","C");
        params.put("Gr","A");
        params.put("Hi","C");
        params.put("BS","D");
        params.put("LS","C");
        params.put("AC","B");
        params.put("Ec","C");
        params.put("Ph","A");
        params.put("GM","E");
        params.put("TS","C");

        String expression = "(((G[LL]>=B)&((COUNT_C[GM,Gr,Ec,BS,LS,Hi]>=4)|(G[AM]>=C))))&STAT_P[TS]>0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }

    @Test
    public void testWithParenthesis13(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","A");
        params.put("AM","C");
        params.put("GM","D");
        params.put("Ph","E");
        params.put("TS","51");


        String expression = "((COUNT_B[LL,AE,AM,GM,Ph]>=3))&STAT_P[TS]>50";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }

    @Test
    public void testWithParenthesis14(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","A");
        params.put("AM","B");
        params.put("GM","D");
        params.put("Ph","E");
        params.put("TS","51");


        String expression = "((COUNT_B[LL,AE,AM,GM,Ph]>=3))&STAT_P[TS]>50";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }
    @Test
    public void testWithParenthesis15(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","A");
        params.put("AM","B");
        params.put("GM","D");
        params.put("Ph","E");
        params.put("TS","50");


        String expression = "((COUNT_B[LL,AE,AM,GM,Ph]>=3))&STAT_P[TS]>50";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }

    @Test
    public void testWithParenthesis16(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","A");
        params.put("AM","B");
        params.put("GM","D");
        params.put("Ph","E");
        params.put("TS","50");
        params.put("Ch","B");
        params.put("Bi","C");

        String expression = "(COUNT_D[LL,AE]=1)&((G[AM]>=D)|(G[GM]>=D))&(G[Bi]>=D)&(COUNT_D[Ch,Ph]>=1)&(STAT_P[TS]>0)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }

    @Test
    public void testWithParenthesis17(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","D");
        params.put("AM","B");
        params.put("GM","D");
        params.put("Ph","A");
        params.put("TS","50");
        params.put("Ch","B");
        params.put("Bi","C");

        String expression = "(COUNT_D[LL,AE]=1)&((G[AM]>=D)|(G[GM]>=D))&(G[Bi]>=D)&(COUNT_D[Ch,Ph]>=1)&(STAT_P[TS]>0)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }

    @Test
    public void testWithParenthesis18(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","D");
        params.put("AM","B");
        params.put("GM","D");
        params.put("Ph","A");
        params.put("TS","50");
        params.put("Ch","B");
        params.put("Bi","C");

        String expression = "(COUNT_D[LL,AE]=1)&((G[AM]>=D)|(G[GM]>=D))&(G[Bi]>=D)&(COUNT_D[Ch,Ph]>=1)&(STAT_P[TS]>0)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }








    /////////////////////////////////////////////////////////////////////////////////////////////
    //*************************TEST RANKINGU******************************************************


    @Test
    public void testRanking1(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","A");

        String expression = "GPA[all]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(4.0F, rankigPoints, 0.0001f);
    }
    @Test
    public void testRanking2(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","B");
        params.put("Hi","C");

        String expression = "GPA[all]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(3.0f, rankigPoints, 0.0001f);
    }

    @Test
    public void testRanking3(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","B");
        params.put("Hi","C");

        String expression = "W_AVG[4G[LL],1GPA[all]]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(3.8f, rankigPoints, 0.0001f);
    }
    @Test
    public void testRanking4(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","D");
        params.put("AE","B");
        params.put("Hi","B");

        String expression = "W_AVG[4G[LL],1GPA[all]]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(1.2f, rankigPoints, 0.0001f);
    }
    @Test
    public void testRanking5(){
        Map<String, String> params = new HashMap<>();
        params.put("LOCAL_PREF","63");

        String expression = "W_AVG[1LOCAL_PREF[]]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(63.0f, rankigPoints, 0.0001f);
    }
    @Test
    public void testRanking6(){
        Map<String, String> params = new HashMap<>();
        params.put("LOCAL_PREF","50");
        params.put("LL","A");
        params.put("AE","B");


        String expression = "W_AVG[10LOCAL_PREF[],1GPA[all]]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(45.727272, rankigPoints, 0.0001f);
    }

    @Test
    public void testRanking7(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("Gr","B");
        params.put("IT","C");
        params.put("GM","B");
        params.put("BS","C");
        params.put("AC","B");
        params.put("Ec","A");


        String expression = "W_AVG[3G[LL],3G[Gr],2G[IT],3G[GM],2G[BS],2G[AC],1G[Ec]]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(3.0f, rankigPoints, 0.0001f);
    }





}
