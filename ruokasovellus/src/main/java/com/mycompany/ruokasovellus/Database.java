
package com.mycompany.ruokasovellus;

import java.sql.*;
import java.sql.Connection;


public class Database {
    
    public Connection db;
    
    public Database() throws SQLException{
        this.db= DriverManager.getConnection("jdbc:sqlite:ruokasovellus.db");
    }
    public void createTables(){
        System.out.println("Luodaan tauluja.");
        try{
            Statement s=db.createStatement();
            s.execute("CREATE TABLE Incredients(name TEXT UNIQUE, ch INTEGER, prot INTEGER, fat INTEGER)");

            s.execute("PRAGMA foreign_keys= ON");
        }catch (SQLException e){
            System.out.println("VIRHE: Taulujen luominen ei onnistunut.");
        }
    }
    public void addIncredient(String name, int ch, int prot, int fat){
        System.out.println("Lisätään ruoka-ainetta "+name+ " ("+ch+", "+prot+", "+fat+")");
        try{
            PreparedStatement p = db.prepareStatement("INSERT INTO Incredients (name, ch, prot, fat) VALUES(?,?,?,?)");
            p.setString(1, name);
            p.setInt(2, ch);
            p.setInt(3, prot);
            p.setInt(4, fat);
            p.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ruoka-aineen lisääminen epäonnistui.");
        }
    }
    public void listIncredients(){
        try{
            PreparedStatement p =db.prepareStatement ("SELECT name, ch, prot, fat FROM Incredients i GROUP BY name");
            ResultSet r= p.executeQuery();
            System.out.println("Ruoka-aineslista (Nimike: hiilihydraattia, proteiinia, rasvaa (g/100g))");
            while (r.next()){
                double hh= r.getInt("ch")/10;
                double protein= r.getInt("prot")/10;
                double rasva= r.getInt("fat")/10;
                System.out.println(r.getString("name")+": h:"+hh+", p:"+protein+", r:"+rasva);
            }
                    
        }catch(SQLException e){
            System.out.println("VIRHE: Ruoka-aineiden listaaminen epäonnistui");
        }
    }
    

/*
    public void closeConnection(){
        try{
            db.close();
        }catch(SQLException e){
            System.out.println("Tietokantayhteyden sulkeminen ei onnistunut");
        }
    }
*/
    public String getTableNames(){
        String tableNames= "Tables:";
        
        try{
        DatabaseMetaData md=db.getMetaData();
        ResultSet r =md.getTables(null, null, "%", null);
        while(r.next()){
            tableNames= tableNames +" "+(r.getString(3));
        }
        }catch(SQLException e){
            System.out.println("VIRHE: ei onnistuttu etsimään tauluja");
        }
        return tableNames;
        
    }
}
