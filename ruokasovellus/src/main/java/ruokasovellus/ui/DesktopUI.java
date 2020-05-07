/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruokasovellus.ui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.application.Application;
import java.sql.SQLException;
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
    private DatabaseIncredients DIncredients;
    private DatabasePortions DPortions;
    private DatabaseDiary DDiary;
    private DiaryFunctions diary;
    private TableView table = new TableView();
 
    
    
    @Override
    public void start(Stage window) throws Exception, SQLException {
        Database kanta = new Database();
        kanta.createTables();
        DatabaseIncredients DIncredients = new DatabaseIncredients(kanta);
        DatabasePortions DPortions = new DatabasePortions(kanta, DIncredients);
        DatabaseDiary DDiary = new DatabaseDiary(kanta, DPortions);
        DiaryFunctions diary = new DiaryFunctions(kanta, DIncredients, DPortions, DDiary);
        
        BorderPane layout = new BorderPane();
        //sivun alalaidassa olevat napit jolla voi vaihtaa näkymää
        HBox pageSwitcher = new HBox();
        Button first = new Button("Ruoka-aineet");
        Button second = new Button("Ruoka-annokset");
        Button third = new Button("Päiväkirja");
        
        pageSwitcher.getChildren().addAll(first, second, third);
        pageSwitcher.setPadding(new Insets(5, 20, 20, 20));
        pageSwitcher.setSpacing(10);
        
        
        // Ruoka-ainesivu
        VBox incredientButtons = new VBox();
        VBox incredientFields = new VBox();
        VBox incredientLabels = new VBox();
        HBox incredientManager = new HBox();
        Label incredientsList = new Label("");
        VBox incredientPage = new VBox();
        
        incredientButtons.setPadding(new Insets(5, 10, 5, 10));
        incredientButtons.setSpacing(4);
        incredientLabels.setPadding(new Insets(5, 10, 5, 10));
        incredientLabels.setSpacing(8);
        incredientLabels.setAlignment(Pos.TOP_RIGHT);
        incredientsList.setPrefSize(400, 400);
        
        Button addIncr = new Button("Lisää");
        Button deleteIncr = new Button("Poista");
        Label incrTip = new Label("Syötä numeroarvot yhden \ndesimaalin kera! esim 13,3");
        Button refreshIncredientsList = new Button("päivitä ruoka-aineluettelo");
        
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
        
        Label incredientsPageErrorMessage = new Label("Message: ");
        
        //ruoka-ainesivun toiminnallisuus
        
        //ruoka-ainelistauksen päivitys
        //Label listIncredients = new Label ("");
        incredientsList.setText("");
        refreshIncredientsList.setOnAction((ActionEvent e)-> {
            //String incredientsInList = kanta.listIncredientsToString();
            incredientsList.setText(DIncredients.listIncredientsToString());
        });
        //ruoka-aineen lisäys
        addIncr.setOnAction((ActionEvent e)-> {

            String incredName = incredientInput.getText();
            String energy = kcalInput.getText();
            String carbohyd = chInput.getText();
            String protein = protInput.getText();
            String fats = fatInput.getText();

            int ene = Integer.valueOf(energy.replaceAll("\\D+", ""));
            int ch = Integer.valueOf(carbohyd.replaceAll("\\D+", ""));
            int prot = Integer.valueOf(protein.replaceAll("\\D+", ""));
            int fat = Integer.valueOf(fats.replaceAll("\\D+", ""));

            boolean iadd = DIncredients.addIncredient(incredName, ene, ch, prot, fat);
            incredientsPageErrorMessage.setText("lisäys onnistui: " + iadd);
            incredientsList.setText(DIncredients.listIncredientsToString());
            
        });
        //Ruoka-aineen nollaus
        deleteIncr.setOnAction((ActionEvent e)-> {
            incredientsPageErrorMessage.setText("Nollataksesi ruoka-aineen jota ei enää ole sidottu annokseen, syötä Kcal -riville arvo: -1");
            if (kcalInput.getText().equals("-1")) {
                DIncredients.deleteIncredient(incredientInput.getText());
                incredientsList.setText(DIncredients.listIncredientsToString());
            }
        });
        
        incredientButtons.getChildren().addAll(addIncr, deleteIncr, incrTip, refreshIncredientsList);
        incredientFields.getChildren().addAll(incredientInput, kcalInput, chInput, protInput, fatInput);
        incredientLabels.getChildren().addAll(incrName, incrkcal, incrch, incrprot, incrfat);
        
                
        incredientManager.getChildren().addAll(incredientLabels, incredientFields, incredientButtons);
        incredientPage.getChildren().addAll(incredientManager, incredientsPageErrorMessage, incredientsList);
        
        
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
        mealInput.setPadding(new Insets(5, 10, 5, 10));
        mealInput.setSpacing(2);
        mealManager.setPadding(new Insets(5, 10, 5, 10));
        mealList.setPrefSize(400, 600);
        
        //Control elements
        Label mealNameLabel = new Label("Ruoka-annoksen nimi:");
        TextField mealNameInput = new TextField();
        Label mealPartLabel = new Label("Ruoka-aine:");
        TextField mealPartField = new TextField();
        Label mealPartAmountLabel = new Label("Ruoka-aineen määrä (g):");
        TextField mealPartAmountField = new TextField();
        
        
        Button addMeal = new Button("Lisää uusi ruoka-annos (nimikenttä)");
        Button addIncrToMeal = new Button("Lisää ruoka-aine annokseen(aine, määrä)");
        Button deleteFromMeal = new Button("Poista ruoka-aine annoksesta(nimi, aine)");
        
        
        Button listAllMeals = new Button("Aterioiden sisältö");
        Button listMealNames = new Button("Lisättyjen aterioiden nimet");
        Button listMealContents = new Button("Näytä annoksen sisältö (nimikenttä)");
        
        Button bringIncredientsList = new Button("Ruoka-aineluettelo");
        Label mealPageErrorMessage = new Label("");
        //Actions
        addMeal.setOnAction((ActionEvent e) -> {
            DPortions.addPortion(mealNameInput.getText());
        });
        addIncrToMeal.setOnAction((ActionEvent e) -> {
            String portName = mealNameInput.getText();
            int portNameInt = DPortions.getPortionId(portName);
            String portIncrName = mealPartField.getText();
            int portIncrNameInt = DIncredients.getIncredientId(portIncrName);
            int portAmount = Integer.valueOf(mealPartAmountField.getText());
            DPortions.addDishContents(portNameInt, portIncrNameInt, portAmount);
        });
        deleteFromMeal.setOnAction((ActionEvent e) -> {
            DPortions.deletePortionPart(mealNameInput.getText(), mealPartField.getText());
            mealList.setText(DIncredients.listIncredientsToString());
        });
        listAllMeals.setOnAction((ActionEvent e) -> {
            mealList.setText(DPortions.getDishContentToString());
        });
        listMealNames.setOnAction((ActionEvent e) -> {
            mealList.setText(DPortions.getPortionNames());
        });
        listMealContents.setOnAction((ActionEvent e) -> {
            String mealsList = DPortions.getPortionContentsWithName(mealNameInput.getText());
            mealList.setText(mealsList);
        });
        bringIncredientsList.setOnAction((ActionEvent e)-> {
            mealList.setText(DIncredients.listIncredientsToString());
        });
        
        mealInput.getChildren().addAll(mealNameLabel, mealNameInput, mealPartLabel, mealPartField, mealPartAmountLabel, mealPartAmountField);
        mealButtons.getChildren().addAll(addMeal, addIncrToMeal, deleteFromMeal);
        mealManager.getChildren().addAll(mealInput, mealButtons);
        searchOptions.getChildren().addAll(listAllMeals, listMealNames, listMealContents);
        mealManagePage.getChildren().addAll(mealManager, bringIncredientsList, mealPageErrorMessage, searchOptions, mealList);
        
        // Päiväkirjasivu
        VBox diaryButtons = new VBox();
        VBox diaryLabels = new VBox();
        VBox diaryFields = new VBox();
        HBox waterManager = new HBox();
        HBox diaryDatabaseButtons = new HBox();
        HBox diaryManager = new HBox();
        
        //diaryParts collects function controllers, errormessage-line and data view( to be added) on top of each other
        VBox diaryPage = new VBox();
        
        diaryButtons.setPadding(new Insets(5, 10, 5, 10));
        diaryButtons.setSpacing(2);
        diaryLabels.setPadding(new Insets(5, 10, 5, 10));
        diaryLabels.setAlignment(Pos.TOP_RIGHT);
        diaryFields.setPadding(new Insets(5, 10, 5, 10));
        waterManager.setPadding(new Insets(5, 10, 5, 10));
        
        Label portionLabel = new Label("Ruoka-annos:");
        Label dayLabel = new Label("Päivämäärä:");
        diaryLabels.setSpacing(8);
        
        TextField diaryInput = new TextField();
        TextField dayInput = new TextField();
        dayInput.setText("PP.KK.VVVV");
        
        Button getDate = new Button("Hae päivämäärä - tänään");
        Button formatMealList = new Button("Alusta lasku (pvm)");
        Button sumAMeal = new Button("Lisää annos(annos)");
        Button substractAMeal = new Button("Vähennä annos (annos)");
        
        Button getWaterAmount = new Button("Hae päivän vesimäärä(pvm)");
        Button exportWater = new Button("Tallenna vesimäärä (pvm)");
        Label waterUnit = new Label ("desilitraa");
        TextField waterAmount = new TextField();
        
        Label portionsInData = new Label(DPortions.getPortionNames());
        
        Button showDiaryData = new Button("Update data");
        
        Label diaryData = new Label();
        
        getDate.setOnAction((ActionEvent e)-> {
            dayInput.setText(diary.getDate());
        });
        formatMealList.setOnAction((ActionEvent e)-> {
            diary.addDate(dayInput.getText());
            diaryData.setText(diary.diaryToString());
        });
        sumAMeal.setOnAction((ActionEvent e)-> {
            diary.addMeal(dayInput.getText(), diaryInput.getText());
            diaryData.setText(diary.diaryToString());
        });
        substractAMeal.setOnAction((ActionEvent e)-> {
            diary.substractMeal(dayInput.getText(), diaryInput.getText());
            diaryData.setText(diary.diaryToString());
        });
        showDiaryData.setOnAction((ActionEvent e)-> {
            diaryData.setText(diary.diaryToString());
        });
        getWaterAmount.setOnAction((ActionEvent e)-> {
            int waterA = diary.getWater(dayInput.getText());
            waterAmount.setText(String.valueOf(waterA));
        });
        exportWater.setOnAction((ActionEvent e)-> {
            int wAmount = Integer.valueOf(waterAmount.getText());
            diary.updateWater(dayInput.getText(), wAmount);
            diaryData.setText(diary.diaryToString());
        });
        
        diaryLabels.getChildren().addAll(portionLabel, dayLabel);
        diaryFields.getChildren().addAll(diaryInput, dayInput);
        diaryButtons.getChildren().addAll(getDate, formatMealList, sumAMeal, substractAMeal);
        diaryDatabaseButtons.getChildren().addAll(showDiaryData);
        waterManager.getChildren().addAll(getWaterAmount, waterAmount, waterUnit, exportWater);
        
        diaryManager.getChildren().addAll(diaryLabels, diaryFields, diaryButtons, portionsInData);
        diaryPage.getChildren().addAll(diaryManager, waterManager, diaryDatabaseButtons, diaryData);
        // Setup primary stage
        first.setOnAction((event)-> {
            layout.setCenter(incredientPage);
            incredientsList.setText(DIncredients.listIncredientsToString());
        });
        second.setOnAction((event)-> layout.setCenter(mealManagePage));
        third.setOnAction((event)-> { 
            layout.setCenter(diaryPage);
            portionsInData.setText(DPortions.getPortionNames());
        });
        
        layout.setCenter(incredientPage);
        
        Scene view = new Scene(layout);
        window.setTitle("Ruokaohjelma");
        window.setWidth(550);
        window.setHeight(600);
        window.setScene(view);
        window.show();
        
    }
    private StackPane createLayout(String text) {
        StackPane layout = new StackPane();
        layout.setPrefSize(800, 800);
        layout.getChildren().add(new Label(text));
        layout.setAlignment(Pos.CENTER);
        
        return layout;
    }
    public static void main(String[] args) throws SQLException {
        System.out.println("Käynnistetään. 3..2..1..");
        
        Application.launch(args);
        
    }
}



