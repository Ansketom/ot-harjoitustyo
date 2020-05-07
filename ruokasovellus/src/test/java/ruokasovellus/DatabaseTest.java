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
import ruokasovellus.DatabaseIncredients;
import ruokasovellus.DatabasePortions;
import ruokasovellus.DatabaseDiary;
import ruokasovellus.DiaryFunctions;




public class DatabaseTest {
    
    public Database kanta;
    public DatabaseIncredients Dincr;
    public DatabasePortions Dport;
    public DatabaseDiary Ddiar;
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
        kanta = new Database();
        Dincr = new DatabaseIncredients(kanta);
        Dport = new DatabasePortions(kanta, Dincr);
        Ddiar = new DatabaseDiary(kanta, Dport);
        diary = new DiaryFunctions(kanta, Dincr, Dport, Ddiar);
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
        assertFalse(kanta.createTables());
        assertTrue(kanta.dropTables());
        assertFalse(kanta.dropTables());
        assertTrue(kanta.createTables());
    }
    
}
