/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.SQLException;
import ruokasovellus.DiaryFunctions;
import ruokasovellus.DatabaseIncredients;
import ruokasovellus.DatabasePortions;
import ruokasovellus.DatabaseDiary;
import ruokasovellus.Database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 
 * 
 * @author AnssiKetomäki
 */
public class DiaryFunctionsTest {
    Database kanta;
    DiaryFunctions diary;
    DatabaseIncredients Dincr;
    DatabasePortions Dport;
    DatabaseDiary Ddiar;
    DateTimeFormatter date;
    
    
    public DiaryFunctionsTest() throws SQLException {
        kanta= new Database();
        Dincr = new DatabaseIncredients(kanta);
        Dport = new DatabasePortions(kanta, Dincr);
        Ddiar = new DatabaseDiary(kanta, Dport);
        diary = new DiaryFunctions(kanta, Dincr, Dport, Ddiar);
        
        date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        kanta.createTables();
    }
    
    @After
    public void tearDown() {
        kanta.dropTables();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void getDateGivesTodaysDate() {
        LocalDate now = LocalDate.now();
        String today = date.format(now);
        assertEquals(today, diary.getDate());
    }
    @Test
    public void addDateWorks() {
        assertTrue(diary.addDate("05.05.2020"));
        Ddiar.deleteDateFromDiary("05.05.2020");
    }
    @Test
    public void addMealMethodAddsMealToDatabase() {
        Dincr.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        Dincr.addIncredient("puolukka", 560, 89, 5, 7);
        Dport.addPortion("aamupuuro");
        Dport.addDishContents(1, 1, 100);
        Dport.addDishContents(1, 2, 50);
        diary.addDate("05.05.2020");
        assertTrue(diary.addMeal("05.05.2020", "aamupuuro"));
        Dport.deletePortionPart("aamupuuro", "kaurahiutale");
        Dport.deletePortionPart("aamupuuro", "puolukka");
        Dport.deletePortion("aamupuuro");
        Dincr.deleteIncredient("kaurahiutale");
        Dincr.deleteIncredient("puolukka");
    }
    @Test
    public void substractMealMethodsubstractsMealFromDatabase() {
        Dincr.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        Dincr.addIncredient("puolukka", 560, 89, 5, 7);
        Dport.addPortion("aamupuuro");
        Dport.addPortion("puolukat");
        Dport.addDishContents(1, 1, 100);
        Dport.addDishContents(1, 2, 50);
        Dport.addDishContents(2, 2, 75);
        diary.addDate("05.05.2020");
        
        assertTrue(diary.addMeal("05.05.2020", "aamupuuro"));
        assertTrue(diary.substractMeal("05.05.2020", "puolukat"));
        assertFalse(diary.substractMeal("05.05.2020", "aamupuuro"));
        
        Dport.deletePortionPart("aamupuuro", "kaurahiutale");
        Dport.deletePortionPart("aamupuuro", "puolukka");
        Dport.deletePortionPart("puolukat", "puolukka");
        Dport.deletePortion("aamupuuro");
        Dport.deletePortion("puolukat");
        Dincr.deleteIncredient("kaurahiutale");
        Dincr.deleteIncredient("puolukka");
    }
    @Test
    public void getWaterGetsTheAmountOfWater() {
        Ddiar.addDateToDiary("04.05.2020");
        assertEquals(0, diary.getWater("04.05.2020"));
    }
    @Test
    public void addWaterAddsTheAmountOfWater() {
        Ddiar.addDateToDiary("04.05.2020");
        assertEquals(0, diary.getWater("04.05.2020"));
        assertTrue(diary.addWater("04.05.2020", 25));
        assertEquals(25, diary.getWater("04.05.2020"));
        assertTrue(diary.addWater("04.05.2020", -5));
        assertTrue(diary.addWater("04.05.2020", -25));
        assertEquals(20, diary.getWater("04.05.2020"));
        Ddiar.deleteDateFromDiary("04.05.2020");
        }
    @Test
    public void diaryToStringWorksCorrectly() {
        Ddiar.addDateToDiary("05.05.2020");
        Ddiar.addDateToDiary("06.05.2020");
        Ddiar.updateDiary("05.05.2020", 20100, 1800, 1200, 900);
        Ddiar.updateDiary("06.05.2020", 20900, 2000, 1200, 900);
        assertEquals("05.05.2020: Kcal: 2010.0, hiilihyd.: 180.0g, proteiini: 120.0g, rasva: 90.0g, vesi: 0.0litraa\n06.05.2020: Kcal: 2090.0, hiilihyd.: 200.0g, proteiini: 120.0g, rasva: 90.0g, vesi: 0.0litraa\n", diary.diaryToString());
        Ddiar.deleteDateFromDiary("05.05.2020");
        Ddiar.deleteDateFromDiary("05.05.2020");
    }
    
}
