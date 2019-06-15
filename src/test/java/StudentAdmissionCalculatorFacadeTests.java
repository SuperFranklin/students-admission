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
        Assert.assertEquals(true, qualified);
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
        Assert.assertEquals(false, qualified);
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
        Assert.assertEquals(false, qualified);
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
        Assert.assertEquals(true, qualified);
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
        Assert.assertEquals(true, qualified);
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
        Assert.assertEquals(false, qualified);
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
        Assert.assertEquals(true, qualified);
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
        Assert.assertEquals(true, qualified);
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
        Assert.assertEquals(true, qualified);
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
        Assert.assertEquals(false, qualified);
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
        Assert.assertEquals(false, qualified);
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
        Assert.assertEquals(true, qualified);
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
        Assert.assertEquals(false, qualified);
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
        Assert.assertEquals(false, qualified);
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
        Assert.assertEquals(true, qualified);
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
        Assert.assertEquals(false, qualified);
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
        Assert.assertEquals(false, qualified);
    }







    /////////////////////////////////////////////////////////////////////////////////////////////
    //*************************TEST RANKINGU******************************************************
}
