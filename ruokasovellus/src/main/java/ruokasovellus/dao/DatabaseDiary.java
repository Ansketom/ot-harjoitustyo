
package ruokasovellus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Päiväkirjaan liittyvät asiat tietokantaan tekevä luokka.
 * @author AnssiKetomäki
 */
public class DatabaseDiary {

    public Database kanta;
    public Connection db;

    
    public DatabaseDiary(Database kanta) throws SQLException {
        this.kanta = kanta;
        this.db = kanta.db;
    }
    
    /**
    * Metodi avaa ohjelmalle yhteyden tietokantaan.
     * @throws java.sql.SQLException
    */
    public void openConnection() throws SQLException {
        db = DriverManager.getConnection("jdbc:sqlite:ruokasovellus.db");
    }
    
    /**
     * Metodi sulkee ohjelman yhteyden tietokantaan.
     * @throws java.sql.SQLException
     */
    public void closeConnection() throws SQLException {
        db.close();
    }
    
    /**
     * Metodi lisää tietokannan päiväkirja-tauluun halutun päivämäärän, ja määrittää taulun
     * kaikkien muiden sarakkeiden kohdalle arvon nolla.
     *
     * @param date Käyttäjän antama päivämäärä
     *
     * @return totuusarvo siitä onnistuivatko metodin tietokantatoiminnot
     */
    public boolean addDateToDiary(String date) {
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("INSERT INTO Diary (date, kcal, ch, prot, fat, water) VALUES(?,?,?,?,?,?)");
            p.setString(1, date);
            p.setInt(2, 0);
            p.setInt(3, 0);
            p.setInt(4, 0);
            p.setInt(5, 0);
            p.setInt(6, 0);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Päivämäärän lisääminen epäonnistui.");
            return false;
        }
    }
    
    /**
     * Metodi poistaa tietokannan päiväkirjataulusta annetun päivämäärän, ja siihen liitetyt
     * tiedot (tätä metodia tarvitaan testauksessa).
     *
     * @param date Käyttäjän antama päivämäärä
     *
     * @return totuusarvo siitä onnistuivatko metodin tietokantatoiminnot
     */
    public boolean deleteDateFromDiary(String date) {
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("DELETE FROM Diary WHERE date=?");
            p.setString(1, date);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Päivämäärän poistaminen epäonnistui.");
            return false;
        }
    }

    /**
     * Metodi päivittää tietokannan päiväkirja-tauluun käyttäjän antaman päivämäärän
     * riville muut arvot tyypillisesti syödyn ruoan päiväkirjaan merkkaamisen yhteydessä.
     *
     * @param date Käyttäjän antama päivämäärä
     * @param kcal tietokantaan päivitettävä uusi päivän energiamäärä (kcal/10)
     * @param ch tietokantaan päivitettävä uusi päivän hiilihydraattimäärä grammoissa
     * @param prot tietokantaan päivitettävä uusi päivän proteiinimäärä grammoissa
     * @param fat tietokantaan päivitettävä uusi päivän rasvamäärä grammoissa
     *
     * @return totuusarvo siitä onnistuivatko metodin tietokantatoiminnot
     */
    public boolean updateDiary(String date, int kcal, int ch, int prot, int fat) {
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("UPDATE Diary SET kcal = ?, ch = ?, prot = ?, fat = ? WHERE date  = ?");
            p.setInt(1, kcal);
            p.setInt(2, ch);
            p.setInt(3, prot);
            p.setInt(4, fat);
            p.setString(5, date);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Päiväkirjamerkinnän päivitys epäonnistui.");
            return false;
        }
    }

    /**
     * Metodi palauttaa koko päiväkirjataulun sisällön ArrayListina riveittäin
     * stringiksi muutettuna.
     *
     * @return ArrayList, jonka sisältö on merkkijonoiksi muutettu data päiväkirja-taulusta
     */
    public ArrayList<String> getDiaryData() {
        ArrayList<String> diaryData = new ArrayList<>();
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("SELECT date, kcal, ch, prot, fat, water FROM Diary ORDER BY date");
            ResultSet r = p.executeQuery();
            while (r.next()) {
                diaryData.add(r.getString("date") + ": Kcal: " + r.getInt("kcal") * 1.0 / 10 + ", hiilihyd.: " + r.getInt("ch") * 1.0 / 10 + "g, proteiini: " + r.getInt("prot") * 1.0 / 10 + "g, rasva: " + r.getInt("fat") * 1.0 / 10 + "g, vesi: " + r.getInt("water") * 1.0 / 10 + "litraa");
            }
            this.closeConnection();
        } catch (SQLException e) {
            System.out.print("Ei voitu palauttaa päiväkirjadataa arraylistina.");
        }
        return diaryData;
    }
    
    /**
     * Metodi palauttaa parametrina annetun päivämäärän kohdalla tietokannan päiväkirja-
     * taulussa olevat numeroarvotiedot int [] -listana.
     *
     * @param date Käyttäjän antama päivämäärä
     *
     * @return int [4], jossa päiväkirjataulussa olleet numeroarvoiset tiedot
     */
    public int[] getDiaryDayData(String date) {
        int[] data = new int[4];
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("SELECT * FROM Diary WHERE date=?");
            p.setString(1, date);
            ResultSet r = p.executeQuery();
            data[0] = r.getInt("kcal");
            data[1] = r.getInt("ch");
            data[2] = r.getInt("prot");
            data[3] = r.getInt("fat");
            this.closeConnection();
            return data;
        } catch (SQLException e) {
            System.out.print("Ei saatu siirrettyä päiväkirjadataa int-listalle.");
            return data;
        }
    }
    
    /**
     * Metodi päivittää tietokannan päiväkirja-tauluun käyttäjän antaman päivämäärän
     * riville juodun veden määrän.
     *
     * @param date Käyttäjän antama päivämäärä
     * @param water tietokantaan päivitettävä uusi päivän vesimäärä, desilitraa.
     *
     * @return totuusarvo siitä onnistuivatko metodin tietokantatoiminnot
     */
    public boolean updateDiaryWater(String date, int water) {
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("UPDATE Diary SET water = ? WHERE date  = ?");
            p.setInt(1, water);
            p.setString(2, date);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Päiväkirjamerkinnän veden päivitys epäonnistui.");
            return false;
        }
    }
    
    /**
     * Metodi palauttaa int-lukuarvona tietokannan päiväkirja -taulusta päivän kohdalle
     * merkityn juodun vesimäärän.
     *
     * @param date Käyttäjän antama päivämäärä
     *
     * @return water-sarakkeeseen merkitty integer vesimäärä desilitroissa
     */
    public int getDiaryWater(String date) {
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("SELECT water FROM diary WHERE date  = ?");
            p.setString(1, date);
            ResultSet r = p.executeQuery();
            int w = r.getInt("water");
            this.closeConnection();
            return w;
        } catch (SQLException e) {
            System.out.println("Ei saatu luettua vesimäärää päiväkirjasta.");
            return -1;
        }
    }

    
    
}
