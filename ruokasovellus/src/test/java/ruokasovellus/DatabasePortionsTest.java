
package ruokasovellus;

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
public class DatabasePortionsTest {
    
    public Database kanta;
    public DatabaseIncredients Dincr;
    public DatabasePortions Dport;
    public DatabaseDiary Ddiar;
    public DateTimeFormatter date;
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    public DatabasePortionsTest() throws SQLException {
        kanta = new Database();
        Dincr = new DatabaseIncredients(kanta);
        Dport = new DatabasePortions(kanta, Dincr);
        Ddiar = new DatabaseDiary(kanta);
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void addAndDeletePortionWorks() throws SQLException {
        assertTrue(Dport.addPortion("aamupuuro"));
        assertTrue(Dport.deletePortion("aamupuuro"));
    }
    
    @Test
    public void addingAndDeletingPortionsWork() throws SQLException {
        Dincr.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        Dincr.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        Dincr.addIncredient("kevytmaito", 380, 27, 35, 15);
        Dincr.addIncredient("puolukka", 560, 89, 5, 7);
        Dport.addPortion("aamupuuro");
        assertTrue(Dport.addDishContents(1, 1, 100));
        assertTrue(Dport.addDishContents(1, 2, 10));
        assertTrue(Dport.addDishContents(1, 3, 150));
        assertTrue(Dport.addDishContents(1, 4, 50));
        assertTrue(Dport.deletePortionPart("aamupuuro", "kaurahiutale"));
        assertTrue(Dport.deletePortionPart("aamupuuro", "oliiviöljy"));
        assertTrue(Dport.deletePortionPart("aamupuuro", "kevytmaito"));
        assertTrue(Dport.deletePortionPart("aamupuuro", "puolukka"));
        Dincr.deleteIncredient("kaurahiutale");
        Dincr.deleteIncredient("oliiviöljy");
        Dincr.deleteIncredient("ekvytmaito");
        Dincr.deleteIncredient("puolukka");
        Dport.deletePortion("aamupuuro");
    }
    @Test
    public void getPortionIdReturnsTheRightId() throws SQLException {
        Dport.addPortion("aamupuuro");
        Dport.addPortion("pakastepitsaMozzarella");
        Dport.addPortion("banaaniJaRahka");
        assertEquals(1, Dport.getPortionId("aamupuuro"));
        assertEquals(2, Dport.getPortionId("pakastepitsaMozzarella"));
        assertEquals(3, Dport.getPortionId("banaaniJaRahka"));
        assertEquals(-1, Dport.getPortionId("mukiMakkara"));
        Dport.deletePortion("aamupuuro");
        Dport.deletePortion("pakastepitsaMozzarella");
        Dport.deletePortion("banaaniJaRahka");
    }
    @Test
    public void getPortionNamesReturnsPortionNames() throws SQLException {
        Dport.addPortion("aamupuuro");
        Dport.addPortion("pakastepitsaMotsarella");
        Dport.addPortion("banaaniJaRahka");
        ArrayList<String> testi = new ArrayList<>();
        testi.add("aamupuuro");
        testi.add("banaaniJaRahka");
        testi.add("pakastepitsaMotsarella");
        assertEquals(testi, Dport.getPortionNames());
        Dport.deletePortion("aamupuuro");
        Dport.deletePortion("pakastepitsaMozzarella");
        Dport.deletePortion("banaaniJaRahka");
       
    }
    @Test
    public void getPortionContentsWithNameWorks() throws SQLException {
        Dincr.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        Dincr.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        Dincr.addIncredient("kevytmaito", 380, 27, 35, 15);
        Dincr.addIncredient("puolukka", 560, 89, 5, 7);
        Dport.addPortion("aamupuuro");
        Dport.addDishContents(1, 1, 100);
        Dport.addDishContents(1, 2, 10);
        Dport.addDishContents(1, 3, 150);
        Dport.addDishContents(1, 4, 50);
        ArrayList<String> testi = new ArrayList<>();
        testi.add("aamupuuro:");
        testi.add("kaurahiutale(100g)");
        testi.add("kevytmaito(150g)");
        testi.add("oliiviöljy(10g)");
        testi.add("puolukka(50g)");
        assertEquals(testi, Dport.getPortionContentsWithName("aamupuuro"));
        Dport.deletePortionPart("aamupuuro", "kaurahiutale");
        Dport.deletePortionPart("aamupuuro", "oliiviöljy");
        Dport.deletePortionPart("aamupuuro", "kevytmaito");
        Dport.deletePortionPart("aamupuuro", "puolukka");
        Dport.deletePortion("aamupuuro");
        Dincr.deleteIncredient("kaurahiutale");
        Dincr.deleteIncredient("kevytmaito");
        Dincr.deleteIncredient("oliiviöljy");
        Dincr.deleteIncredient("puolukka");
    }
    @Test
    public void getPortionContentsInListReturnsAList() throws SQLException {
        Dincr.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        Dincr.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        Dincr.addIncredient("kevytmaito", 380, 27, 35, 15);
        Dincr.addIncredient("puolukka", 560, 89, 5, 7);
        Dport.addPortion("aamupuuro");
        Dport.addDishContents(1, 1, 100);
        Dport.addDishContents(1, 2, 10);
        Dport.addDishContents(1, 3, 150);
        Dport.addDishContents(1, 4, 50);
        ArrayList <String> parts = new ArrayList<>();
        parts.add("kaurahiutale:100g");
        parts.add("kevytmaito:150g");
        parts.add("oliiviöljy:10g");
        parts.add("puolukka:50g");
        assertEquals(parts, Dport.getPortionContentsInList("aamupuuro"));
        Dport.deletePortionPart("aamupuuro", "kaurahiutale");
        Dport.deletePortionPart("aamupuuro", "oliiviöljy");
        Dport.deletePortionPart("aamupuuro", "kevytmaito");
        Dport.deletePortionPart("aamupuuro", "puolukka");
        Dport.deletePortion("aamupuuro");
    }
    
    @Test
    public void getDishContentsArrayListReturnsList() throws SQLException {
        Dincr.addIncredient("kaurahiutale", 3620, 540, 140, 75);
        Dincr.addIncredient("oliiviöljy", 9000, 0, 0, 1000);
        Dincr.addIncredient("kevytmaito", 380, 27, 35, 15);
        Dincr.addIncredient("puolukka", 560, 89, 5, 7);
        Dport.addPortion("aamupuuro");
        Dport.addDishContents(1, 1, 100);
        Dport.addDishContents(1, 2, 10);
        Dport.addDishContents(1, 3, 150);
        Dport.addDishContents(1, 4, 50);
        ArrayList<String> testi = new ArrayList<>();
        testi.add("annos, ruoka-aine , määrä(g):");
        testi.add("aamupuuro, kaurahiutale, 100");
        testi.add("aamupuuro, oliiviöljy, 10");
        testi.add("aamupuuro, kevytmaito, 150");
        testi.add("aamupuuro, puolukka, 50");
        assertEquals(testi, Dport.getDishContentArrayList());
        Dport.deletePortionPart("aamupuuro", "kaurahiutale");
        Dport.deletePortionPart("aamupuuro", "oliiviöljy");
        Dport.deletePortionPart("aamupuuro", "kevytmaito");
        Dport.deletePortionPart("aamupuuro", "puolukka");
        Dport.deletePortion("aamupuuro");
        Dincr.deleteIncredient("kaurahiutale");
        Dincr.deleteIncredient("oliiviöljy");
        Dincr.deleteIncredient("kevytmaito");
        Dincr.deleteIncredient("puolukka");
    }
    
    @Test
    public void getPortionContentsInListCatchesError() throws SQLException {
        kanta.dropTables();
        Dport.getPortionContentsInList("aamupuuro");
        assertEquals("Ei onnistuttu luomaan palautettavaa taulua annoksen osista.", outContent.toString());
        kanta.createTables();
    }
    @Test
    public void getPortionNamesCatchesError() throws SQLException {
        kanta.dropTables();
        Dport.getPortionNames();
        assertEquals("VIRHE: ei onnistuttu etsimään tauluja.", outContent.toString());
        kanta.createTables();
    }
    @Test
    public void getPortionWithNameCatchesError() throws SQLException {
        kanta.dropTables();
        Dport.getPortionContentsWithName("aamupuuro");
        assertEquals("VIRHE: ei onnistuttu etsimään annoksen sisältöä.", outContent.toString());
        kanta.createTables();
    }
    @Test
    public void getDishContentArrayListCatchesError() throws SQLException {
        kanta.dropTables();
        Dport.getDishContentArrayList();
        assertEquals("VIRHE: ei onnistuttu hakemaan annostensisältödataa.", outContent.toString());
        kanta.createTables();
    }
    @Test
    public void databaseCatchesErrorInCaseOneAppears() throws SQLException {
        kanta.dropTables();
        assertFalse(Dport.addPortion("aamupuuro"));
        assertFalse(Dport.deletePortion("aamupuuro"));
        assertFalse(Dport.addDishContents(1, 1, 100));
        assertFalse(Dport.deletePortionPart("aamupuuro", "kaurahiutale"));
    }
}
