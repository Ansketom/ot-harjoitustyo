
package com.mycompany.ruokasovellus;

import java.util.Scanner;
import javafx.application.Application;
import java.sql.SQLException;


public class Userinterface {
    
    public Database kanta;
    
    public Userinterface() throws SQLException{
        this.kanta=new Database();
    }
    
    public void start (Scanner lukija){
        
        textUI(lukija);

    }
    
     public void textUI(Scanner lukija){
        
        System.out.println("Tervetuloa ruokasovellukseen!");
        System.out.println("Uusi käyttäjä? Aloita luomalla tietokantataulut komennolla: 9");
        System.out.println("Lisää ruoka-aine komennolla: 1");
        System.out.println("Listaa lisätyt ruoka-aineet komennolla: 2");
        System.out.println("Sulje ohjelma komennolla: x");
        
        String komento;
        while(true){
            System.out.print("Valitse komento: ");
            komento=lukija.nextLine();
            if(komento.equals("1")){
                System.out.println("Mikä on ruoka-aineen nimi?");
                String incredient=lukija.nextLine();
                System.out.println("Paljonko siinä on hiilihydraattia/100g? (PITÄÄ OLLA YKSI DESIMAALI! esim 12.2)");
                String carbohyd= lukija.nextLine();
                System.out.println("Paljonko siinä on proteiinia/100g? (Muista yksi desimaali!)");
                String protein=lukija.nextLine();
                System.out.println("Paljonko siinä on rasvaa/100g? (Muista yksi desimaali!)");
                String fats= lukija.nextLine();
                
                //tietokanta int, niin tallennetaan data desigrammoina
                int ch=Integer.valueOf(carbohyd.replaceAll("\\D+", ""));
                int prot=Integer.valueOf(protein.replaceAll("\\D+", ""));
                int fat=Integer.valueOf(fats.replaceAll("\\D+", ""));
                
                kanta.addIncredient(incredient, ch, prot, fat);
                
            }else if (komento.equals("2")){
                kanta.listIncredients();
            }else if (komento.equals("x")){
                kanta.closeConnection();
                break;
            }else if (komento.equals("9")){
                kanta.createTables();
            }else if(komento.equals("test")){
                kanta.test()
            }
            
            
        }
        
    
    }
       
}
