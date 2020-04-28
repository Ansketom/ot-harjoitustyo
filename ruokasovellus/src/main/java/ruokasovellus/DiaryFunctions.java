
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
    public void addDate(Database kanta, String dayValue) {
        kanta.addDateToDiary(dayValue);
    }
    public void addMeal(Database kanta, String dayValue, String meal) {
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
        kanta.updateDiary(dayValue, kcal, ch, prot, fat);
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
