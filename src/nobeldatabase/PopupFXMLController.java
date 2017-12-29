package nobeldatabase;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import static nobeldatabase.ModifiedString.capitalize;
import static nobeldatabase.ModifiedString.removeAllQuotations;

/**
 * PopupFXMLController -- Controls the Pop-Up window that pops up when a
 * laureate is clicked.
 *
 * @author Quyen Tang
 * @author Matthew Craner
 * @author Bruce Leung
 */
public class PopupFXMLController implements Initializable {

    @FXML
    private Text textInfo;
    @FXML
    private ImageView image;

    private String imgPath, info;
    private Nobelwinner winner;

    /**
     * Initializes the popup information
     *
     * @param nw the winner that the popup is about
     */
    public void initPop(Nobelwinner nw) {
        winner = nw;
        // Create the info string
        info = createInfo(winner);
        // Create the image url
        imgPath = downloadImage(winner.getLastName(), winner.getCategory(), winner.getYear());
        // Set and display the info and image
        textInfo.setText(info);
        textInfo.maxWidth(300);
        textInfo.setWrappingWidth(295);
        //System.out.println(imgPath);
        if (winner.getGender().equalsIgnoreCase("org")) {
            imgPath = "/nobeldatabase/images/UnknownPerson.jpg";
        }
        image.setImage(new Image(imgPath));
        image.setFitHeight(398);
        image.setFitWidth(295);
    }

    /**
     * Handle odd last names
     *
     * @param lastName the last name to check
     * @return the special case modified last name
     */
    public String handleSpecialCase(String lastName) { //Use map for this if we have time.
        if (countSpaces(lastName) == 0) {
            return lastName;
        } else if (countSpaces(lastName) == 1) { //von Behring
            String lastWord = lastName.substring(lastName.lastIndexOf(" ") + 1);
            return lastWord;
        } else if (lastName.contains("von Suttner née Countess Kinsky von Chinic und Tettau")) {
            return "snutter";
        } else if (lastName.contains("Curie née Sklodowska")) {
            return "marie-curie";
        } else if (lastName.contains("\"Mother")) {
            return "teresa";
        } else {
            return null;
        }
    }

    /**
     * To properly format the URL to download the image
     *
     * @param lastName - as stated in the database
     * @param category - as stated in the database
     * @param year - as stated in the database
     * @return path - a path for the url of the image
     */
    public String downloadImage(String lastName, String category, int year) {
        if (handleSpecialCase(lastName) == null) {
            return "/nobeldatabase/images/UnknownPerson.jpg";
        } else {
            /*
              Handles the cases where there's a unicode character in the last name, which will invalidate the URL.
              In these strange character cases, we just use the stock photo.
              -Bruce
             */
            for (int i = 0; i < lastName.length(); i++) {
                if ((Character.valueOf(lastName.charAt(i)) > 127) | ((Character.valueOf(lastName.charAt(i)) < 0))) {
                    return "/nobeldatabase/images/UnknownPerson.jpg";
                }
            }

            StringBuilder finalUrl = new StringBuilder();
            String path;
            finalUrl.append("https://www.nobelprize.org/nobel_prizes/");
            String afterCategory = "/laureates/";
            String afterYear = "/";
            String afterLastName = "_postcard.jpg";
            finalUrl.append(category);
            finalUrl.append(afterCategory);
            finalUrl.append(year);
            finalUrl.append(afterYear);
            finalUrl.append(lastName.toLowerCase());
            finalUrl.append(afterLastName);
            path = finalUrl.toString();
            try { //Checks to see if URL for the picture is valid. -Bruce
                URL url = new URL(path);
                try {
                    url.toURI();
                } catch (URISyntaxException e) {
                    path = "/nobeldatabase/images/UnknownPerson.jpg";
                }
            } catch (MalformedURLException e) {
                path = "/nobeldatabase/images/UnknownPerson.jpg";
            }
            return path;
        }
    }

    /**
     * To count the number of spaces a string has
     *
     * @param lastName - as stated in the database
     * @return an integer representing the number of spaces a string has
     */
    public int countSpaces(String lastName) {
        int spaceCount = 0;
        for (char c : lastName.toCharArray()) {
            if (c == ' ') {
                spaceCount++;
            }
        }
        return spaceCount;
    }

    /**
     * Create the information string to display in the popup
     *
     * @param nw the nobel winner to get information from and display
     * @return the formatted information string
     */
    private String createInfo(Nobelwinner nw) {
        StringBuilder info = new StringBuilder();
        info.append("Name <ID>:\t" + nw.getFirstName() + " " + nw.getLastName() + " <" + nw.getId() + ">\n");
        info.append("Gender:\t\t" + capitalize(nw.getGender()) + "\n");
        info.append("Category:\t\t" + capitalize(nw.getCategory()) + "\n");
        info.append("Prize Year:\t" + nw.getYear() + "\n");

        if (nw.getDied().equalsIgnoreCase("0000-00-00")) {// Added to fill out the birth date of N/A - Bruce
            info.append("Born:\t\t" + "N/A" + "\n");
        } else {
            info.append("Born:\t\t" + nw.getBorn() + "\n");
        }

        info.append("Born Country:\t" + nw.getBornCountry() + "\n");
        if (nw.getDied().equalsIgnoreCase("0000-00-00")) { // Added to fill out the death date of N/A - Bruce
            info.append("Died:\t\t" + "N/A" + "\n");
        } else {
            info.append("Died:\t\t" + nw.getDied() + "\n");
        }
        info.append("Died Country:\t" + nw.getDiedCountry() + "\n");
        info.append("Affliation:\t\t" + nw.getName() + "\n");
        info.append("Motivation:\t" + capitalize(removeAllQuotations(nw.getMotivation())) + "\n");
        info.append("Share: \t\t1/" + nw.getShare() + "\n");

        return info.toString();
    }

    /**
     * initialize() -- Initializes the pop-up window. Currently not in use.
     *
     * @param url given from the auto generation
     * @param rb given from the auto generation
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
