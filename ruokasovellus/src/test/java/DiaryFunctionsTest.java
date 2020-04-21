/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ruokasovellus.DiaryFunctions;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AnssiKetomäki
 */
public class DiaryFunctionsTest {
    DiaryFunctions diary;
    
    public DiaryFunctionsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.diary = new DiaryFunctions("21.04.2020");
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
    public void diaryExistsAfterCreation(){
        assertEquals("Päivämäärä: 21.04.2020, Yht kcal: 0, hh: 0g, prot: 0g, rasva: 0g", diary.toString("21.04.2020"));
    }
    @Test
    public void addMEalWorks(){
        diary.addMeal(500, 4, 50, 100);
        assertEquals("Päivämäärä: 21.04.2020, Yht kcal: 500, hh: 4g, prot: 50g, rasva: 100g", diary.toString("21.04.2020"));
    }
}
