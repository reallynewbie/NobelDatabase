/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobeldatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Shawn Gagnon (gagnons23@mymacewan.ca)
 * @author Bruce Leung
 * @author Matthew Craner
 */
public class Main extends Application {

    public static NobelDatabase DB;

    public static void main(String[] args) throws IOException {
        DB = new NobelDatabase();
        launch(args);  //Launches the FXML file.
    }

    /**
     * start -- Starts the JavaFX Database Search Application.  
     * 
     *
     * @param primaryStage - the primaryStage for the FXML Application
     * @throws Exception - If it can't find the FXML File,it throws an exception.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFXML.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> Platform.exit()); // Closes all the pop-ups when the main window is closed.

        /* I needed the following steps to dig down through the panes, boxes,
         * grids, to get into the location where I want to add the YearSlider 
         * Object.
         */
        AnchorPane aPane = (AnchorPane) root;
        VBox box = (VBox) aPane.getChildren().get(2);
        GridPane grid = (GridPane) box.getChildren().get(1);
        YearSlider slider = new YearSlider();
        grid.add(slider.getSlider(), 1, 1);

        FXMLController controller = loader.getController();
        controller.addSlider(slider);

        //First query to pull everything from the database.
        Queries myQuery = new Queries(-1, null, -1, -1, null, null, null);

        //Populates the combobox for the countries by pulling countries from
        //the database.
        ArrayList<Countries> countries = myQuery.getCountries(DB.winners);
        Collections.sort(countries, new MyComp("", ""));
        controller.addCountries(countries);
        controller.addDatabase(DB);
        
        controller.addStage(primaryStage);

        primaryStage.setTitle("Nobel Prize Database Search System");
        primaryStage.show();
    }
}
