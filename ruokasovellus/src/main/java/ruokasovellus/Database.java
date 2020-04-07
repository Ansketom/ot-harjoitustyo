
package ruokasovellus;

import java.sql.*;
import java.sql.Connection;


public class Database {
    
    public Connection db;
    
    public Database() throws SQLException {
        this.db = DriverManager.getConnection("jdbc:sqlite:ruokasovellus.db");
    }
    public boolean createTables() {
        
        try {
            Statement s = db.createStatement();
            s.execute("CREATE TABLE Incredients(id INTEGER PRIMARY KEY, name TEXT UNIQUE, kcal INTEGER, ch INTEGER, prot INTEGER, fat INTEGER)");
            s.execute("CREATE TABLE Portions(id INTEGER PRIMARY KEY, name TEXT UNIQUE)");
            s.execute("CREATE TABLE DishContents(portion_id INTEGER, incredient_id INTEGER, amount INTEGER)");
            s.execute("CREATE TABLE Diary(date TEXT, ch INTEGER, prot INTEGER, fat INTEGER)");

            s.execute("PRAGMA foreign_keys= ON");
            
            return true;
        } catch (SQLException e) {
            System.out.println("VIRHE: Taulujen luominen ei onnistunut.");
            return false;
        }
    }
    public boolean dropDatabase() {
        try {
            Statement s = db.createStatement();
            s.execute("DROP DATABASE ruokasovellus.db");
            return true;
        } catch (SQLException e) {
            System.out.println("Tietokannan poisto ei onnistunut.");
            return false;
        }
    }
    public boolean dropTables() {

        try {
            Statement s = db.createStatement();
            s.execute("PRAGMA foreign_keys=OFF");
            s.execute("DROP TABLE Diary");
            s.execute("DROP TABLE DishContents");
            s.execute("DROP TABLE Incredients");
            s.execute("DROP TABLE Portions");
            s.execute("PRAGMA foiregn_keys=ON");
            

            return true;
        } catch (SQLException e) {
            System.out.println("VIRHE: Taulujen poistaminen ei onnistunut.");
            return false;
        }
    }
    public void closeConnection() {
        try {
            db.close();
        } catch (SQLException e) {
            System.out.println("Tietokantayhteyden sulkeminen ei onnistunut.");
        }
    }
    public boolean addIncredient(String name, int kcal, int ch, int prot, int fat) {
        System.out.println("Lisätään ruoka-ainetta " + name + " (" + kcal + ", " + ch + ", " + prot + ", " + fat + ").");
        try {
            PreparedStatement p = db.prepareStatement("INSERT INTO Incredients (name, kcal , ch, prot, fat) VALUES(?,?,?,?,?)");
            p.setString(1, name);
            p.setInt(2, kcal);
            p.setInt(3, ch);
            p.setInt(4, prot);
            p.setInt(5, fat);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Ruoka-aineen lisääminen epäonnistui.");
            return false;
        }
    }
    public void deleteIncredient(String name) {
        System.out.println("Poistetaan ruoka-ainetta " + name + ".");
        try {
            PreparedStatement p = db.prepareStatement("DELETE FROM Incredients WHERE name=?");
            p.setString(1, name);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ruoka-aineen poistaminen epäonnistui.");
        }
    }
    public String listIncredientsToString() {
        String list = "Ruoka-aineslista \n(Nimike: energiaa(kcal), hiilihydraattia, proteiinia, rasvaa (g/100g))";
        try {
            PreparedStatement p = db.prepareStatement("SELECT name, kcal, ch, prot, fat FROM Incredients i GROUP BY name");
            ResultSet r = p.executeQuery();
            
            while (r.next()) {
                double kcal = r.getInt("kcal");
                double hh = r.getInt("ch");
                double protein = r.getInt("prot");
                double rasva = r.getInt("fat");
                list = list + "\n" + r.getString("name") + ": " + kcal / 10 + "(kcal), h:" + hh / 10 + ", p:" + protein / 10 + ", r:" + rasva / 10;
            }
            return list;    
        } catch (SQLException e) {
            return "VIRHE: Ruoka-aineiden listaaminen epäonnistui.";
        }
    }
    public boolean addPortion(String name) {
        try {
            PreparedStatement p = db.prepareStatement("INSERT INTO Portions (name) VALUES(?)");
            p.setString(1, name);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Ruoka-annoksen lisääminen epäonnistui.");
            return false;
        }
    }
    public boolean addDishContents(int portion, int incredient, int amount) {
        try {
            PreparedStatement p = db.prepareStatement("INSERT INTO DishContents (portion_id, incredient_id, amount) VALUES(?,?,?)");
            p.setInt(1, portion);
            p.setInt(2, incredient);
            p.setInt(3, amount);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Annoksen osan lisääminen epäonnistui.");
            return false;
        }
    }
    public void deletePortion(String name) {
        System.out.println("Poistetaan ruoka-annosta " + name + ".");
        try {
            PreparedStatement d = db.prepareStatement("DELETE FROM DishContents d Where portion_id=(SELECT id FROM Portions WHERE name=?)");
            d.setString(1, name);
                    
            PreparedStatement p = db.prepareStatement("DELETE FROM Portions WHERE name=?");
            p.setString(1, name);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ruoka-aineen poistaminen epäonnistui.");
        }
    }
    public boolean addToDiary(String date, int kcal, int ch, int prot, int fat) {
        try {
            PreparedStatement p = db.prepareStatement("INSERT INTO Diary (date, kcal, ch, prot, fat) VALUES(?,?,?,?,?)");
            p.setString(1, date);
            p.setInt(2, kcal);
            p.setInt(3, ch);
            p.setInt(4, prot);
            p.setInt(5, fat);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Päiväkirjamerkinnän lisääminen epäonnistui.");
            return false;
        } 
    }


