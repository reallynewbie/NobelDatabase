package nobeldatabase;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import org.controlsfx.control.RangeSlider;

/**
 * YearSlider -- Creates a HBox with a text field to the left and right of the
 * RangeSlider object from ControlsFX.
 *
 * @author Bruce Leung
 */
public class YearSlider extends HBox {

    HBox hbox = new HBox(10);
    TextField minBox = new TextField();
    TextField maxBox = new TextField();
    RangeSlider hSlider;

    /**
     * YearSlider Constructor -- Constructs the YearSlider Object and customizes
     * the HBox parameters.
     */
    public YearSlider() {
        initSlider();
        initBoxes();
        hbox.setPadding(new Insets(5));
        hbox.setAlignment(Pos.CENTER);
        hbox.setHgrow(hSlider, Priority.ALWAYS);
        hbox.getChildren().addAll(minBox, hSlider, maxBox);
        hSlider.setId("YearSlider");
    }

    /**
     * getSlider -- Returns the HBox holding everything. This is mainly used for
     * accessing the HBox from outside of this object.
     *
     * @return hbox - the HBox holding the object.
     */
    public HBox getSlider() {
        return hbox;
    }

    /**
     * getMin -- Retrieves the lower head of the RangeSlider.
     *
     * @return - a double value representing the lower head value in the
     * RangeSlider.
     */
    public double getMin() {
        return hSlider.lowValueProperty().getValue();
    }

    /**
     * getMax -- Retrieves the higher head of the RangeSlider.
     *
     * @return - a double value representing the high head value in the
     * RangeSlider.
     */
    public double getMax() {
        return hSlider.highValueProperty().getValue();
    }

    /**
     * resetSlider() -- Resets the slider when the clear button is hit to it's
     * default entries.
     */
    public void resetSlider() {
        hSlider.setLowValue(1901);
        hSlider.setHighValue(2017);
    }

    /**
     * initBoxes -- Initializes the textboxes used to show the min/max value of
     * the RangeSlider.
     */
    private void initBoxes() {
        minBox.setEditable(false);
        maxBox.setEditable(false);
        minBox.textProperty().bind(hSlider.lowValueProperty().asString("%.0f"));
        maxBox.textProperty().bind(hSlider.highValueProperty().asString("%.0f"));
        minBox.setPrefColumnCount(3);
        maxBox.setPrefColumnCount(3);
    }

    /**
     * initSlider -- Initializes the RangeSlider and makes sure the labels are
     * in the correct format(0 decimal places, no commas).
     */
    private void initSlider() {
        hSlider = new RangeSlider(1901, 2017, 1901, 2017);
        hSlider.setShowTickLabels(true);
        hSlider.setShowTickMarks(true);
        /* Overwrites the rangeSlider's labels to values I want. If the 
         * following is not there, I would have commas and periods in the 
         * year.
         */
        hSlider.setLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                switch (object.intValue()) {
                    case 1901:
                        return "1901";
                    case 1926:
                        return "1926";
                    case 1951:
                        return "1951";
                    case 1976:
                        return "1976";
                    case 2001:
                        return "2001";
                    case 2017:
                        return "2017";
                }
                return object.toString();
            }

            @Override
            public Number fromString(String string) {
                return Double.valueOf(string);
            }
        });
    }
}
