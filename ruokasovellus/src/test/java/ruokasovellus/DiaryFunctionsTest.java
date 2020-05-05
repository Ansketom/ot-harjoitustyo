/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.SQLException;
import ruokasovellus.DiaryFunctions;
import ruokasovellus.Database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author AnssiKetomäki
 */
public class DiaryFunctionsTest {
    public Database kanta;
    public DiaryFunctions diary;
    public DateTimeFormatter date;
    
    public DiaryFunctionsTest() throws SQLException {
        kanta= new Database();
        diary = new DiaryFunctions(kanta);
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
        assertTrue(diary.addDate(kanta, "05.05.2020"));
        kanta.deleteDateFromDiary("05.05.2020");
    }
    @Test
    public void addMealMethodAddsMealToDatabase() {
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("puolukka", 560, 89, 5, 7);
        kanta.addPortion("aamupuuro");
        kanta.addDishContents(1, 1, 100);
        kanta.addDishContents(1, 2, 50);
        diary.addDate(kanta, "05.05.2020");
        assertTrue(diary.addMeal(kanta, "05.05.2020", "aamupuuro"));
        kanta.deletePortionPart("aamupuuro", "kaurahiutale");
        kanta.deletePortionPart("aamupuuro", "puolukka");
        kanta.deletePortion("aamupuuro");
        kanta.deleteIncredient("kaurahiutale");
        kanta.deleteIncredient("puolukka");
    }
    @Test
    public void diaryToStringWorksCorrectly() {
        kanta.addDateToDiary("05.05.2020");
        kanta.addDateToDiary("06.05.2020");
        kanta.updateDiary("05.05.2020", 20100, 1800, 1200, 900);
        kanta.updateDiary("06.05.2020", 20900, 2000, 1200, 900);
        assertEquals("05.05.2020: Kcal: 2010.0, hiilihyd.: 180.0g, proteiini: 120.0g, rasva: 90.0g, vesi: 0.0litraa\n06.05.2020: Kcal: 2090.0, hiilihyd.: 200.0g, proteiini: 120.0g, rasva: 90.0g, vesi: 0.0litraa\n", diary.diaryToString());
        kanta.deleteDateFromDiary("05.05.2020");
        kanta.deleteDateFromDiary("05.05.2020");
    }
    
}
