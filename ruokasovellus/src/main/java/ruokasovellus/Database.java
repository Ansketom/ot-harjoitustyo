
package ruokasovellus;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;


public class Database {
   
    public Connection db;
    
    
    public Database() throws SQLException {
        
    }
    public boolean createTables() {
        try {
            this.openConnection();
            Statement s = db.createStatement();
            s.execute("CREATE TABLE Incredients(id INTEGER PRIMARY KEY, name TEXT UNIQUE, kcal INTEGER, ch INTEGER, prot INTEGER, fat INTEGER)");
            s.execute("CREATE TABLE Portions(id INTEGER PRIMARY KEY, name TEXT UNIQUE)");
            s.execute("CREATE TABLE DishContents(portion_id INTEGER, incredient_id INTEGER, amount INTEGER)");
            s.execute("CREATE TABLE Diary(date TEXT UNIQUE, kcal INTEGER, ch INTEGER, prot INTEGER, fat INTEGER, water INTEGER)");

            s.execute("PRAGMA foreign_keys= ON");
            
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("VIRHE: Taulujen luominen ei onnistunut.");
            return false;
        }
    }
    public boolean dropTables() {

        try {
            this.openConnection();
            Statement s = db.createStatement();
            s.execute("PRAGMA foreign_keys=OFF");
            s.execute("DROP TABLE Diary");
            s.execute("DROP TABLE DishContents");
            s.execute("DROP TABLE Incredients");
            s.execute("DROP TABLE Portions");
            s.execute("PRAGMA foiregn_keys=ON");
            
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("VIRHE: Taulujen poistaminen ei onnistunut.");
            return false;
        }
    }
    public void closeConnection() throws SQLException {
        try {
            this.db.close();
        } catch (SQLException e) {
            System.out.println("yhteyden sulku epäonnistui");
        }
    }
    public void openConnection() throws SQLException {
        try {
            this.db = DriverManager.getConnection("jdbc:sqlite:ruokasovellus.db");
        } catch (SQLException e) {
            System.out.println("yhteyden avaus epäonnistui");
        }
    }

    public boolean addIncredient(String name, int kcal, int ch, int prot, int fat) {
        
        System.out.println("Lisätään ruoka-ainetta " + name + " (" + kcal + ", " + ch + ", " + prot + ", " + fat + ").");
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
    public void deleteIncredient(String name) {
        System.out.println("Poistetaan ruoka-ainetta " + name + ".");
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("DELETE FROM Incredients WHERE name=?");
            p.setString(1, name);
            p.executeUpdate();
            this.closeConnection();
        } catch (SQLException e) {
            System.out.println("Ruoka-aineen poistaminen epäonnistui.");
        }
    }
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

