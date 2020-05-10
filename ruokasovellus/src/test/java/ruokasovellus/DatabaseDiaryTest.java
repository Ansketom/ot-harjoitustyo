package ruokasovellus;

import ruokasovellus.domain.DiaryFunctions;
import ruokasovellus.dao.Database;
import ruokasovellus.dao.DatabasePortions;
import ruokasovellus.dao.DatabaseDiary;
import ruokasovellus.dao.DatabaseIncredients;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AnssiKetomäki
 */
public class DatabaseDiaryTest {
    
    public Database kanta;
    public DatabaseIncredients Dincr;
    public DatabasePortions Dport;
    public DatabaseDiary Ddiar;
    public DiaryFunctions diary;
    public DateTimeFormatter date;
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    public DatabaseDiaryTest() throws SQLException {
        kanta = new Database();
        Dincr = new DatabaseIncredients(kanta);
        Dport = new DatabasePortions(kanta, Dincr);
        Ddiar = new DatabaseDiary(kanta, Dport);
        diary = new DiaryFunctions(kanta, Dincr, Dport, Ddiar);
        date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.setOut(new PrintStream(outContent));
    }

    @Before
    public void setUp() {
        kanta.createTables();
    }    
    
    @After
    public void tearDown() {
        kanta.dropTables();
    }

    @Test
    public void addingAndDeletingDateTODiaryWorks() throws SQLException {
        assertTrue(Ddiar.addDateToDiary("05.05.2020"));
        assertTrue(Ddiar.deleteDateFromDiary("05.05.2020"));
    }
    @Test
    public void updateDiaryAndGettingDiaryDataWorks() throws SQLException {
        
        Ddiar.addDateToDiary("05.05.2020");
        Ddiar.updateDiary("05.05.2020", 21000, 1800, 1200, 1000);
        ArrayList <String> dData = new ArrayList<>();
        dData.add("05.05.2020: Kcal: 2100.0, hiilihyd.: 180.0g, proteiini: 120.0g, rasva: 100.0g, vesi: 0.0litraa");
        assertEquals(dData, Ddiar.getDiaryData());
        dData.clear();
        Ddiar.updateDiary("05.05.2020", 25000, 2500, 1500, 1000);
        dData.add("05.05.2020: Kcal: 2500.0, hiilihyd.: 250.0g, proteiini: 150.0g, rasva: 100.0g, vesi: 0.0litraa");
        assertEquals(dData, Ddiar.getDiaryData());
        Ddiar.deleteDateFromDiary("05.05.2020");
    }
    @Test
    public void getDiaryDayDataReturnsData() throws SQLException {
        int [] testdata = new int [4];
        testdata [0] = 21000;
        testdata [1] = 1800;
        testdata [2] = 1200;
        testdata [3] = 1000;
        Ddiar.addDateToDiary("05.05.2020");
        Ddiar.updateDiary("05.05.2020", 21000, 1800, 1200, 1000);
        int [] daydata = Ddiar.getDiaryDayData("05.05.2020");
        assertEquals(testdata[0], daydata [0]);
        assertEquals(testdata[1], daydata [1]);
        assertEquals(testdata[2], daydata [2]);
        assertEquals(testdata[3], daydata [3]);
        Ddiar.deleteDateFromDiary("05.05.2020");
    }
    @Test
    public void updateDiaryWaterWorks() throws SQLException {
        Ddiar.addDateToDiary("06.05.2020");
        assertTrue(Ddiar.updateDiaryWater("06.05.2020", 43));
        Ddiar.deleteDateFromDiary("06.05.2020");
    }
    @Test
    public void getDiaryWaterGetsRightValue() throws SQLException {
        Ddiar.addDateToDiary("06.05.2020");
        Ddiar.updateDiaryWater("06.05.2020", 37);
        assertEquals(37, Ddiar.getDiaryWater("06.05.2020"));
        Ddiar.deleteDateFromDiary("06.05.2020");
    }
    @Test
    public void databaseCatchesErrorInCaseOneAppears() throws SQLException {
        kanta.dropTables();
        assertFalse(Ddiar.addDateToDiary("06.05.2020"));
        assertFalse(Ddiar.deleteDateFromDiary("06.05.2020"));
        assertFalse(Ddiar.updateDiary("60.05.2020", 220, 20, 20, 20));
        assertFalse(Ddiar.updateDiaryWater("06.05.2020", 40));
        assertEquals(-1, Ddiar.getDiaryWater("06.05.2020"));
        
        kanta.createTables();
    }
    @Test
    public void getDiaryDayDataCatchesError() throws SQLException {
        kanta.dropTables();
        Ddiar.getDiaryDayData("06.05.2020");
        assertEquals("Ei saatu siirrettyä päiväkirjadataa int-listalle.", outContent.toString());
        kanta.createTables();
    }
    @Test
    public void getDiaryDataCatchesError() throws SQLException {
        kanta.dropTables();
        Ddiar.getDiaryData();
        assertEquals("Ei voitu palauttaa päiväkirjadataa arraylistina.", outContent.toString());
        kanta.createTables();
    }
}
