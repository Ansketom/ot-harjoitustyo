package ruokasovellus;

import java.io.File;
import java.util.*;
import java.sql.SQLException;
import java.nio.file.*;
import java.io.IOException;

public class Main {
    
    public static void main(String[]args) throws SQLException{
        
        Scanner lukija=new Scanner(System.in);
        Userinterface ui = new Userinterface();
        ui.start(lukija);
        
    }
}
