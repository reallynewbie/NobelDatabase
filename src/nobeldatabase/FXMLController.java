package nobeldatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXMLController -- the main controller for our main FXML page and UI.
 *
 * Is interconnected with the other controllers such as the laureateController,
 * and PopupController.
 *
 * @author Bruce Leung
 * @author Matthew Craner
 */
public class FXMLController implements Initializable {

    private Boolean searchExpanded = false;
    private Boolean whichPane = false;
    private Boolean homepageActive = true;
    private Boolean running = false;
    private final static ObservableList<String> GENDERS = FXCollections.observableArrayList("<Select a Gender>", "Male", "Female", "Org");
    private final static ObservableList<String> CATEGORIES = FXCollections.observableArrayList("<Select a Nobel Prize Category>", "Chemistry",
            "Economics", "Literature", "Peace", "Physics", "Medicine");
    private ObservableList<Countries> countries = FXCollections.observableArrayList();
    private NobelDatabase DB;
    private Task currentTask;

    @FXML
    private ComboBox comboCountry;

    @FXML
    private ComboBox comboGender;

    @FXML
    private ComboBox comboNP;

    @FXML
    private Button btnSearch;

    @FXML
    private VBox searchBox;

    @FXML
    private TextField textID;

    @FXML
    private TextField textName;

    @FXML
    private VBox resultsVBox1;

    @FXML
    private VBox resultsVBox2;

    @FXML
    private Pane homePane;

    @FXML
    private ImageView searchBar;

    private YearSlider hSlider;

    private Stage mainStage;

    /**
     * cursorChange() -- Changes the cursor to a pointing hand to signify that
     * the button is clickable.
     */
    @FXML
    private void cursorChange() {
        searchBox.getScene().setCursor(Cursor.HAND);
    }

