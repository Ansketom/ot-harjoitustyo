
package ruokasovellus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class DiaryFunctions {
    DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    Database kanta;

    public DiaryFunctions(Database kanta) {
        this.kanta = kanta;
    }
    public final String getDate() {
        LocalDate now = LocalDate.now();
        return date.format(now);
    }
    public boolean addDate(Database kanta, String dayValue) {
        return kanta.addDateToDiary(dayValue);
    }
    public boolean addMeal(Database kanta, String dayValue, String meal) {
        int [] daydata = kanta.getDiaryDayData(dayValue);
        int kcal = daydata [0];
        int ch = daydata [1];
        int prot = daydata [2];
        int fat = daydata [3];
        
        ArrayList<String> mealdata = kanta.getPortionContentsInList(meal);
        for (String row : mealdata) {
            String [] parts = row.split(":");
            int grams = Integer.valueOf(row.replaceAll("\\D+", ""));
            int [] mealvalues = kanta.getIncredientDataInInt(parts[0]);
            kcal += (mealvalues[0] * (1.0 * grams / 100));
            ch += (mealvalues[1] * (1.0 * grams / 100));
            prot += (mealvalues[2] * (1.0 * grams / 100));
            fat += (mealvalues[3] * (1.0 * grams / 100));
        }
        return kanta.updateDiary(dayValue, kcal, ch, prot, fat);
    }
    public boolean substractMeal(Database kanta, String dayValue, String meal) {
        int [] daydata = kanta.getDiaryDayData(dayValue);
        int kcal = daydata [0];
        int ch = daydata [1];
        int prot = daydata [2];
        int fat = daydata [3];
        
        ArrayList<String> mealdata = kanta.getPortionContentsInList(meal);
        for (String row : mealdata) {
            String [] parts = row.split(":");
            int grams = Integer.valueOf(row.replaceAll("\\D+", ""));
            int [] mealvalues = kanta.getIncredientDataInInt(parts[0]);
            kcal -= (mealvalues[0] * (1.0 * grams / 100));
            ch -= (mealvalues[1] * (1.0 * grams / 100));
            prot -= (mealvalues[2] * (1.0 * grams / 100));
            fat -= (mealvalues[3] * (1.0 * grams / 100));
        }
        if (kcal > 0 && ch > 0 && prot > 0 && fat > 0){
            return kanta.updateDiary(dayValue, kcal, ch, prot, fat);
        } else {
            return false;
        }
    }
    public int getWater (Database kanta, String dayvalue) {
        return kanta.getDiaryWater(dayvalue);
    }
    public boolean addWater (Database kanta, String dayvalue, int desiliters) {
        int waterAmount = this.getWater(kanta, dayvalue);
        if(desiliters > waterAmount) {
            waterAmount += desiliters;
        }
        return this.updateWater(kanta, dayvalue, waterAmount);
    }
    public boolean updateWater (Database kanta, String dayvalue, int waterAmount) {
        return kanta.updateDiaryWater(dayvalue, waterAmount);
    }
    

    public String diaryToString() {
        ArrayList<String> diaryData = kanta.getDiaryData();
        String dataString = "";
        for (String row : diaryData) {
            dataString += row + "\n";
        }
        return dataString;
    }
    
}
