package ruokasovellus;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {
    
    Database kanta;
    
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
    }
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void TEST1createTablesCreatesTheTables(){
        System.out.println("Testi:1");
        assertEquals("Tables: Diary DishContents Incredients Portions", kanta.getTableNames());
    }
    @Test
    public void TEST2addingIncredientToInexistingTablesReturnsFalse(){
        System.out.println("Testi:2");
        kanta.dropTables();
        assertFalse(kanta.addIncredient("muusi", 1167, 200, 40, 23));
        kanta.createTables();
    }
    @Test
    public void TEST3addIncredientAddsAnIncredient(){
        System.out.println("Testi:3");
        kanta.addIncredient("muusi", 1167, 200, 40, 23);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "muusi: 116.7(kcal), h:20.0, p:4.0, r:2.3", kanta.listIncredientsToString());
    }
    @Test
    public void TEST4incredientNamesAreUnique(){
        System.out.println("Testi:4");
        kanta.addIncredient("muusi", 1167, 200, 40, 23);
        kanta.addIncredient("muusi", 116, 20, 4, 2);
        kanta.addIncredient("muusi", 11167, 2040, 440, 423);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "muusi: 116.7(kcal), h:20.0, p:4.0, r:2.3", kanta.listIncredientsToString());
    }
    @Test
    public void TEST5deleteIncredientDeletesTheIncredient(){
        System.out.println("Testi:5");
        kanta.addIncredient("muusi", 1167, 200, 40, 23);
        kanta.addIncredient("puuro", 1097, 460, 133, 56);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "muusi: 116.7(kcal), h:20.0, p:4.0, r:2.3\n" +
        "puuro: 109.7(kcal), h:46.0, p:13.3, r:5.6", kanta.listIncredientsToString());
        //poistetaan puuro, ja katsotaan onko sitä enää tietokannassa.
        kanta.deleteIncredient("puuro");
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "muusi: 116.7(kcal), h:20.0, p:4.0, r:2.3", kanta.listIncredientsToString());
    }
    @Test
    public void TEST6getIncredientIdReturnsrightIncredientId() {
        System.out.println("Testi:6");
        //muusi on id=1 ja lisätty aiemmin
        kanta.addIncredient("luumusose", 35, 32, 10, 2);
        kanta.addIncredient("päärynäsose", 40, 40, 7, 2);
        assertEquals(3, kanta.getIncredientId("päärynäsose"));
        assertEquals(2, kanta.getIncredientId("luumusose"));
    }
 /*
    @Test
    public void TEST9addingPortionAddsPortions() {
        System.out.println("Testi:9");
        assertEquals("", kanta.getPortionNames());
        kanta.addPortion("LihapullatJaMuusi");
        assertEquals("LihapullatJaMuusi ", kanta.getPortionNames());
    }
    
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
}
