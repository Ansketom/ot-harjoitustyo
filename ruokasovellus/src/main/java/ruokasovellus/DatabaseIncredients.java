/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruokasovellus;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author AnssiKetomäki
 */
public class DatabaseIncredients {
    
    Database kanta;
    Connection db;

    public DatabaseIncredients (Database kanta) throws SQLException {
        this.kanta = kanta;
        this.db = kanta.db;
        
    }
    /**
    * Metodi sulkee ohjelman yhteyden tietokantaan.
    */
    public void closeConnection() {
        try {
            db.close();
        } catch (SQLException e) {
            System.out.println("yhteyden sulku epäonnistui.");
        }
    }
    /**
    * Metodi avaa ohjelmalle yhteyden tietokantaan.
    */
    public void openConnection() {
        try {
            db = DriverManager.getConnection("jdbc:sqlite:ruokasovellus.db");
        } catch (SQLException e) {
            System.out.println("yhteyden avaus epäonnistui.");
        }
    }

    /**
    * Metodi lisää tietokannan ruoka-aine -tauluun ruoka-aineen.
    * 
    * @param name käyttäjän antama ruoka-aineen nimi
    * @param kcal käyttäjän antama energiamäärätieto ruoka-aineesta
    * @param ch käyttäjän antama hiilihydraattimäärätieto ruoka-aineesta
    * @param prot käyttäjän antama proteiinimäärätieto ruoka-aineesta
    * @param fat käyttäjän antama rasvamäärätieto ruoka-aineesta
    * 
    * @return totuusarvo siitä onnistuivatko metodin tietokantatoiminnot
    */
    public boolean addIncredient(String name, int kcal, int ch, int prot, int fat) {
        //System.out.println("Lisätään ruoka-ainetta " + name + " (" + kcal + ", " + ch + ", " + prot + ", " + fat + ").");
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("INSERT INTO Incredients (name, kcal , ch, prot, fat) VALUES(?,?,?,?,?)");
            p.setString(1, name);
            p.setInt(2, kcal);
            p.setInt(3, ch);
            p.setInt(4, prot);
            p.setInt(5, fat);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Ruoka-aineen lisääminen epäonnistui.");
            return false;
        }
    }
    /**
    * Metodi poistaa tietokannasta annetunnimisen ruoka-aineen
    * 
    * @param name Käyttäjän antama ruoka-aineen nimi 
    * 
    * @return totuusarvo siitä onnistuivatko metodin tietokantatoiminnot
    */
    public boolean deleteIncredient(String name) {
        //System.out.println("Poistetaan ruoka-ainetta " + name + ".");
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("DELETE FROM Incredients WHERE name=?");
            p.setString(1, name);
            p.executeUpdate();
            this.closeConnection();
        } catch (SQLException e) {
            System.out.println("Ruoka-aineen poistaminen epäonnistui.");
            return false;
        }
        return true;
    }
    /**
    * Metodi listaa tietokantaan lisätyt ruoka-aineet Stringiksi riveittäin
    * otsikkorivin ja seliterivin alle
    *  
    * @return String, jossa otsikkorivin ja seliterivin alla tietokantaan lisätyt ruoka-
    * aineet riveittäin tietoineen
    */
    public String listIncredientsToString() {
        String list = "Ruoka-aineslista \n(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))";
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT name, kcal, ch, prot, fat FROM Incredients i GROUP BY name");
            ResultSet r = p.executeQuery();
            
            while (r.next()) {
                double kcal = r.getInt("kcal");
                double hh = r.getInt("ch");
                double protein = r.getInt("prot");
                double rasva = r.getInt("fat");
                list = list + "\n" + r.getString("name") + ": " + kcal / 10 + "(kcal), h:" + hh / 10 + ", p:" + protein / 10 + ", r:" + rasva / 10;
            }
            this.closeConnection();
            return list;    
        } catch (SQLException e) {
            return "VIRHE: Ruoka-aineiden listaaminen epäonnistui.";
        }
    }

    /**
     * Metodi palauttaa sille parametrina annettua ruoka-aineen nimeä vastaavan
     * id:n
     *
     * @param name käyttäjän antama ruoka-aineen nimi
     *
     * @return Parametrinimeä vastaava ruoka-aineen id -numero
     */
    public int getIncredientId(String name) {
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("SELECT id FROM Incredients WHERE name=?");
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            int result = r.getInt("id");
            this.closeConnection();
            return result;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Metodi palauttaa parametrina annetun ruoka-aineen integer -lukuarvoiset tiedot
     * int -listana
     *
     * @param name Käyttäjän antama ruoka-aineen nimi
     *
     * @return int -lista, jossa parametrina annetun ruoka-aineen makroravinne- ja energiamäärätiedot
     */
    public int[] getIncredientDataInInt(String name) {
        int[] data = new int[4];
        try {
            this.openConnection();
            PreparedStatement p = this.db.prepareStatement("SELECT * FROM Incredients WHERE name=?");
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            data[0] = r.getInt("kcal");
            data[1] = r.getInt("ch");
            data[2] = r.getInt("prot");
            data[3] = r.getInt("fat");
            this.closeConnection();
        } catch (SQLException e) {
            System.out.print("Ei voitu palauttaa päiväkirjadataa int-listana.");
        }
        return data;
    }
}
