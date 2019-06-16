import core.facade.StudentAdmissionCalculatorFacade;
import core.facade.StudentAdmissionCalculatorFacadeImpl;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StudentAdmissionCalculatorFacadeTests {

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

    @Test
    public void testWithParenthesisAndBestTrue(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","A");
        params.put("AM","B");
        params.put("GM","B");
        params.put("Ph","A");
        params.put("TS","50");
        params.put("Ch","E");
        params.put("Bi","C");
        params.put("Ec","B");
        params.put("Ge","A");
        params.put("Hi","E");
        params.put("Geo","C");
        params.put("Acc","B");
        params.put("BS","D");
        params.put("LS","E");
        params.put("ICT","3");

        String expression = "(((G[LL]>=B)|(G[AE]>B)&((G[GM]>=C)|(G[AM]>=B)))&(GPA[BEST_2[Ec,Ge,Hi,Ph,Ch,Bi,Geo,Acc,BS,LS,ICT]]>=2.5))";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }

    @Test
    public void testWithParenthesisAndBestFalse(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","C");
        params.put("AM","B");
        params.put("GM","B");
        params.put("Ph","A");
        params.put("TS","50");
        params.put("Ch","E");
        params.put("Bi","C");
        params.put("Ec","B");
        params.put("Ge","A");
        params.put("Hi","E");
        params.put("Geo","C");
        params.put("Acc","B");
        params.put("BS","D");
        params.put("LS","E");
        params.put("ICT","3");
        params.put("LOCAL_PREF","33");

        String expression = "(((G[LL]>=B)|(G[AE]>B)&((G[GM]>=C)|(G[AM]>=B)))&(GPA[BEST_2[Ec,Ge,Hi,Ph,Ch,Bi,Geo,Acc,BS,LS,ICT]]>=2.5))";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }
    @Test
    public void testWithDoubledParenthesis(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AM","B");
        params.put("Ph","A");
        params.put("TS","50");

        String expression = "((COUNT_B[LL,Ph,AM]>=3)) & STAT_P[TS] > 0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }
    @Test
    public void testWithWithoutParenthesis19(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","C");
        params.put("AM","B");
        params.put("GM","B");
        params.put("CH","A");
        params.put("TS","50");
        params.put("Ch","E");
        params.put("Bi","C");
        params.put("Ec","B");
        params.put("Gr","B");
        params.put("Ge","A");
        params.put("Hi","E");
        params.put("Geo","C");
        params.put("Acc","B");
        params.put("BS","D");
        params.put("LS","E");
        params.put("AC","D");
        params.put("GI","D");
        params.put("Ph","C");
        params.put("IT","B");
        params.put("AS","C");
        params.put("BC","D");
        params.put("ICT","3");
        params.put("LOCAL_PREF","33");

        String expression = "(G[WORST[all]]>=C)&(GPA[all]>=2.25)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(false, qualified);
    }

    @Test
    public void testWithWithoutParenthesis20(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","C");
        params.put("AM","B");
        params.put("GM","B");
        params.put("CH","A");
        params.put("TS","50");
        params.put("Ch","E");
        params.put("Bi","C");
        params.put("Ec","B");
        params.put("Gr","B");
        params.put("Ge","A");
        params.put("Hi","E");
        params.put("Geo","C");
        params.put("Acc","B");
        params.put("BS","D");
        params.put("LS","E");
        params.put("AC","D");
        params.put("GI","D");
        params.put("Ph","C");
        params.put("IT","B");
        params.put("AS","C");
        params.put("BC","D");
        params.put("ICT","3");
        params.put("LOCAL_PREF","33");

        String expression = "((G[GM]>=C)|(G[AM]>=C))&((G[AE]>=C)|(G[LL]>=C)) ";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }

    @Test
    public void testWithWithoutParenthesis21(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","2");
        params.put("AE","2");
        params.put("AM","0");
        params.put("GM","4");
        params.put("Ch","3");
        params.put("Ph","0");
        params.put("Bi","E");

        params.put("LOCAL_PREF","33");

        String expression = "(COUNT_C[LL,AE,Ch,Ph,Bi]>=3)&(COUNT_B[AM,GM]=1)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertEquals(true, qualified);
    }

    @Test
    public void testWithWithoutParenthesis22(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","1");
        params.put("AE","2");
        params.put("AM","0");
        params.put("GM","4");
        params.put("Ch","3");
        params.put("Ph","0");
        params.put("Bi","E");
        params.put("LOCAL_PREF","33");

        String expression = "(COUNT_C[LL,AE,Ch,Ph,Bi]>=3)&(COUNT_B[AM,GM]=1)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertFalse( qualified);
    }

    @Test
    public void testWithWithoutParenthesis23(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","4");
        params.put("AE","2");
        params.put("AM","0");
        params.put("GM","B");
        params.put("Ch","3");
        params.put("Ph","0");
        params.put("Bi","E");
        params.put("LOCAL_PREF","33");

        String expression = "(COUNT_C[LL,AE,Ch,Ph,Bi]>=3)&(COUNT_B[AM,GM]=1)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertTrue( qualified);
    }

    @Test
    public void testWithWithoutParenthesis24(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","4");
        params.put("AE","2");
        params.put("AM","0");
        params.put("GM","C");
        params.put("Ch","3");
        params.put("Ph","0");
        params.put("Bi","E");
        params.put("LOCAL_PREF","33");

        String expression = "(COUNT_C[LL,AE,Ch,Ph,Bi]>=3)&(COUNT_B[AM,GM]=1)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertFalse( qualified);
    }

    @Test
    public void testWithWithoutParenthesis25(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","B");
        params.put("AM","C");
        params.put("GM","C");
        params.put("Ch","C");
        params.put("Ph","C");
        params.put("Bi","D");

        String expression = "(G[WORST[all]]>=C)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertFalse( qualified);
    }

    @Test
    public void testWithWithoutParenthesis26(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("AE","B");
        params.put("AM","C");
        params.put("GM","C");
        params.put("Ch","C");
        params.put("Ph","C");
        params.put("Bi","C");

        String expression = "(G[WORST[all]]>=C)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertTrue( qualified);
    }

    @Test
    public void testWithWithoutParenthesis27(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","C");
        params.put("AE","C");
        params.put("AM","C");
        params.put("GM","B");

        String expression = "(GPA[ALL]>=2.25)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertTrue( qualified);
    }
    @Test
    public void testWithWithoutParenthesis28(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","D");
        params.put("AE","D");
        params.put("AM","C");
        params.put("GM","B");
        params.put("TS","2");

        String expression = "((G[BEST[LL,AE]]>=C)&((G[AM]>=C)|(G[GM]>=C)))&STAT_P[TS]>0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertFalse( qualified);
    }
    @Test
    public void testWithWithoutParenthesis29(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","D");
        params.put("AE","C");
        params.put("AM","C");
        params.put("GM","B");
        params.put("TS","1");

        String expression = "((G[BEST[LL,AE]]>=C)&((G[AM]>=C)|(G[GM]>=C)))&STAT_P[TS]>0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertTrue( qualified);
    }
    @Test
    public void testWithWithoutParenthesis30(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","E");
        params.put("AM","C");
        params.put("GM","B");
        params.put("Bi","B");
        params.put("Ch","B");
        params.put("Ph","B");
        params.put("TS","1");

        String expression = "(COUNT_D[LL,AE]=1)&((G[AM]>=D)|(G[GM]>=D))&(G[Bi]>=D)&(COUNT_D[Ch,Ph]>=1)&(STAT_P[TS]>0)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertFalse( qualified);
    }

    @Test
    public void testWithWithoutParenthesis31(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","D");
        params.put("AM","C");
        params.put("GM","B");
        params.put("Bi","B");
        params.put("Ch","B");
        params.put("Ph","B");
        params.put("TS","1");

        String expression = "(COUNT_D[LL,AE]=1)&((G[AM]>=D)|(G[GM]>=D))&(G[Bi]>=D)&(COUNT_D[Ch,Ph]>=1)&(STAT_P[TS]>0)";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertTrue( qualified);
    }

    @Test
    public void testWithWithoutParenthesis32(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","D");
        params.put("AM","B");
        params.put("GM","B");
        params.put("Bi","B");
        params.put("Ch","B");
        params.put("Ph","B");
        params.put("TS","1");

        String expression = "((COUNT_A[Ch,Ph,AM]>=1)&(COUNT_B[Ch,AM,Ph,LL]>=3))&STAT_P[TS]>0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertFalse( qualified);
    }

    @Test
    public void testWithWithoutParenthesis33(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","D");
        params.put("AM","B");
        params.put("GM","B");
        params.put("Bi","B");
        params.put("Ch","B");
        params.put("Ph","A");
        params.put("TS","1");

        String expression = "((COUNT_A[Ch,Ph,AM]>=1)&(COUNT_B[Ch,AM,Ph,LL]>=3))&STAT_P[TS]>0";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        boolean qualified = studentAdmissionCalculatorFacade.isQualified(expression, params);
        assertTrue( qualified);
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

    @Test
    public void testRanking8(){
        Map<String, String> params = new HashMap<>();
        params.put("LL","A");
        params.put("Gr","B");
        params.put("IT","C");
        params.put("GM","B");
        params.put("BS","C");
        params.put("AC","B");
        params.put("Ec","A");
        params.put("LOCAL_PREF","43");

        String expression = "W_AVG[1GPA[all],1LOCAL_PREF[]]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(23.0f, rankigPoints, 0.0001f);
    }

    @Test
    public void testRanking9(){
        Map<String, String> params = new HashMap<>();
        params.put("Ch","A");
        params.put("Gr","B");
        params.put("IT","C");
        params.put("GM","B");
        params.put("LOCAL_PREF","10");

        String expression = "W_AVG[5G[Ch],10GPA[all],10LOCAL_PREF[]]";
        StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
        float rankigPoints = studentAdmissionCalculatorFacade.calculateRankingPoints(expression, params);
        assertEquals(6.0f, rankigPoints, 0.0001f);
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //***********************************TESTY WYKONANIA WSZYSTKICH FUNKCJI*********************************************
    @Test
    public void testExecutionAllQualiffiedFunctions() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","C");
        params.put("AM","B");
        params.put("GM","B");
        params.put("Ac","C");
        params.put("Gc","C");
        params.put("Ag","C");
        params.put("Gl","C");
        params.put("CH","A");
        params.put("TS","50");
        params.put("Ch","E");
        params.put("Bi","C");
        params.put("Ec","B");
        params.put("Gr","B");
        params.put("Ge","A");
        params.put("Hi","E");
        params.put("Geo","C");
        params.put("Acc","B");
        params.put("BS","D");
        params.put("LS","E");
        params.put("AC","D");
        params.put("GI","D");
        params.put("Ph","C");
        params.put("ph","C");
        params.put("IT","B");
        params.put("AS","C");
        params.put("BC","D");
        params.put("ICT","3");
        params.put("LOCAL_PREF","33");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("all_qualified_functions.csv").getFile());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String function;
            int i=0;
            while ((function = br.readLine()) != null) {
                if(function.isEmpty()){
                    break;
                }
                //wypisuje wiersz, który aktulanie jest testowany
                System.out.println("testing row: " + i);
                StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
                studentAdmissionCalculatorFacade.isQualified(function,params);
                i++;
            }
        }
    }

    @Test
    public void testExecutionAllRankingFunctions() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("LL","E");
        params.put("AE","C");
        params.put("AM","B");
        params.put("GM","B");
        params.put("Ac","C");
        params.put("Gc","C");
        params.put("Ag","C");
        params.put("Gl","C");
        params.put("CH","A");
        params.put("TS","50");
        params.put("Ch","E");
        params.put("Bi","C");
        params.put("Ec","B");
        params.put("Gr","B");
        params.put("Ge","A");
        params.put("Hi","E");
        params.put("Geo","C");
        params.put("Acc","B");
        params.put("BS","D");
        params.put("LS","E");
        params.put("AC","D");
        params.put("GI","D");
        params.put("Ph","C");
        params.put("ph","C");
        params.put("IT","B");
        params.put("AS","C");
        params.put("BC","D");
        params.put("ICT","3");
        params.put("LOCAL_PREF","33");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("all_ranking_functions.csv").getFile());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String function;
            int i=0;
            while ((function = br.readLine()) != null) {
                if(function.isEmpty()){
                    break;
                }
                //wypisuje wiersz, który aktulanie jest testowany
                System.out.println("testing row: " + i);
                StudentAdmissionCalculatorFacade studentAdmissionCalculatorFacade = new StudentAdmissionCalculatorFacadeImpl();
                studentAdmissionCalculatorFacade.calculateRankingPoints(function,params);
                i++;
            }
        }
    }





}
