
package ruokasovellus;

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
        System.out.println("Tyhjennä taulut komennolla: drop");
        System.out.println("Listaa taulut komennolla: tables");
        System.out.println("");
        
        System.out.println("Lisää ruoka-aine komennolla: 1");
        System.out.println("Listaa lisätyt ruoka-aineet komennolla: 2");
        System.out.println("Poista ruoka-aine komennolla: 3");
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
                
                incredient=incredient.toLowerCase();
                //tietokanta int, niin tallennetaan data desigrammoina
                int ch=Integer.valueOf(carbohyd.replaceAll("\\D+", ""));
                int prot=Integer.valueOf(protein.replaceAll("\\D+", ""));
                int fat=Integer.valueOf(fats.replaceAll("\\D+", ""));
                
                boolean y = kanta.addIncredient(incredient, ch, prot, fat);
                
            }else if (komento.equals("2")){
                System.out.println(kanta.listIncredientsToString());
            }else if (komento.equals("3")){
                System.out.println("Minkä niminen ruoka-aine poistetaan?");
                String name=lukija.nextLine();
                name=name.toLowerCase();
                kanta.deleteIncredient(name);
            }else if (komento.equals("x")){
                kanta.closeConnection();
                break;
            }else if (komento.equals("9")){
                kanta.createTables();
            }else if(komento.equals("drop")){
                kanta.dropTables();
            }else if(komento.equals("tables")){
                System.out.println(kanta.getTableNames());
            }
            
            
        }
        
    
    }
       
}
