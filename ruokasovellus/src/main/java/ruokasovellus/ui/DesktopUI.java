
package ruokasovellus.ui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import static javafx.scene.paint.Color.*;


import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.application.Application;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import ruokasovellus.Database;
import ruokasovellus.DatabaseIncredients;
import ruokasovellus.DatabasePortions;
import ruokasovellus.DatabaseDiary;
import ruokasovellus.DiaryFunctions;



/**
 * Desktop-käyttöliittymän ruokasovellukseen luova luokka.
 * 
 * @author AnssiKetomäki
 */
public class DesktopUI extends Application {
    
    // private Scene view;

    private Database kanta;
    private DatabaseIncredients dIncredients;
    private DatabasePortions dPortions;
    private DatabaseDiary dDiary;
    private DiaryFunctions diary;
    private TableView table = new TableView();
    
    private ObservableList<String> diaryList = FXCollections.observableArrayList();
    private ObservableList<String> dMealList = FXCollections.observableArrayList();
    private ObservableList<String> pMealList = FXCollections.observableArrayList();
    private ObservableList<String> incrList = FXCollections.observableArrayList();
 
    
    
    @Override
    public void start(Stage window) throws Exception, SQLException {
        Database kanta = new Database();
        kanta.createTables();
        DatabaseIncredients dIncredients = new DatabaseIncredients(kanta);
        DatabasePortions dPortions = new DatabasePortions(kanta, dIncredients);
        DatabaseDiary dDiary = new DatabaseDiary(kanta, dPortions);
        DiaryFunctions diary = new DiaryFunctions(kanta, dIncredients, dPortions, dDiary);
        
        BorderPane layout = new BorderPane();
        //sivun alalaidassa olevat napit jolla voi vaihtaa näkymää
        HBox pageSwitcher = new HBox();
        Button first = new Button("Ruoka-aineet");
        Button second = new Button("Ruoka-annokset");
        Button third = new Button("Päiväkirja");
        
        pageSwitcher.getChildren().addAll(first, second, third);
        pageSwitcher.setPadding(new Insets(5, 20, 20, 20));
        pageSwitcher.setSpacing(10);
        first.setStyle("-fx-background-color: LIGHTGRAY;");
        second.setStyle("-fx-background-color: LIGHTGRAY;");
        third.setStyle("-fx-background-color: LIGHTSALMON;");
        
        
        // Ruoka-ainesivu
        VBox incredientButtons = new VBox();
        VBox incredientFields = new VBox();
        VBox incredientLabels = new VBox();
        HBox incredientManager = new HBox();
        VBox incredientPage = new VBox();
        
        incredientButtons.setPadding(new Insets(5, 10, 5, 10));
        incredientButtons.setSpacing(4);
        incredientLabels.setPadding(new Insets(5, 10, 5, 10));
        incredientLabels.setSpacing(8);
        incredientLabels.setAlignment(Pos.TOP_RIGHT);
        
        
        Button addIncr = new Button("Lisää");
        Button deleteIncr = new Button("Poista");
        Label incrTip = new Label("Syötä numeroarvot yhden \ndesimaalin kera! esim 13,3");
        Button refreshIncredientsList = new Button("Päivitä ruoka-aineluettelo");
        
        TextField incredientInput = new TextField();
        TextField kcalInput = new TextField();
        TextField chInput = new TextField();
        TextField protInput = new TextField();
        TextField fatInput = new TextField();
        
        Label incrName = new Label("Ruoka-aine:");
        Label incrkcal = new Label("Kcal/100g:");
        Label incrch = new Label("Hiilihydraatti/100g:");
        Label incrprot = new Label("Proteiini/100g:");
        Label incrfat = new Label("Rasva/100g:");
        
        Label incredientsPageErrorMessage = new Label("");
        incredientsPageErrorMessage.setTextFill(RED);
        incredientsPageErrorMessage.setPadding(new Insets(0, 0, 0, 10));
        ListView<String> dIncrList = new ListView<>(incrList);
        dIncrList.prefHeight(400);
        dIncrList.prefWidth(400);
        dIncrList.setPadding(new Insets(5, 10, 5, 10));
        
        incrList.addAll(dIncredients.listIncredientsArrayList());
        
        
        
        //ruoka-ainesivun toiminnallisuus
        
        //ruoka-ainelistauksen päivitys
        
        refreshIncredientsList.setOnAction((ActionEvent e)-> {
            incrList.clear();
            incrList.addAll(dIncredients.listIncredientsArrayList());
        });
        //ruoka-aineen lisäys
        addIncr.setOnAction((ActionEvent e)-> {
            incredientsPageErrorMessage.setText("");

            String incredName = incredientInput.getText();
            String energy = kcalInput.getText();
            String carbohyd = chInput.getText();
            String protein = protInput.getText();
            String fats = fatInput.getText();

            energy = energy.replaceAll("\\D+", "");
            carbohyd = carbohyd.replaceAll("\\D+", "");
            protein = protein.replaceAll("\\D+", "");
            fats = fats.replaceAll("\\D+", "");
            
            if (energy.equals("")) {
                energy = "0";
            }
            if (carbohyd.equals("")) {
                carbohyd = "0";
            }
            if (protein.equals("")) {
                protein = "0";
            }
            if (fats.equals("")) {
                fats = "0";
            }
            
            int ene = Integer.valueOf(energy);
            int ch = Integer.valueOf(carbohyd);
            int prot = Integer.valueOf(protein);
            int fat = Integer.valueOf(fats);
            

            boolean iadd = dIncredients.addIncredient(incredName, ene, ch, prot, fat);
            incredientsPageErrorMessage.setText("Lisäys onnistui: " + iadd);
            incrList.clear();
            incrList.addAll(dIncredients.listIncredientsArrayList());
            
        });
        //Ruoka-aineen nollaus
        deleteIncr.setOnAction((ActionEvent e)-> {
            incredientsPageErrorMessage.setText("Nollataksesi ruoka-aineen jota ei enää ole sidottu annokseen, syötä Kcal -riville arvo: -1");
            if (kcalInput.getText().equals("-1")) {
                dIncredients.deleteIncredient(incredientInput.getText());
                incrList.clear();
                incrList.addAll(dIncredients.listIncredientsArrayList());
                incredientsPageErrorMessage.setText("");
            }
        });
        
        incredientButtons.getChildren().addAll(addIncr, deleteIncr, incrTip, refreshIncredientsList);
        incredientFields.getChildren().addAll(incredientInput, kcalInput, chInput, protInput, fatInput);
        incredientLabels.getChildren().addAll(incrName, incrkcal, incrch, incrprot, incrfat);
        
                
        incredientManager.getChildren().addAll(incredientLabels, incredientFields, incredientButtons);
        incredientPage.getChildren().addAll(incredientManager, incredientsPageErrorMessage, dIncrList);
        
        
        layout.setBottom(pageSwitcher);
        
        // Ruoka-annossivu
        //Layout elements
        VBox mealButtons = new VBox();
        VBox mealInput = new VBox();
        HBox mealManager = new HBox();
        Label mealList = new Label("");
        HBox searchOptions = new HBox();
        VBox mealManagePage = new VBox();
        
        mealButtons.setPadding(new Insets(17, 10, 5, 10));
        mealButtons.setSpacing(2);
        mealInput.setPadding(new Insets(5, 10, 5, 0));
        mealInput.setSpacing(2);
        mealManager.setPadding(new Insets(5, 10, 5, 0));
        mealList.setPrefSize(400, 600);
        searchOptions.setPadding(new Insets(5, 10, 5, 0));
        mealManagePage.setPadding(new Insets(5, 10, 5, 10));
        
        //Control elements
        Label mealNameLabel = new Label("Ruoka-annoksen nimi:");
        TextField mealNameInput = new TextField();
        Label mealPartLabel = new Label("Ruoka-aine:");
        TextField mealPartField = new TextField();
        Label mealPartAmountLabel = new Label("Ruoka-aineen määrä (g):");
        TextField mealPartAmountField = new TextField();
        
        
        Button addMeal = new Button("Lisää uusi ruoka-annos (nimikenttä)");
        Button addIncrToMeal = new Button("Lisää ruoka-aine annokseen(nimi, aine, määrä)");
        Button deleteFromMeal = new Button("Poista ruoka-aine annoksesta(nimi, aine)");
        
        
        Button listAllMeals = new Button("Aterioiden sisältö");
        Button listMealNames = new Button("Lisättyjen aterioiden nimet");
        Button listMealContents = new Button("Näytä annoksen sisältö (nimikenttä)");
        
        Button bringIncredientsList = new Button("Ruoka-aineluettelo");
        Label mealPageErrorMessage = new Label("");
        mealPageErrorMessage.setTextFill(RED);
        
        ListView<String> portionList = new ListView<>(pMealList);
        pMealList.addAll(dPortions.getDishContentArrayList());
        
        //Actions
        addMeal.setOnAction((ActionEvent e) -> {
            dPortions.addPortion(mealNameInput.getText());
        });
        addIncrToMeal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mealPageErrorMessage.setText("");
                try {
                    String portName = mealNameInput.getText();
                    int portNameInt = dPortions.getPortionId(portName);
                    String portIncrName = mealPartField.getText();
                    int portIncrNameInt = dIncredients.getIncredientId(portIncrName);
                    int portAmount = Integer.valueOf(mealPartAmountField.getText());
                    dPortions.addDishContents(portNameInt, portIncrNameInt, portAmount);
                } catch (NumberFormatException n) {
                    mealPageErrorMessage.setText("Ruoan lisäys ateriaan epäonnistui: tarkista kaikki kentät!");
                }
            }
        });
        deleteFromMeal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mealPageErrorMessage.setText("");
                try {
                    dPortions.deletePortionPart(mealNameInput.getText(), mealPartField.getText());
                } catch (NumberFormatException n) {
                    mealPageErrorMessage.setText("Ruoka-aineen poisto ateriasta epäonnistui: tarkista kentät 'ruoka-annoksen nimi' ja 'ruoka-aine'!");
                    
                }
            }
        });
        listAllMeals.setOnAction((ActionEvent e) -> {
            pMealList.clear();
            pMealList.addAll(dPortions.getDishContentArrayList());
        });
        listMealNames.setOnAction((ActionEvent e) -> {
            pMealList.clear();
            pMealList.addAll(dPortions.getPortionNames());
        });
        listMealContents.setOnAction((ActionEvent e) -> {
            pMealList.clear();
            pMealList.addAll(dPortions.getPortionContentsWithName(mealNameInput.getText()));
            
        });
        bringIncredientsList.setOnAction((ActionEvent e)-> {
            pMealList.clear();
            pMealList.addAll(dIncredients.listIncredientsArrayList());
        });
        
        mealInput.getChildren().addAll(mealNameLabel, mealNameInput, mealPartLabel, mealPartField, mealPartAmountLabel, mealPartAmountField);
        mealButtons.getChildren().addAll(addMeal, addIncrToMeal, deleteFromMeal);
        mealManager.getChildren().addAll(mealInput, mealButtons);
        searchOptions.getChildren().addAll(listAllMeals, listMealNames, listMealContents);
        mealManagePage.getChildren().addAll(mealManager, bringIncredientsList, mealPageErrorMessage, searchOptions, portionList);
        
        // Päiväkirjasivu
        VBox diaryButtons = new VBox();
        VBox diaryLabelsFields = new VBox();
        HBox waterManager = new HBox();
        HBox diaryDatabaseButtons = new HBox();
        HBox diaryManager = new HBox();
        
        VBox diaryPage = new VBox();
        
        diaryButtons.setPadding(new Insets(5, 10, 5, 10));
        diaryButtons.setSpacing(2);
        diaryLabelsFields.setPadding(new Insets(5, 10, 5, 10));
        diaryLabelsFields.setAlignment(Pos.TOP_LEFT);
        waterManager.setPadding(new Insets(5, 10, 5, 10));
        diaryDatabaseButtons.setPadding(new Insets(0, 0, 5, 10));
        diaryDatabaseButtons.setSpacing(2);
        waterManager.setSpacing(2);
        
        Label portionLabel = new Label("Ruoka-annos:");
        Label dayLabel = new Label("Päivämäärä:");

        
        TextField diaryInput = new TextField();
        TextField dayInput = new TextField();
        dayInput.setText("PP.KK.VVVV");
        
        Button getDate = new Button("Hae päivämäärä - tänään");
        Button formatMealList = new Button("Alusta lasku (pvm)");
        Button sumAMeal = new Button("Lisää annos(annos)");
        Button substractAMeal = new Button("Vähennä annos (annos)");
        
        Button getWaterAmount = new Button("Hae päivän vesimäärä(pvm)");
        Button exportWater = new Button("Tallenna vesimäärä (pvm)");
        Label waterUnit = new Label("desilitraa");
        TextField waterAmount = new TextField();
        
        
        Button showDiaryData = new Button("Update data");
        Button zeroDiaryDay = new Button("Nollaa päivän ruoat (pvm, ateria)");
        
        ListView<String> diaryL = new ListView<>(diaryList);
        diaryList.clear();
        diaryList.addAll(dDiary.getDiaryData());
        
        ListView<String> diaryM = new ListView<>(dMealList);
        dMealList.clear();
        dMealList.addAll(dPortions.getPortionNames());
        
        
        
        Label diaryErrorMessage = new Label("");
        diaryErrorMessage.setPadding(new Insets(0, 0, 5, 10));
        diaryErrorMessage.setTextFill(RED);
        
        
        getDate.setOnAction((ActionEvent e)-> {
            dayInput.setText(diary.getDate());
        });
        formatMealList.setOnAction((ActionEvent e)-> {
            diary.addDate(dayInput.getText());
            diaryList.clear();
            diaryList.addAll(dDiary.getDiaryData());
            
        });
        sumAMeal.setOnAction((ActionEvent e)-> {
            diary.addMeal(dayInput.getText(), diaryInput.getText());
            diaryList.clear();
            diaryList.addAll(dDiary.getDiaryData());
        });
        substractAMeal.setOnAction((ActionEvent e)-> {
            diary.substractMeal(dayInput.getText(), diaryInput.getText());
            diaryList.clear();
            diaryList.addAll(dDiary.getDiaryData());
            diaryErrorMessage.setText("");
        });
        showDiaryData.setOnAction((ActionEvent e)-> {
            diaryList.clear();
            diaryList.addAll(dDiary.getDiaryData());
            diaryErrorMessage.setText("");
        });
        zeroDiaryDay.setOnAction((ActionEvent e)-> {
            diaryErrorMessage.setText("Nollataksesi päivän ruoat, sijoita '0' ruoka-annoskenttään");
            if (diaryInput.getText().equals("0")) {
                diaryErrorMessage.setText("");
                dDiary.updateDiary(dayInput.getText(), 0, 0, 0, 0);
                diaryList.clear();
                diaryList.addAll(dDiary.getDiaryData());
            }
        });
        getWaterAmount.setOnAction((ActionEvent e)-> {
            int waterA = diary.getWater(dayInput.getText());
            waterAmount.setText(String.valueOf(waterA));
            diaryErrorMessage.setText("");
        });
        exportWater.setOnAction((ActionEvent e)-> {
            String wAmount = waterAmount.getText().replaceAll("\\D+", "");
            if (wAmount.equals("")) {
                diaryErrorMessage.setText("Ei lisättävää vesimäärää");
            } else {
                int wAmo = Integer.valueOf(wAmount);
                diary.updateWater(dayInput.getText(), wAmo);
                diaryList.clear();
                diaryList.addAll(dDiary.getDiaryData());
                diaryErrorMessage.setText("");
            }
        });
        
        diaryLabelsFields.getChildren().addAll(portionLabel, diaryInput, dayLabel, dayInput);
        diaryButtons.getChildren().addAll(getDate, formatMealList, sumAMeal, substractAMeal);
        diaryDatabaseButtons.getChildren().addAll(showDiaryData, zeroDiaryDay);
        waterManager.getChildren().addAll(getWaterAmount, waterAmount, waterUnit, exportWater);
        
        diaryManager.getChildren().addAll(diaryLabelsFields, diaryButtons, diaryM);
        diaryPage.getChildren().addAll(diaryManager, waterManager, diaryErrorMessage, diaryDatabaseButtons, diaryL);
        // Setup primary stage
        first.setOnAction((event)-> {
            layout.setCenter(incredientPage);
            first.setStyle("-fx-background-color: LIGHTSALMON");
            second.setStyle("-fx-background-color: LIGHTGRAY");
            third.setStyle("-fx-background-color: LIGHTGRAY");
            incrList.clear();
            incrList.addAll(dIncredients.listIncredientsArrayList());
        });
        second.setOnAction((event)-> {
            layout.setCenter(mealManagePage);
            first.setStyle("-fx-background-color: LIGHTGRAY");
            second.setStyle("-fx-background-color: LIGHTSALMON;");
            third.setStyle("-fx-background-color: LIGHTGRAY");
        });
        third.setOnAction((event)-> { 
            layout.setCenter(diaryPage);
            dMealList.clear();
            dMealList.addAll(dPortions.getPortionNames());
            diaryList.clear();
            diaryList.addAll(dDiary.getDiaryData());
            first.setStyle("-fx-background-color: LIGHTGRAY");
            second.setStyle("-fx-background-color: LIGHTGRAY");
            third.setStyle("-fx-background-color: LIGHTSALMON;");
        });
        
        layout.setCenter(diaryPage);
        
        Scene view = new Scene(layout);
        window.setTitle("Ruokaohjelma");
        window.setWidth(600);
        window.setHeight(600);
        window.setScene(view);
        window.show();
        
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Käynnistetään. 3..2..1..");
        
        Application.launch(args);
        
    }
}