    /**
     * cursorRevert() -- Changes the cursor back to the default arrow after a
     * cursorChange() event.
     */
    @FXML
    private void cursorRevert() {
        searchBox.getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * barClicked() -- The event that triggers when the search bar is clicked.
     * If it is currently lowered, it extends and vice-versa.
     */
    @FXML
    private void barClicked() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(250), searchBox);
        if (searchExpanded == false) {
            searchBox.toFront();
            searchExpanded = true;
            searchBar.setImage(new Image("nobeldatabase/images/SearchExpanded.png"));
            tt.setByY(-176f);
            tt.play();
        } else {
            searchBox.toFront();
            searchExpanded = false;
            searchBar.setImage(new Image("nobeldatabase/images/SearchCollapsed.png"));
            tt.setByY(176f);
            tt.play();
        }
    }

    /**
     * evtSearch() -- Event that triggers when the search button is clicked.
     * Causes a search to occur with the currently selected search criteria.
     */
    @FXML
    private void evtSearch() {
        int yearMin, yearMax, id;
        String name, countryCode, category, gender;
        ArrayList<Nobelwinner> results = new ArrayList();

        if (running) {
            currentTask.cancel();
        }

        //Fades the Homepage if it's currently active.
        if (homepageActive) {
            fadeHomePage();
        }

        // Get ID value
        if (textID.getText().equals("")) {
            id = -1;
        } else {
            try {
                id = Integer.parseInt(textID.getText());
            } catch (NumberFormatException e) {
                id = -1;
                textID.setText("");
            }
        }

        // Get Name value
        if (textName.getText().equals("")) {
            name = null;
        } else {
            name = textName.getText();
        }

        // Get prize category
        if (comboNP.getValue().toString().equals("<Select a Nobel Prize Category>")) {
            category = null;
        } else {
            category = comboNP.getValue().toString().toLowerCase();
        }

        // Get gender
        if (comboGender.getValue().toString().equals("<Select a Gender>")) {
            gender = null;
        } else {
            gender = comboGender.getValue().toString().toLowerCase();
        }

        // Get Years
        yearMin = (int) hSlider.getMin();
        yearMax = (int) hSlider.getMax();

        // Get the country code
        if (comboCountry.getValue().toString().equals("<Select a Country>")) {
            countryCode = null;
        } else {
            Countries country;
            country = (Countries) comboCountry.getSelectionModel().getSelectedItem();
            countryCode = country.getCountryCode();
        }

        // Run the search with the given parameters
        Queries searchQuery = new Queries(id, category, yearMin, yearMax, countryCode, gender, name);
        results = searchQuery.runQueries(DB.winners);

        mainStage.setTitle("Nobel Prize Database Search System - " + results.size() + " Results found.");

        // Clear any existing results
        whichPane = false;
        resultsVBox1.getChildren().clear();
        resultsVBox2.getChildren().clear();

        //Begins the helper function which starts the concurrent search thread.
        taskSearch(results);

        //Minimizes the search bar.
        barClicked();
    }

    /**
     * taskSearch -- Creates a Task/Thread to run addResult.
     *
     * @param results
     */
    private void taskSearch(ArrayList<Nobelwinner> results) {
        currentTask = new Task<Void>() {
            /**
             * call() -- When the task is started, everything in call is
             * executed.
             *
             * @return null
             */
            @Override
            public Void call() {
                btnSearch.setDisable(true); // Disables the Search Button until the search is completed.
                for (Nobelwinner winner : results) {
                    //Checks if task is cancelled and clears the boxes.
                    if (isCancelled()) {
                        Platform.runLater(() -> resultsVBox1.getChildren().clear());
                        Platform.runLater(() -> resultsVBox2.getChildren().clear());
                        break;
                    }
                    addResult(winner);
                }
                return null;
            }

            /**
             * succeeded() -- When the task is completed successfully, it
             * re-enables the Search button and sets the running variable to
             * false to notify other functions that nothing is currently running
             * .
             */
            @Override
            public void succeeded() {
                btnSearch.setDisable(false);
                whichPane = false;
                running = false;
            }

            /**
             * cancelled() -- This is run when the task is cancelled.
             * Basically resets some of the variables used to populate the
             * results.
             */
            @Override
            public void cancelled() {
                running = false;
                whichPane = false;
                btnSearch.setDisable(false);
            }
        };
        new Thread(currentTask).start();
        running = true;
    }

    /**
     * addSlider -- Adds a two-headed range slider object to the controller
     *
     * @param slider - the slider to add.
     */
    public void addSlider(YearSlider slider) {
        hSlider = slider;
    }

    /**
     * addStage -- Adds the stage for the controller to be able to access it.
     * Mainly using the stage for changing the title of the window.
     *
     * @param stage - the main stage of the application.
     */
    public void addStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * evtClear() -- Clears the search parameters, sets the combo boxes to the
     * first item, clears out the textfields, and resets the slider. Also clears
     * our the current search results and cancels a running search.
     */
    @FXML
    private void evtClear() {
        homePane.toBack();

        //Cancels the search if there is one currently running.
        if (running) {
            currentTask.cancel();
        }

        mainStage.setTitle("Nobel Prize Database Search System");

        comboGender.getSelectionModel().selectFirst();
        comboNP.getSelectionModel().selectFirst();
        comboCountry.getSelectionModel().selectFirst();

        textID.setText("");
        textName.setText("");
        hSlider.resetSlider();

        // Resets the side on which the first entry pops up.
        whichPane = false;

        //Clears the two VBoxes.
        resultsVBox1.getChildren().clear();
        resultsVBox2.getChildren().clear();

    }

    /**
     * addCountries -- Populates the combobox with countries.
     *
     * @param list the countries passed to the controller
     */
    public void addCountries(ArrayList<Countries> list) {

        countries.setAll(list);
        comboCountry.setItems(countries);
        comboCountry.getItems().add(0, "<Select a Country>");
        comboCountry.getSelectionModel().selectFirst();
    }

    /**
     * addResult() - Blank result for testing the UI.
     */
    public void addResult() {

        try {
            FXMLLoader lauLoader = new FXMLLoader(getClass().getResource("LaureateFXML.fxml"));
            Pane myPane = lauLoader.load();
            myPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            myPane.setPadding(new Insets(5));

            // Switches which side the result is added to
            if (whichPane == false) {
                resultsVBox1.getChildren().add(myPane);
                whichPane = true;
            } else {
                resultsVBox2.getChildren().add(myPane);
                whichPane = false;
            }

        } catch (IOException e) {
            System.out.println("Unable to find file");
        }

    }

    /**
     * addResult(winner) -- Add the result to the display
     *
     * @param winner the winner to display information for
     */
    public void addResult(Nobelwinner winner) {

        try {
            FXMLLoader lauLoader = new FXMLLoader(getClass().getResource("LaureateFXML.fxml"));
            Pane myPane = lauLoader.load();
            myPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            myPane.setPadding(new Insets(5));

            // Get the controller and send the nobelwinner to it for the popup
            LaureateController lauCon = lauLoader.getController();
            lauCon.setWinner(winner);

            // Sets which side the result gets added to
            if (whichPane == false) {
                Platform.runLater(() -> resultsVBox1.getChildren().add(myPane));
                whichPane = true;
            } else {
                Platform.runLater(() -> resultsVBox2.getChildren().add(myPane));
                whichPane = false;
            }
        } catch (IOException e) {
            System.out.println("Unable to find file");
        }

    }

    /**
     * addDatabase -- adds the NobelDatabase object to the controller.
     *
     * @param data - the NobelDatabase Object containing all our data.
     */
    public void addDatabase(NobelDatabase data) {
        DB = data;
    }

    /**
     * initHomePage() -- Initializes the Splash Pane that shows up at the first
     * run.
     */
    private void initHomePage() {
        try {
            homePane.getChildren().add(FXMLLoader.load(getClass().getResource("HomeFXML.fxml")));
            homePane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        } catch (IOException e) {
            System.out.println("Unable to find HomeFXML.fxml");
        }

    }

    /**
     * fadeHomePage() -- Controls the fade transition when the user does the
     * first search.
     */
    private void fadeHomePage() {

        FadeTransition ft = new FadeTransition(Duration.seconds(1), homePane);
        ft.setFromValue(1.0);
        ft.setToValue(0);

        ft.play();
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                homePane.toBack();
            }
        });
        homepageActive = false;
    }

    /**
     * initialize() -- Sets up the main JavaFX window.
     *
     * @param url given from the auto generation
     * @param rb given from the auto generation
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        comboGender.setItems(GENDERS);
        comboNP.setItems(CATEGORIES);

        initHomePage();

        comboGender.getSelectionModel().selectFirst();
        comboNP.getSelectionModel().selectFirst();
        resultsVBox1.setPadding(new Insets(5));
        resultsVBox2.setPadding(new Insets(5));
        searchBox.toFront();

        searchBar.setImage(new Image("/nobeldatabase/images/SearchCollapsed.png"));
    }
}
