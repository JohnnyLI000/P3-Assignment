/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import p3.assignment1.ContestGUI;

/**
 *
 * @author Yuan Hao Li
 */

public class projectTest {
    private ContestGUI contestGUI;
    public projectTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        contestGUI = new ContestGUI();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test 
    public void testPrizeCount(){
        System.out.println("prize count");
        int questionNo = 2 ;//suppose to get 100
        int exptResult = 100;
        int result = contestGUI.prizeCount(questionNo);
        assertTrue(exptResult == result);
        
}
}