    public boolean addPortion(String name) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("INSERT INTO Portions (name) VALUES(?)");
            p.setString(1, name);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Ruoka-annoksen lisääminen epäonnistui.");
            return false;
        }
    }
    public boolean deletePortion(String name) {
        System.out.println("Poistetaan ruoka-annosta " + name + ".");
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("DELETE FROM Portions WHERE name=?");
            p.setString(1, name);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Ruoka-annoksen poistaminen epäonnistui.");
            return false;
        }
    }
    public boolean addDishContents(int portion, int incredient, int amount) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("INSERT INTO DishContents (portion_id, incredient_id, amount) VALUES(?,?,?)");
            p.setInt(1, portion);
            p.setInt(2, incredient);
            p.setInt(3, amount);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Annoksen osan lisääminen epäonnistui.");
            return false;
        }
    }
    public boolean deletePortionPart(String portion, String incredient) {
        
        System.out.println("Poistetaan ruoka-annoksesta" + portion + " ruoka-ainetta " + incredient);
        int portionId = this.getPortionId(portion);
        int incredientId = this.getIncredientId(incredient);
        System.out.println("annosid " + portionId + " aineid :" + incredientId);
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("DELETE FROM DishContents WHERE portion_id=? AND incredient_id=?");
            p.setInt(1, portionId);
            p.setInt(2, incredientId);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Ruoka-aineen poistaminen epäonnistui.");
            return false;
        }
    }
    
    public boolean addDateToDiary(String date) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("INSERT INTO Diary (date, kcal, ch, prot, fat, water) VALUES(?,?,?,?,?,?)");
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
    public boolean deleteDateFromDiary(String date) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("DELETE FROM Diary WHERE date=?");
            p.setString(1, date);
            p.executeUpdate();
            this.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Päivämäärän poistaminen epäonnistui.");
            return false;
        }
    }
    public boolean updateDiary(String date, int kcal, int ch, int prot, int fat) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("UPDATE Diary SET kcal = ?, ch = ?, prot = ?, fat = ? WHERE date  = ?");
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
    
    public boolean updateDiaryWater(String date, int water) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("UPDATE Diary SET water = ? WHERE date  = ?");
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
    public int getDiaryWater(String date) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT water FROM diary WHERE date  = ?");
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
    public int getPortionId(String name) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT id FROM Portions WHERE name=?");
            p.setString(1, name);
            
            ResultSet r = p.executeQuery();
            
            int id = r.getInt("id");
            System.out.println("checkcheck" + id);
            this.closeConnection();
            return id;
            
        } catch (SQLException e) {
            return -1;
        }
    }
    public String getPortionNames() {
        
        try {
            this.openConnection();
            String portions = "";
            PreparedStatement p = db.prepareStatement("SELECT * FROM Portions ORDER BY name");
            ResultSet r = p.executeQuery();
            while (r.next()) {
                portions = portions + r.getString("name") + "\n";
            }
            this.closeConnection();
            return portions;
        } catch (SQLException e) {
            return "VIRHE: ei onnistuttu etsimään tauluja.";
        }
    }
    
    public String getPortionContentsWithName(String name) {
        
        String portionContent = name + ":";
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT I.name AS incr, C.amount AS gr from Incredients I, Portions P LEFT JOIN DishContents C ON I.id=C.incredient_id WHERE P.id=C.portion_id AND P.name=? GROUP BY I.name");
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                portionContent = portionContent + " " + r.getString("incr") + "(" + r.getInt("gr") + "g)";
            }
            this.closeConnection();
            return portionContent;
        } catch (SQLException e) {
            return "VIRHE: ei onnistuttu etsimään annoksen sisältöä.";
        }
    }
    public ArrayList<String> getPortionContentsInList(String name) {
        
        ArrayList<String> portionParts = new ArrayList<>();
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT I.name AS incr, C.amount AS gr from Incredients I, Portions P LEFT JOIN DishContents C ON I.id=C.incredient_id WHERE P.id=C.portion_id AND P.name=? GROUP BY I.name");
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                String parts = r.getString("incr") + ":" + r.getInt("gr") + "g";
                portionParts.add(parts);
            }
            this.closeConnection();
        } catch (SQLException e) {
            System.out.println("Ei onnistuttu luomaan palautettavaa taulua annoksen osista");
        }
        return portionParts;
    }
    public String getDishContentToString() {
        
        String data = "annos, ruoka-aine , määrä(g):\n";
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT P.name AS pname, I.name AS iname, D.amount AS damount FROM DishContents D, Incredients I, Portions P WHERE I.id=D.incredient_id AND P.id=D.portion_id;");
            ResultSet r = p.executeQuery();
            while (r.next()) {
                data = data + "" + (r.getString("pname")) + " " + (r.getString("iname")) + " " + (r.getInt("damount")) + "\n";
            }
            this.closeConnection();
            return data;
        } catch (SQLException e) {
            return "VIRHE: ei onnistuttu hakemaan annostensisältödataa.";
        }
    }
    
    public int getIncredientId(String name) {
        
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT id FROM Incredients WHERE name=?");
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            int result = r.getInt("id");
            this.closeConnection();
            return result;
        } catch (SQLException e) {
            return -1;
        }
    }
    
    public int[] getIncredientDataInInt(String name) {
        int [] data = new int[4];
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT * FROM Incredients WHERE name=?");
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            data[0] = r.getInt("kcal");
            data[1] = r.getInt("ch");
            data[2] = r.getInt("prot");
            data[3] = r.getInt("fat");
            this.closeConnection();
        } catch (SQLException e) {
            System.out.println("Ei voitu palauttaa päiväkirjadataa int-listana");
        }
        return data;
    }         
    public int[] getDiaryDayData(String date) {
        int [] data = new int[4];
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT * FROM Diary WHERE date=?");
            p.setString(1, date);
            ResultSet r = p.executeQuery();
            data[0] = r.getInt("kcal");
            data[1] = r.getInt("ch");
            data[2] = r.getInt("prot");
            data[3] = r.getInt("fat");
            this.closeConnection();
            return data;
        } catch (SQLException e) {
            System.out.println("Ei saatu siirrettyä päiväkirjadataa int-listalle");
            return data;
        }
    }         
    public ArrayList<String> getDiaryData() {
        
        ArrayList<String> diaryData = new ArrayList<>();
        try {
            this.openConnection();
            PreparedStatement p = db.prepareStatement("SELECT date, kcal, ch, prot, fat, water FROM Diary ORDER BY date");
            ResultSet r = p.executeQuery();
            while (r.next()) {
                diaryData.add(r.getString("date") + ": Kcal: " + r.getInt("kcal") * 1.0 / 10 + ", hiilihyd.: " + r.getInt("ch") * 1.0 / 10 + "g, proteiini: " + r.getInt("prot") * 1.0 / 10 + "g, rasva: " + r.getInt("fat") * 1.0 / 10 + "g, vesi: " + r.getInt("water") * 1.0 / 10 + "litraa");
            }
            this.closeConnection();
        } catch (SQLException e) {
            System.out.println("Ei voitu palauttaa päiväkirjadataa arraylistina");
        }
        
        return diaryData;
    }         
}
