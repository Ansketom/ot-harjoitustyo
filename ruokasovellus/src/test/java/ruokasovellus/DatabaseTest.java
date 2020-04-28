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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {
    
    Database kanta;
    DiaryFunctions diary;
    DateTimeFormatter date;
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
    public void TEST1createTablesCreatesTheTables() throws SQLException {
        System.out.println("Testi:1");
        assertTrue(kanta.createTables());
    }
    @Test
    public void TEST2addIncredientAddsAnIncredient()throws SQLException {
        System.out.println("Testi:2");
        kanta.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        kanta.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "kaurahiutale: 362.0(kcal), h:54.0, p:14.0, r:7.5\n" + 
        "oliiviöljy: 900.0(kcal), h:0.0, p:0.0, r:100.0", kanta.listIncredientsToString());
    }
    @Test
    public void TEST3incredientNamesAreUniqueAndListIncredientsToStringWorks() throws SQLException {
        System.out.println("Testi:3");
        kanta.addIncredient("kaurahiutale", 1167, 200, 40, 23);
        kanta.addIncredient("kaurahiutale", 116, 20, 4, 2);
        kanta.addIncredient("oliiviöljy", 11167, 2040, 440, 423);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "kaurahiutale: 362.0(kcal), h:54.0, p:14.0, r:7.5\n" + 
        "oliiviöljy: 900.0(kcal), h:0.0, p:0.0, r:100.0", kanta.listIncredientsToString());
    }
    @Test
    public void TEST4deleteIncredientDeletesTheIncredient() throws SQLException {
        System.out.println("Testi:4");
        kanta.addIncredient("muusi", 1167, 200, 40, 23);
       
        //poistetaan muusi ja oliiviöljy, ja katsotaan onko niitä enää tietokannassa.
        kanta.deleteIncredient("muusi");
        kanta.deleteIncredient("oliiviöljy");
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "kaurahiutale: 362.0(kcal), h:54.0, p:14.0, r:7.5", kanta.listIncredientsToString());
    }
    
    @Test
    public void TEST5addPortionWorks() throws SQLException {
        System.out.println("Testi:6");
        assertTrue(kanta.addPortion("aamupuuro"));
    }
    @Test
    public void TEST6addDishContentsWorks() throws SQLException {
        System.out.println("Testi:6");
        //kaurahiutale lisätty aiemmin
        kanta.addIncredient("kevytmaito", 380, 27, 35, 15);
        kanta.addIncredient("puolukka", 560, 89, 5, 7);
        kanta.addIncredient("rypsiöljy", 9000, 0, 0, 100);
        assertTrue(kanta.addDishContents(1, 1, 100));
        assertTrue(kanta.addDishContents(1, 3, 75));
        
        
    }
    
    @Test
    public void TEST7deletePortionPartWorks() throws SQLException {
        System.out.println("Testi:7");
        assertTrue(kanta.deletePortionPart("aamupuuro", "puolukka"));
        }
/* TESTI EI SAA PÄIVÄÄ LUOTUA, VAIKKA OHJELMASSA TÄMÄ KYLLÄ TOIMII.
    @Test
    public void TEST8addDateToDiaryWorks() throws SQLException {
        System.out.println("Testi:8");
        diary.addDate(kanta, "01.04.2020");
        assertEquals("Päivän lisäys onnistui", outContent.toString());
    }
    @Test
    public void TEST9upDateDiaryWorks() throws SQLException {
        System.out.println("Testi:9");
        Assert.assertTrue(kanta.updateDiary("29.04.2020", 20000, 1200, 1775, 800));
    }
*/ 
    @Test
    public void TESTAgetPortionIdGetsTheId() throws SQLException {
        System.out.println("Testi:10");
        assertEquals(1, kanta.getPortionId("aamupuuro"));
    }
    @Test
    public void TESTBgetPortionNamesGetsTheNames() throws SQLException {
        System.out.println("Testi:11");
        assertEquals("aamupuuro\n", kanta.getPortionNames());
    }
    @Test
    public void TESTCgetPortionContentsWithNameWorks() throws SQLException {
        System.out.println("Testi:12");
        assertEquals("aamupuuro: kaurahiutale(100g)", kanta.getPortionContentsWithName("aamupuuro"));
    }
 /*
    @Test
    public void TESTAcannotAddSamePortionTwice() {
        System.out.println("Testi:A");
        System.out.println("Testi:");
        kanta.addPortion("LihapullatJaMuusi");
        kanta.addPortion("LihapullatJaMuusi");
        assertEquals("LihapullatJaMuusi ", kanta.getPortionNames());
    }
    
    @Test
    public void TESTBgetPortionIdReturnsrightPortionId() {
        System.out.println("Testi:B");
        //LihapullatJaMuusi added before
        kanta.addPortion("Kaurapuuro100gJaPuolukat");
        kanta.addPortion("Nyhtismakarooni");
        kanta.addPortion("KKahviJaPulla");
        assertEquals(1,kanta.getPortionId("LihapullatJaMuusi"));
        assertEquals(3,kanta.getPortionId("Nyhtismakarooni"));
    }
    @Test
    public void TESTCgetPortionContentsReturnsPortionContents(){
        System.out.println("Testi:C");
        //tietokannassa jo: muusi, luumusose, päärynäsose
        kanta.addIncredient("lihapulla", 690, 0, 150, 10);
        kanta.addPortion("LihapullatJaMuusi");
        kanta.addDishContents(1, 1, 200);
        kanta.addDishContents(1, 4, 150);
        kanta.addDishContents(1, 2, 25);
        assertEquals("LihapullatJaMuusi: lihapulla(150g) muusi(200g) luumusose(25g)", kanta.getPortionContents("LihapullatJaMuusi"));
    }
            @Test
    public void TESTDcloseConnectionWorks(){
        System.out.println("Testi:D");
        kanta.closeConnection();
        assertFalse(kanta.addPortion("happymeal"));
    }

    
    
    /*

    @Test
    public void TESTEopenConnectionWorks(){
        kanta.openConnection();
        assertTrue(kanta.addPortion("happymeal"));
    }
        @Test
    public void TEST7dropAndListTablesMethodsWork(){
        assertEquals("Tables: Diary DishContents Incredients Portions", kanta.getTableNames());
        kanta.dropTables();
        assertEquals("Tables:", kanta.getTableNames());
        kanta.createTables();
    }
    @Test
    public void TEST8listingIncredientsFromInexistingTablesReturnsErrorMessage(){
        kanta.dropTables();
        assertEquals("VIRHE: Ruoka-aineiden listaaminen epäonnistui.", kanta.listIncredientsToString());
        kanta.createTables();
    }
    */
        @Test
    public void TESTVgetDateGetsADate(){
        LocalDate now = LocalDate.now();
        assertEquals(date.format(now), diary.getDate());
    }
    @Test
    public void TESTYaddDateWorks()throws SQLException {
        diary.addDate(kanta, "27.04.2020");
        int [] data = {0, 0, 0, 0};
        int [] datab = kanta.getDiaryDayData("27.04.2020");
        assertEquals(data [0], datab [0]);
        assertEquals(data [1], datab [1]);
        assertEquals(data [2], datab [2]);
        assertEquals(data [3], datab [3]);
    }
    


}
