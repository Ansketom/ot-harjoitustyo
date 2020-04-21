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
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.application.Application;
import java.sql.SQLException;
import ruokasovellus.Database;



/**
 *
 * @author AnssiKetomäki
 */
public class DesktopUI extends Application {
    
    // private Scene view;

    private Database kanta;
 
    
    
    @Override
    public void start(Stage window) throws Exception, SQLException {
        Database kanta = new Database();
        kanta.createTables();
        
        BorderPane layout = new BorderPane();
        //sivun alalaidassa olevat napit jolla voi vaihtaa näkymää
        HBox pageSwitcher = new HBox();
        Button first = new Button("Ruoka-aineet");
        Button second = new Button("Ruoka-annokset");
        Button third = new Button("Päiväkirja");
        
        pageSwitcher.getChildren().addAll(first, second, third);
        pageSwitcher.setPadding (new Insets(5,20,20,20));
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
        Label incrTip = new Label ("Syötä numeroarvot yhden \ndesimaalin kera! esim 13,3");
        Button refreshIncredientsList = new Button("päivitä luettelo");
        
        TextField incredientInput = new TextField();
        TextField kcalInput = new TextField();
        TextField chInput = new TextField();
        TextField protInput = new TextField();
        TextField fatInput = new TextField();
        
        Label incrName = new Label ("Ruoka-aine:");
        Label incrkcal = new Label ("Kcal/100g:");
        Label incrch = new Label ("Hiilihydraatti/100g:");
        Label incrprot = new Label ("Proteiini/100g:");
        Label incrfat = new Label ("Rasva/100g:");
        
        Label incredientsPageErrorMessage = new Label("Message: ");
        
        //ruoka-ainesivun toiminnallisuus
        
        //ruoka-ainelistauksen päivitys
        //Label listIncredients = new Label ("");
        incredientsList.setText("here comes\ntext in\norder");
        refreshIncredientsList.setOnAction((ActionEvent e)-> {
            String incredientsInList = kanta.listIncredientsToString();
            //listIncredients.setText(incredientsInList);
            incredientsList.setText(incredientsInList);
        });
        //ruoka-aineen lisäys
        addIncr.setOnAction((ActionEvent e)-> {
            
            String incredName = incredientInput.getText();
            String energy = kcalInput.getText();
            String carbohyd = chInput.getText();
            String protein = incrprot.getText();
            String fats = incrfat.getText();
            
            int ene = Integer.valueOf(energy.replaceAll("\\D+", ""));
            int ch = Integer.valueOf(carbohyd.replaceAll("\\D+", ""));
            int prot = Integer.valueOf(protein.replaceAll("\\D+", ""));
            int fat = Integer.valueOf(fats.replaceAll("\\D+", ""));
            
            boolean iadd = kanta.addIncredient(incredName, ene, ch, prot, fat);
            incredientsPageErrorMessage.setText("lisäys onnistui: " + iadd);
            
        
                    
        });
        
        
        incredientButtons.getChildren().addAll(addIncr, deleteIncr, incrTip, refreshIncredientsList);
        incredientFields.getChildren().addAll(incredientInput, kcalInput, chInput, protInput, fatInput);
        incredientLabels.getChildren().addAll(incrName, incrkcal, incrch, incrprot, incrfat);
        
                
        incredientManager.getChildren().addAll(incredientLabels, incredientFields, incredientButtons);
        incredientPage.getChildren().addAll(incredientManager, incredientsPageErrorMessage, incredientsList);
        
        
        layout.setBottom(pageSwitcher);
        
        // Ruoka-annossivu
        VBox mealButtons = new VBox();
        VBox mealInput = new VBox();
        HBox mealManager = new HBox();
        ScrollPane mealList = new ScrollPane();
        VBox mealManagePage = new VBox();
        
        mealButtons.setPadding(new Insets(0, 10, 5, 10));
        mealButtons.setSpacing(2);
        mealInput.setPadding(new Insets(5, 10, 5, 10));
        mealInput.setSpacing(2);
        mealManager.setPadding(new Insets(5, 10, 5, 10));
        mealList.setPrefSize(400, 600);
        
        Label mealNameLabel = new Label("Ruoka-annoksen nimi:");
        TextField mealNameInput = new TextField();
        
        Button addMeal = new Button("Lisää");
        Button deleteMeal = new Button("Poista");
        Button listMeals = new Button("Hae Ateriat");
        
        mealInput.getChildren().addAll(mealNameLabel, mealNameInput);
        mealButtons.getChildren().addAll(addMeal, deleteMeal, listMeals);
        mealManager.getChildren().addAll(mealInput, mealButtons);
        
        // Päiväkirjasivu
        VBox diaryButtons = new VBox();
        VBox diaryLabels = new VBox();
        VBox diaryFields = new VBox();
        HBox diaryManager = new HBox();
        
        //diaryParts collects function controllers, errormessage-line and data view( to be added) on top of each other
        VBox diaryParts = new VBox();
        
        diaryButtons.setPadding(new Insets(5, 10, 5, 10));
        diaryButtons.setSpacing(2);
        diaryLabels.setPadding(new Insets(5, 10, 5, 10));
        diaryLabels.setAlignment(Pos.TOP_RIGHT);
        diaryFields.setPadding(new Insets(5, 10, 5, 10));
        
        Label portionLabel = new Label("Ruoka-annos:");
        Label dayLabel = new Label("Päivämäärä:");
        diaryLabels.setSpacing(8);
        
        TextField diaryInput = new TextField();
        TextField dayInput = new TextField();
        
        Button reloadMealList = new Button("Päivitä annoslista");
        Button sumAMeal = new Button("Lisää annos");
        Button substractAMeal = new Button("Vähennä ateria");
        
        diaryLabels.getChildren().addAll(portionLabel, dayLabel);
        diaryFields.getChildren().addAll(diaryInput, dayInput);
        diaryButtons.getChildren().addAll(reloadMealList, sumAMeal, substractAMeal);

        
        diaryManager.getChildren().addAll(diaryLabels, diaryFields, diaryButtons);
        
        // Setup primary stage
        first.setOnAction((event)-> layout.setCenter(incredientPage));
        second.setOnAction((event)-> layout.setCenter(mealManager));
        third.setOnAction((event)-> layout.setCenter(diaryManager));
        
        
        layout.setCenter(incredientPage);
        
        Scene view = new Scene(layout);
        window.setTitle("Ruokaohjelma");
        window.setScene(view);
        window.show();
        
    }
    private StackPane createLayout(String text){
        StackPane layout = new StackPane();
        layout.setPrefSize(800,600);
        layout.getChildren().add(new Label(text));
        layout.setAlignment(Pos.CENTER);
        
        return layout;
    }
    public static void main(String[] args) throws SQLException {
        System.out.println("Käynnistetään. 3..2..1..");
        
        Application.launch(args);
        
    }
}



