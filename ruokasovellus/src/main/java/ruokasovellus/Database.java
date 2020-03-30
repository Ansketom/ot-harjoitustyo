
package ruokasovellus;

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
    public void dropTables(){
        System.out.println("Poistetaan tauluja.");
        try{
            Statement s=db.createStatement();
            s.execute("PRAGMA foreign_keys=OFF");
            s.execute("DROP TABLE Incredients");
            s.execute("PRAGMA foiregn_keys=ON");
        }catch(SQLException e){
            System.out.println("VIRHE: Taulujen poistaminen ei onnistunut.");
        }
    }
    public boolean addIncredient(String name, int ch, int prot, int fat){
        System.out.println("Lisätään ruoka-ainetta "+name+ " ("+ch+", "+prot+", "+fat+").");
        try{
            PreparedStatement p = db.prepareStatement("INSERT INTO Incredients (name, ch, prot, fat) VALUES(?,?,?,?)");
            p.setString(1, name);
            p.setInt(2, ch);
            p.setInt(3, prot);
            p.setInt(4, fat);
            p.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Ruoka-aineen lisääminen epäonnistui.");
            return false;
        }
    }
    public void deleteIncredient(String name){
        System.out.println("Poistetaan ruoka-ainetta "+name+".");
        try{
        PreparedStatement p = db.prepareStatement("DELETE FROM Incredients WHERE name=?");
            p.setString(1, name);
            p.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ruoka-aineen poistaminen epäonnistui.");
        }
    }
    public String listIncredientsToString(){
        String list= "Ruoka-aineslista \n(Nimike: hiilihydraattia, proteiinia, rasvaa (g/100g))";
        try{
            PreparedStatement p =db.prepareStatement ("SELECT name, ch, prot, fat FROM Incredients i GROUP BY name");
            ResultSet r= p.executeQuery();
            
            while (r.next()){
                double hh= r.getInt("ch");
                double protein= r.getInt("prot");
                double rasva= r.getInt("fat");
                list=list+"\n"+r.getString("name")+": h:"+hh/10+", p:"+protein/10+", r:"+rasva/10;
                
            }
            return list;    
        }catch(SQLException e){
            return "VIRHE: Ruoka-aineiden listaaminen epäonnistui.";
        }
        
    }

    public void closeConnection(){
        try{
            db.close();
        }catch(SQLException e){
            System.out.println("Tietokantayhteyden sulkeminen ei onnistunut.");
        }
    }

    public String getTableNames(){
        String tableNames= "Tables:";
        
        try{
        DatabaseMetaData md=db.getMetaData();
        ResultSet r =md.getTables(null, null, "%", null);
        while(r.next()){
            tableNames= tableNames +" "+(r.getString(3));
        }
        return tableNames;
        }catch(SQLException e){
            return "VIRHE: ei onnistuttu etsimään tauluja.";
        }
        
        
    }
}
