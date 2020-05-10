package ruokasovellus;

import ruokasovellus.domain.DiaryFunctions;
import ruokasovellus.dao.Database;
import ruokasovellus.dao.DatabasePortions;
import ruokasovellus.dao.DatabaseDiary;
import ruokasovellus.dao.DatabaseIncredients;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class DatabaseTest {
    
    public Database kanta;
    public DatabaseIncredients Dincr;
    public DatabasePortions Dport;
    public DatabaseDiary Ddiar;
    public DiaryFunctions diary;
    public DateTimeFormatter date;
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    //Jos testeissä on ilmenee virhe, saattaa joissain tapauksissa olla tarpeen poistaa ruokasovellus.db ennen testausta!

    public DatabaseTest() throws SQLException{
        kanta = new Database();
        Dincr = new DatabaseIncredients(kanta);
        Dport = new DatabasePortions(kanta, Dincr);
        Ddiar = new DatabaseDiary(kanta);
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
    public void createTablesCreatesTheTables() throws SQLException {
        assertFalse(kanta.createTables());
        assertTrue(kanta.dropTables());
        assertFalse(kanta.dropTables());
        assertTrue(kanta.createTables());
    }
    
}
