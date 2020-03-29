package com.mycompany.ruokasovellus;

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
}
