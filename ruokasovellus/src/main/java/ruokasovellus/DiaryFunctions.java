
package ruokasovellus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class DiaryFunctions {
    DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    Database kanta;
    DatabaseIncredients DIncredients;
    DatabasePortions DPortions;
    DatabaseDiary DDiary;

    public DiaryFunctions(Database kanta, DatabaseIncredients DI, DatabasePortions DP, DatabaseDiary DD) {
        this.kanta = kanta;
        this.DIncredients = DI;
        this.DPortions = DP;
        this.DDiary = DD;
    }
    /**
    * Metodi palauttaa tämän päivän päivämäärän merkkijonona
    * 
    * @return String jossa tämän päivän päivämäärä muodossa dd.MM.yyyy
    */
    public final String getDate() {
        LocalDate now = LocalDate.now();
        return date.format(now);
    }
    /**
    * Metodi käyttää Database -luokan metodia, joka lisää tietokannan päiväkirjatauluun
    * päivämäärän ja alustaa rivin muut arvot nolliksi.
    * 
    * @param dayValue tietokantaan lisättävä päivämäärä dd.MM.yyyy
    * 
    * @return totuusarvo siitä, menikö päivämäärän tietokantaan lisäys läpi
    */
    public boolean addDate(String dayValue) {
        return DDiary.addDateToDiary(dayValue);
    }
    /**
    * Metodi suorittaa yhteenlaskun päiväkirjatauluun päivämäärän kohdalle merkityistä
    * arvoista ja käyttäjän osoittamasta ateriasta, ja päivittää summat
    * päiväkirjataulun päivämäärän riville
    * 
    * @param dayValue päivämäärä dd.MM.yyyy
    * @param meal Summattava ruoka-annos
    * 
    * @return totuusarvo siitä, menikö summan tuloksen lisäys tietokantaan läpi
    */
    public boolean addMeal(String dayValue, String meal) {
        int [] daydata = DDiary.getDiaryDayData(dayValue);
        int kcal = daydata [0];
        int ch = daydata [1];
        int prot = daydata [2];
        int fat = daydata [3];
        
        ArrayList<String> mealdata = DPortions.getPortionContentsInList(meal);
        for (String row : mealdata) {
            String [] parts = row.split(":");
            int grams = Integer.valueOf(row.replaceAll("\\D+", ""));
            int [] mealvalues = DIncredients.getIncredientDataInInt(parts[0]);
            kcal += (mealvalues[0] * (1.0 * grams / 100));
            ch += (mealvalues[1] * (1.0 * grams / 100));
            prot += (mealvalues[2] * (1.0 * grams / 100));
            fat += (mealvalues[3] * (1.0 * grams / 100));
        }
        return DDiary.updateDiary(dayValue, kcal, ch, prot, fat);
    }
    /**
    * Metodi suorittaa vähennyslaskun päiväkirjatauluun päivämäärän kohdalle merkityistä
    * arvoista ja käyttäjän osoittamasta ateriasta, ja jos erotukset ovat positiivisia, 
    * niin päivittää ne päiväkirjataulun päivämäärän riville.
    * 
    * @param dayValue päivämäärä dd.MM.yyyy
    * @param meal Vähennettävä ruoka-annos
    * 
    * @return totuusarvo siitä, menikö vähennyslaskun tuloksen lisäys tietokantaan läpi
    */
    public boolean substractMeal(String dayValue, String meal) {
        int [] daydata = DDiary.getDiaryDayData(dayValue);
        int kcal = daydata [0];
        int ch = daydata [1];
        int prot = daydata [2];
        int fat = daydata [3];
        
        ArrayList<String> mealdata = DPortions.getPortionContentsInList(meal);
        for (String row : mealdata) {
            String [] parts = row.split(":");
            int grams = Integer.valueOf(row.replaceAll("\\D+", ""));
            int [] mealvalues = DIncredients.getIncredientDataInInt(parts[0]);
            kcal -= (mealvalues[0] * (1.0 * grams / 100));
            ch -= (mealvalues[1] * (1.0 * grams / 100));
            prot -= (mealvalues[2] * (1.0 * grams / 100));
            fat -= (mealvalues[3] * (1.0 * grams / 100));
        }
        if (kcal < 0 || ch < 0 || prot < 0 || fat < 0){
            return false;
        }
        return DDiary.updateDiary(dayValue, kcal, ch, prot, fat);
        }
    /**
    * Metodi hakee päivämäärän kohdalle merkityn vesimäärän 
    * tietokannan päiväkirjataulusta tietokantaa käyttävän luokan metodin avulla
    * 
    * @param dayValue päivämäärä dd.MM.yyyy
    * 
    * @return päivämäärän kohdalla päiväkirjataulussa ollut vesimäärä desilitroina
    */
    public int getWater (String dayValue) {
        return DDiary.getDiaryWater(dayValue);
    }
    /**
    * Metodi käyttää tietokantaa käyttävän luokan metodeita hakeakseen päivämäärän
    * kohdalle merkityn vesimäärän, summaa siihen halutun vesimäärän, ja jos tulos
    * on positiivinen niin päivittää sen tietokannan päiväkirjatauluun.
    * 
    * @param dayValue päivämäärä dd.MM.yyyy
    * @param desiliters summattava vesimäärä desilitroina
    * 
    * @return totuusarvo siitä saiko metodi lopuksi päivitettyä uuden arvon tietokantaan
    */
    public boolean addWater (String dayValue, int desiliters) {
        int waterAmount = this.getWater(dayValue);
        if((desiliters + waterAmount) > 0) {
            waterAmount += desiliters;
        }
        return this.updateWater(dayValue, waterAmount);
    }
    /**
    * Metodi käyttää tietokantaa käyttävän luokan metodia päivittääkseen parametrina
    * annetun vesimäärän parametrina annetun päivämäärän kohdalle tietokannan
    * päiväkirjatauluun
    * 
    * @param dayValue päivämäärä dd.MM.yyyy
    * @param waterAmount vesimäärä desilitroina
    * 
    * @return totuusarvo siitä saiko metodi lopuksi päivitettyä uuden arvon tietokantaan
    */
    public boolean updateWater (String dayValue, int waterAmount) {
        return DDiary.updateDiaryWater(dayValue, waterAmount);
    }
    /**
    * Metodi käyttää tietokantaa käyttävän luokan metodia hakeakseen päiväkirjan sisällön
    * ArrayListina, ja sitten muuttaa sen rivitetyksi merkkijonoksi.
    * 
    * @return merkkijono, jossa on koko päiväkirjan sisältö rivitettynä
    */
    public String diaryToString() {
        ArrayList<String> diaryData = DDiary.getDiaryData();
        String dataString = "";
        for (String row : diaryData) {
            dataString += row + "\n";
        }
        return dataString;
    }
    
}
