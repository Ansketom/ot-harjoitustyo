package ruokasovellus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
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
    
    @Before
    public void setUp() throws SQLException {
        kanta.createTables();
    }
    
    @After
    public void tearDown() {
        kanta.dropTables();
    }

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
   
}
