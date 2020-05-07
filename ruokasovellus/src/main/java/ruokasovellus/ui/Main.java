package ruokasovellus.ui;


import ruokasovellus.ui.DesktopUI;
import java.io.File;
import java.util.*;
import java.sql.SQLException;
import java.io.IOException;

/**
 * Käyttöliittymän käynnistävä luokka.
 * @author AnssiKetomäki
 */

public class Main {
    
    public static void main(String[]args) throws SQLException {
        
        Scanner lukija = new Scanner(System.in);
        //TextUI ui = new TextUI();
        //ui.start(lukija);
        DesktopUI.main(args);
        
    }
}
