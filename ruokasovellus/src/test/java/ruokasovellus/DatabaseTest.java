package ruokasovellus;

import ruokasovellus.Database;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;



public class DatabaseTest {
    
    Database kanta;
    
    /*
Testeissä ei aluksi ole tauluja. Poista ruokasovellus.db ennen testausta!
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
        kanta.dropTables();
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
    public void createTablesCreatesTheTables(){
        assertEquals("Tables: Incredients", kanta.getTableNames());
    }
    @Test
    public void addIncredientAddsAnIncredient(){
        kanta.addIncredient("muusi", 200, 40, 23);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "muusi: h:20.0, p:4.0, r:2.3", kanta.listIncredientsToString());
    }
    @Test
    public void incredientNamesAreUnique(){
        kanta.addIncredient("muusi", 200, 40, 23);
        kanta.addIncredient("muusi", 20, 4, 2);
        kanta.addIncredient("muusi", 2040, 440, 423);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "muusi: h:20.0, p:4.0, r:2.3", kanta.listIncredientsToString());
    }
    @Test
    public void dropAndCreateAndListTablesMethodsWork(){
        assertEquals("Tables: Incredients", kanta.getTableNames());
        kanta.dropTables();
        assertEquals("Tables:", kanta.getTableNames());
        kanta.createTables();
        assertEquals("Tables: Incredients", kanta.getTableNames());
    }
    @Test
    public void deleteIncredientDeletesTheIncredient(){
        kanta.addIncredient("muusi", 200, 40, 23);
        kanta.addIncredient("puuro", 460, 133, 56);
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "muusi: h:20.0, p:4.0, r:2.3\n" +
        "puuro: h:46.0, p:13.3, r:5.6", kanta.listIncredientsToString());
        //poistetaan puuro, ja katsotaan onko sitä enää tietokannassa.
        kanta.deleteIncredient("puuro");
        assertEquals("Ruoka-aineslista \n" +
        "(Nimike: hiilihydraattia, proteiinia, rasvaa (g/100g))\n" +
        "muusi: h:20.0, p:4.0, r:2.3", kanta.listIncredientsToString());
    }
    @Test
    public void addingIncredientToInexistingTablesReturnsFalse(){
        kanta.dropTables();
        assertFalse(kanta.addIncredient("muusi", 200, 40, 23));
    }
    @Test
    public void listingIncredientsFromInexistingTablesReturnsErrorMessage(){
        kanta.dropTables();
        assertEquals("VIRHE: Ruoka-aineiden listaaminen epäonnistui.", kanta.listIncredientsToString());
    }
     
}