    public String getPortionNames() {
        try {
            PreparedStatement p = db.prepareStatement("SELECT * FROM Portions GROUP BY name");
            ResultSet r = p.executeQuery();
            String portions = "";
            while (r.next()) {
                portions = portions + (r.getString("name")) + " ";
            }
            return portions;
        } catch (SQLException e) {
            return "VIRHE: ei onnistuttu etsimään tauluja.";
        }
    }
    public String getPortionContents(String name) {
        String portionContent = name + ":";
        try {
            PreparedStatement p = db.prepareStatement("SELECT I.name AS incr, C.amount AS gr from Incredients I, Portions P LEFT JOIN DishContents C ON I.id=C.incredient_id WHERE P.id=C.portion_id AND P.name=? GROUP BY I.name");
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                portionContent = portionContent + " " + r.getString("incr") + "(" + r.getInt("gr") + "g)";
            }
            return portionContent;
        } catch (SQLException e) {
            return "VIRHE: ei onnistuttu etsimään annoksen sisältöä.";
        }
    }
    public String getDishContentData() {
        String data = "(annos-id, ruoka-aine -id, määrä(g):\n";
        
        try {
            PreparedStatement p = db.prepareStatement("SELECT * FROM DishContents");
            ResultSet r = p.executeQuery();
            
            while (r.next()) {
                data = data + " (" + (r.getInt("portion_id")) + " " + (r.getInt("incredient_id")) + " " + (r.getInt("amount")) + ")\n";
            }
            return data;
        } catch (SQLException e) {
            return "VIRHE: ei onnistuttu hakemaan annostensisältödataa.";
        }
    }
    public String getTableNames() {
        String tableNames = "Tables:";
        
        try {
            DatabaseMetaData md = db.getMetaData();
            ResultSet r = md.getTables(null, null, "%", null);
            while (r.next()) {
                tableNames = tableNames + " " + (r.getString(3));
            }
            return tableNames;
        } catch (SQLException e) {
            return "VIRHE: ei onnistuttu etsimään tauluja.";
        } 
    }
    public int getIncredientId(String name) {
        try {
            PreparedStatement p = db.prepareStatement("SELECT id FROM Incredients WHERE name=?");
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            return r.getInt("id");

        } catch (SQLException e) {
            return -1;
        }
    }
    public int getPortionId(String name) {
        try {
            PreparedStatement p = db.prepareStatement("SELECT id FROM Portions WHERE name=?");
            p.setString(1, name);
            
            ResultSet r = p.executeQuery();
            
            int id = r.getInt("id");
            System.out.println("checkcheck" + id);
            return id;
            
        } catch (SQLException e) {
            return -1;
        }
    }
            
}
