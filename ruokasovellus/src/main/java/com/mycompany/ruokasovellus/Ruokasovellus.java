package com.mycompany.ruokasovellus;

import java.util.*;
import java.sql.SQLException;

public class Ruokasovellus {
    
    public static void main(String[]args) throws SQLException{
        Scanner lukija=new Scanner(System.in);
        Userinterface ui = new Userinterface();
        ui.start(lukija);
        
    }
}
