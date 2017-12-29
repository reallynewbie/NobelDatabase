package nobeldatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import static nobeldatabase.ModifiedString.capitalize;

/**
 * LaureateController - the controller for each of the LaureateFXML objects
 * which will display the laureate's information.
 *
 * @author Bruce Leung
 * @author Matthew Craner
 */
public class LaureateController implements Initializable {

    private Nobelwinner winner;

    @FXML
    private Text textCategory;

    @FXML
    private Text textYear;

    @FXML
    private Text textCountry;

    @FXML
    private Text textGender;

    @FXML
    private Label labelName;

    @FXML
    private HBox resultsBox;

    /**
     * paneClicked() -- Event triggered when a result is clicked.
     *
     * Creates a pop-up window with a photo and more details on the laureate.
     */
    @FXML
    private void paneClicked() {
        final Stage popStage = new Stage();
        final Popup infoPop = new Popup();
        PopupFXMLController popCon = null;
        Scene popScene;
        Parent popRoot;

        try {
            FXMLLoader popLoad = new FXMLLoader(getClass().getResource("PopupFXML.fxml"));
            popRoot = popLoad.load();
            popScene = new Scene(popRoot);
            popStage.setScene(popScene);
            popStage.setResizable(false);
            popCon = popLoad.getController();
        } catch (IOException ex) {
            System.out.println("Unable to find popup file");
        }
        popStage.show();
        popCon.initPop(winner);
        infoPop.show(popStage);
        popStage.setTitle(winner.getFirstName() + " " + winner.getLastName() + " <" + winner.getId() + ">");
    }

    /**
     * setCategory -- Set the category for the results pane in the main window.
     *
     * @param category - String which is displayed.
     */
    public void setCategory(String category) {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ");
        sb.append(category);
        textCategory.setText(sb.toString());
    }

    /**
     * setYear -- Sets the year for the results pane in the main window.
     *
     * @param year that is displayed
     */
    public void setYear(int year) {
        StringBuilder sb = new StringBuilder();
        sb.append("Year: ");
        sb.append(year);
        textYear.setText(sb.toString());
    }

    /**
     * setCountry -- Sets the country for the results pane in the main window.
     *
     * @param country that is displayed
     */
    public void setCountry(String country) {
        StringBuilder sb = new StringBuilder();
        sb.append("Country: ");
        sb.append(country);
        textCountry.setText(sb.toString());
    }

    /**
     * setGender -- Sets the gender for the results pane in the main window
     *
     * @param gender that is displayed
     */
    public void setGender(String gender) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gender: ");
        sb.append(gender);
        textGender.setText(sb.toString());
    }

    /**
     * setNameID -- Sets the name and id for the results pane in the main window
     *
     * @param name that is displayed
     * @param id that is displayed
     */
    public void setNameID(String name, int id) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" <");
        sb.append(id);
        sb.append(">");
        labelName.setText(sb.toString());
    }

    /**
     * Sets the winner to get the information from and call the set methods
     *
     * @param nw
     */
    public void setWinner(Nobelwinner nw) {
        winner = nw;
        String name;
        // Combine the first and last name
        name = winner.getFirstName() + " " + winner.getLastName();

        // Set the display
        setCategory(capitalize(winner.getCategory()));
        setYear(winner.getYear());
        setCountry(winner.getBornCountry());
        setGender(capitalize(winner.getGender()));
        setNameID(name, winner.getId());
    }

    /**
     * cursorChange() -- Changes the cursor to a pointing hand to signify that
     * the button is clickable.
     */
    @FXML
    private void cursorChange() {
        resultsBox.getScene().setCursor(Cursor.HAND);
    }

    /**
     * cursorRevert() -- Changes the cursor back to the default arrow after a
     * cursorChange() event.
     */
    @FXML
    private void cursorRevert() {
        resultsBox.getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * Initializes the LaureateFXML with just a simple wrap text since that's
     * all that is necessary
     *
     * @param url - given from the auto generation
     * @param rb - given from the auto generation
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelName.setWrapText(true);
    }

}
