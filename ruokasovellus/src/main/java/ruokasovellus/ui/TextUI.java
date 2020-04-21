
package ruokasovellus.ui;

import java.util.Scanner;

import java.sql.SQLException;
import ruokasovellus.Database;


public class TextUI{
    
    public Database kanta;
    
    public TextUI() throws SQLException {
        this.kanta = new Database();
    }
    public void start(Scanner lukija) {
        textUserInterface(lukija);

    }
    
    public void textUserInterface(Scanner lukija) {
        
        System.out.println("Tervetuloa ruokasovellukseen!");
        System.out.println("Uusi käyttäjä? Aloita luomalla tietokantataulut komennolla: 9");
        System.out.println("Tyhjennä taulut komennolla: drop");
        System.out.println("Listaa taulut komennolla: tables");
        System.out.println("Raaka dishContent komennolla: dishContent");
        System.out.println("");
        
        System.out.println("Lisää ruoka-aine komennolla: 1");
        System.out.println("Listaa lisätyt ruoka-aineet komennolla: 2");
        System.out.println("Poista ruoka-aine komennolla: 3");
        System.out.println("Lisää ruoka-annos komennolla: 5");
        System.out.println("Etsi lisättyjen ruoka-annosten nimet komennolla: 6");
        System.out.println("Etsi ruoka-annoksen osat ja määrät komennolla: 7");
        System.out.println("Sulje ohjelma komennolla: x");
        
        String komento;
        while (true) {
            System.out.print("Valitse komento: ");
            komento = lukija.nextLine();
            if (komento.equals("1")) {
                System.out.println("Mikä on ruoka-aineen nimi?");
                String incredient = lukija.nextLine();
                System.out.println("Paljonko siinä on energiaa(kcal)/100g? (1 desimaalin tarkkuudella. Pitää olla 1 desimaali! esim 69.6)");
                String energy = lukija.nextLine();
                System.out.println("Paljonko siinä on hiilihydraattia/100g? (Muista 1 desimaali esim 12.2)");
                String carbohyd = lukija.nextLine();
                System.out.println("Paljonko siinä on proteiinia/100g? (Muista yksi desimaali!)");
                String protein = lukija.nextLine();
                System.out.println("Paljonko siinä on rasvaa/100g? (Muista yksi desimaali!)");
                String fats = lukija.nextLine();
                
                incredient = incredient.toLowerCase();
                //tietokanta int, niin tallennetaan data desigrammoina
                int ene = Integer.valueOf(energy.replaceAll("\\D+", ""));
                int ch = Integer.valueOf(carbohyd.replaceAll("\\D+", ""));
                int prot = Integer.valueOf(protein.replaceAll("\\D+", ""));
                int fat = Integer.valueOf(fats.replaceAll("\\D+", ""));
                
                boolean y = kanta.addIncredient(incredient, ene, ch, prot, fat);
                
            } else if (komento.equals("2")) {
                System.out.println(kanta.listIncredientsToString());
            } else if (komento.equals("3")) {
                System.out.println("Minkä niminen ruoka-aine poistetaan?");
                String name = lukija.nextLine();
                name = name.toLowerCase();
                kanta.deleteIncredient(name);
            } else if (komento.equals("x")) {
                kanta.closeConnection();
                break;
            } else if (komento.equals("5")) {
                System.out.println("Minkä niminen Ruoka-annos lisätään?");
                String name = lukija.nextLine();
                if(kanta.addPortion(name)){
                    int iPort = kanta.getPortionId(name);
                    String incr;
                    int amount = 0;
                    System.out.println( kanta.listIncredientsToString() );
                    while(true) {
                        System.out.println("Mikä ruoka-aine annokseen kuuluu? (Paina vain ENTER, jos kaikki annoksen osat syötetty)");
                        incr = lukija.nextLine();                        
                        if(incr.equals("")){
                            break;
                        }
                        int incrId = kanta.getIncredientId(incr);
                        System.out.println("Paljonko ruoka-ainetta " + incr + " on annoksessa (tasa-grammamäärä)");
                        amount = Integer.valueOf(lukija.nextLine());
                        kanta.addDishContents(iPort, incrId, amount);
                    }
                }
                
                
                
            } else if (komento.equals("6")) {
                System.out.println( kanta.getPortionNames() );
            } else if (komento.equals("7")) {
                System.out.println(kanta.getPortionNames());
                System.out.println("Minkä nimisen annoksen sisältö haetaan?");
                String name = lukija.nextLine();
                System.out.println( kanta.getPortionContents(name));
            } else if (komento.equals("9")) {
                kanta.createTables();
            } else if (komento.equals("drop")) {
                kanta.dropTables();
            } else if (komento.equals("tables")) {
                System.out.println(kanta.getTableNames());
            } else if (komento.equals("dishContent")) {
                System.out.println(kanta.getDishContentData());
            }
            
            
        }
        
    
    }
    

    
    
    
    
    
    
    
       
}
