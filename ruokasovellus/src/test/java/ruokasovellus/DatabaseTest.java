package ruokasovellus;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.junit.Assert;


import ruokasovellus.Database;
import ruokasovellus.DiaryFunctions;




public class DatabaseTest {
    
    public Database kanta;
    public DiaryFunctions diary;
    public DateTimeFormatter date;
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /*
Poista ruokasovellus.db ennen testausta!
 
TESTIEN JÄRJESTYKSELLÄ ON VÄLIÄ!
Tauluja ei voi poistaa joka testin välissä, koska kun tauluille tulee riippuvuussuhteita
    toisiinsa niin niiden droppaus, tai koko tietokantatiedoston droppaus, ei mene enää läpi.
    Asia on ratkaistu niin, että testiluokan myöhemmät testit hyödyntävät aiempien testien
    luomia tietokantarivejä. Näin siis testien järjestyksellä on väliä!!
     */
    public DatabaseTest() throws SQLException{
        kanta= new Database();
        diary = new DiaryFunctions(kanta);
        date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.setOut(new PrintStream(outContent));

    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
    public void createTablesCreatesTheTables() throws SQLException {
        assertTrue(kanta.dropTables());
        assertTrue(kanta.createTables());
        }
    @Test
    public void addDeleteAndListIncredientsMethodsWork()throws SQLException {
        System.out.println("Testi:2");
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "kaurahiutale: 362.0(kcal), h:54.0, p:14.0, r:7.5\n" + 
        "oliiviöljy: 900.0(kcal), h:0.0, p:0.0, r:100.0", kanta.listIncredientsToString());
        kanta.deleteIncredient("kaurahiutale");
        kanta.deleteIncredient("oliiviöljy");
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))", kanta.listIncredientsToString());
    
    }
    @Test
    public void incredientNamesAreUniqueAndListIncredientsToStringWorks() throws SQLException {
        System.out.println("Testi:3");
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("kaurahiutale", 1167, 200, 40, 23);
        kanta.addIncredient("kaurahiutale", 116, 20, 4, 2);
        kanta.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        kanta.addIncredient("oliiviöljy", 1117, 200, 4460, 423);
        kanta.addIncredient("oliiviöljy", 1167, 240, 440, 4553);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "kaurahiutale: 362.0(kcal), h:54.0, p:14.0, r:7.5\n" + 
        "oliiviöljy: 900.0(kcal), h:0.0, p:0.0, r:100.0", kanta.listIncredientsToString());
    }
    
    @Test
    public void addAndDeletePortionWorks() {
        assertTrue(kanta.addPortion("aamupuuro"));
        assertTrue(kanta.deletePortion("aamupuuro"));
    }
    
    @Test
    public void addingAndDeletingPortionsWork() {
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        kanta.addIncredient("kevytmaito", 380, 27, 35, 15);
        kanta.addIncredient("puolukka", 560, 89, 5, 7);
        kanta.addPortion("aamupuuro");
        assertTrue(kanta.addDishContents(1, 1, 100));
        assertTrue(kanta.addDishContents(1, 2, 10));
        assertTrue(kanta.addDishContents(1, 3, 150));
        assertTrue(kanta.addDishContents(1, 4, 50));
        assertTrue(kanta.deletePortionPart("aamupuuro", "kaurahiutale"));
        assertTrue(kanta.deletePortionPart("aamupuuro", "oliiviöljy"));
        assertTrue(kanta.deletePortionPart("aamupuuro", "kevytmaito"));
        assertTrue(kanta.deletePortionPart("aamupuuro", "puolukka"));
        kanta.deletePortion("aamupuuro");
    }
    @Test
    public void getPortionIdReturnsTheRightId() {
        kanta.addPortion("aamupuuro");
        kanta.addPortion("pakastepitsaMozzarella");
        kanta.addPortion("banaaniJaRahka");
        assertEquals(1, kanta.getPortionId("aamupuuro"));
        assertEquals(2, kanta.getPortionId("pakastepitsaMozzarella"));
        assertEquals(3, kanta.getPortionId("banaaniJaRahka"));
        assertEquals(-1, kanta.getPortionId("mukiMakkara"));
        kanta.deletePortion("aamupuuro");
        kanta.deletePortion("pakastepitsaMozzarella");
        kanta.deletePortion("banaaniJaRahka");
    }
    @Test
    public void getPortionNamesReturnsPortionNames() {
        kanta.addPortion("aamupuuro");
        kanta.addPortion("pakastepitsaMozzarella");
        kanta.addPortion("banaaniJaRahka");
        assertEquals("aamupuuro\nbanaaniJaRahka\npakastepitsaMozzarella\n", kanta.getPortionNames());
        kanta.deletePortion("aamupuuro");
        kanta.deletePortion("pakastepitsaMozzarella");
        kanta.deletePortion("banaaniJaRahka");
    }
    @Test
    public void getPortionContentsWithNameWorks() {
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        kanta.addIncredient("kevytmaito", 380, 27, 35, 15);
        kanta.addIncredient("puolukka", 560, 89, 5, 7);
        kanta.addPortion("aamupuuro");
        kanta.addDishContents(1, 1, 100);
        kanta.addDishContents(1, 2, 10);
        kanta.addDishContents(1, 3, 150);
        kanta.addDishContents(1, 4, 50);
        assertEquals("aamupuuro: kaurahiutale(100g) kevytmaito(150g) oliiviöljy(10g) puolukka(50g)", kanta.getPortionContentsWithName("aamupuuro"));
        kanta.deletePortionPart("aamupuuro", "kaurahiutale");
        kanta.deletePortionPart("aamupuuro", "oliiviöljy");
        kanta.deletePortionPart("aamupuuro", "kevytmaito");
        kanta.deletePortionPart("aamupuuro", "puolukka");
        kanta.deletePortion("aamupuuro");
    }
    @Test
    public void getPortionContentsInListReturnsAList() {
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        kanta.addIncredient("kevytmaito", 380, 27, 35, 15);
        kanta.addIncredient("puolukka", 560, 89, 5, 7);
        kanta.addPortion("aamupuuro");
        kanta.addDishContents(1, 1, 100);
        kanta.addDishContents(1, 2, 10);
        kanta.addDishContents(1, 3, 150);
        kanta.addDishContents(1, 4, 50);
        ArrayList <String> parts = new ArrayList<>();
        parts.add("kaurahiutale:100g");
        parts.add("kevytmaito:150g");
        parts.add("oliiviöljy:10g");
        parts.add("puolukka:50g");
        assertEquals(parts, kanta.getPortionContentsInList("aamupuuro"));
        kanta.deletePortionPart("aamupuuro", "kaurahiutale");
        kanta.deletePortionPart("aamupuuro", "oliiviöljy");
        kanta.deletePortionPart("aamupuuro", "kevytmaito");
        kanta.deletePortionPart("aamupuuro", "puolukka");
        kanta.deletePortion("aamupuuro");
    }
    @Test
    public void getDishContentsToStringReturnsString() {
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        kanta.addIncredient("kevytmaito", 380, 27, 35, 15);
        kanta.addIncredient("puolukka", 560, 89, 5, 7);
        kanta.addPortion("aamupuuro");
        kanta.addDishContents(1, 1, 100);
        kanta.addDishContents(1, 2, 10);
        kanta.addDishContents(1, 3, 150);
        kanta.addDishContents(1, 4, 50);
        String data = "annos, ruoka-aine , määrä(g):\naamupuuro kaurahiutale 100\naamupuuro oliiviöljy 10\naamupuuro kevytmaito 150\naamupuuro puolukka 50\n";
        assertEquals(data, kanta.getDishContentToString());
        kanta.deletePortionPart("aamupuuro", "kaurahiutale");
        kanta.deletePortionPart("aamupuuro", "oliiviöljy");
        kanta.deletePortionPart("aamupuuro", "kevytmaito");
        kanta.deletePortionPart("aamupuuro", "puolukka");
        kanta.deletePortion("aamupuuro");
        kanta.deleteIncredient("kaurahiutale");
        kanta.deleteIncredient("oliiviöljy");
        kanta.deleteIncredient("kevytmaito");
        kanta.deleteIncredient("puolukka");
    }
    @Test
    public void getIncredientIdReturnsInt() {
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("puolukka", 560, 89, 5, 7);
        assertEquals(1, kanta.getIncredientId("kaurahiutale"));
        assertEquals(2, kanta.getIncredientId("puolukka"));
        kanta.deleteIncredient("kaurahiutale");
        kanta.deleteIncredient("puolukka");
        }
    @Test
    public void getIncredientDataIdReturnsData() {
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        int [] idata = new int [4];
        idata [0] = 3620;
        idata [1] = 540;
        idata [2] = 140;
        idata [3] = 75;
        int [] kdata = kanta.getIncredientDataInInt("kaurahiutale");
        assertEquals(idata [0], kdata [0]);
        assertEquals(idata [1], kdata [1]);
        assertEquals(idata [2], kdata [2]);
        assertEquals(idata [3], kdata [3]);
        kanta.deleteIncredient("kaurahiutale");
    }
    @Test
    public void addingAndDeletingDateTODiaryWorks() {
        assertTrue(kanta.addDateToDiary("05.05.2020"));
        assertTrue(kanta.deleteDateFromDiary("05.05.2020"));
    }
    @Test
    public void updateDiaryAndGettingDiaryDataWorks() {
        
        kanta.addDateToDiary("05.05.2020");
        kanta.updateDiary("05.05.2020", 21000, 1800, 1200, 1000);
        ArrayList <String> dData = new ArrayList<>();
        dData.add("05.05.2020: Kcal: 2100.0, hiilihyd.: 180.0g, proteiini: 120.0g, rasva: 100.0g, vesi: 0.0litraa");
        assertEquals(dData, kanta.getDiaryData());
        dData.clear();
        kanta.updateDiary("05.05.2020", 25000, 2500, 1500, 1000);
        dData.add("05.05.2020: Kcal: 2500.0, hiilihyd.: 250.0g, proteiini: 150.0g, rasva: 100.0g, vesi: 0.0litraa");
        assertEquals(dData, kanta.getDiaryData());
        kanta.deleteDateFromDiary("05.05.2020");
    }
    @Test
    public void getDiaryDayDataReturnsData() {
        int [] testdata = new int [4];
        testdata [0] = 21000;
        testdata [1] = 1800;
        testdata [2] = 1200;
        testdata [3] = 1000;
        kanta.addDateToDiary("05.05.2020");
        kanta.updateDiary("05.05.2020", 21000, 1800, 1200, 1000);
        int [] daydata = kanta.getDiaryDayData("05.05.2020");
        assertEquals(testdata[0], daydata [0]);
        assertEquals(testdata[1], daydata [1]);
        assertEquals(testdata[2], daydata [2]);
        assertEquals(testdata[3], daydata [3]);
        kanta.deleteDateFromDiary("05.05.2020");
    }
}
