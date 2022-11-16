package controller;


// Add Parts Pane

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;

/** This is the controller class that controls the Add Part screen of the application */
public class AddPart {

    /** The Radio button that indicates the part is In House */
    public RadioButton inHouseRadio;

    /** The Radio button that indicates the part is Outsourced */
    public RadioButton outsourcedRadio;

    /** The toggle group to make sure that only one of the above Radio Buttons can be selected at a time */
    public ToggleGroup addPartGroup;

    /** The label for the attribute that changes based on whether the part is In House or Outsourced. */
    public Label flexLabel;

    /** The save button for this screen */
    public Button addPartSaveButton;

    /** The cancel button for this screen */
    public Button addPartCancelButton;

    /** The Text field to enter the part's name */
    public TextField addPartNameTF;

    /** The text field to enter the part's Inventory level */
    public TextField addPartInvTF;

    /** The text field to enter the part's price */
    public TextField addPartPriceTF;

    /** The text field to enter the parts Max inventory level */
    public TextField addPartMaxTF;

    /** The text field to enter the part's Machine ID or Company name, depending on which type of part it is. */
    public TextField addPartLabelTF;

    /** The text field to enter the parts minimum inventory level */
    public TextField addPartMinTF;

    /** Whether the in House radio button is selected or not */
    public boolean inHouse = true;

    /** The starting point for all part ID's. I've chosen to start at 1 and increment with each new part */
    public static int partIdStart = 1;

    /** When the In House radio button is selected, the flex label with read "Machine ID"
     *
     * @param actionEvent clicking the In House Radio button
     */
    public void onInHouseRadio(ActionEvent actionEvent) {
        flexLabel.setText("Machine ID");
        inHouse = true;
    }

    /** When the Outsourced radio button is selected, the flex label with read "Company Name"
     *
     * @param actionEvent clicking the Outsourced Radio button
     */
    public void onOutsourcedRadio(ActionEvent actionEvent) {
        flexLabel.setText("Company Name");
        inHouse = false;
    }

    /** Saves all the info entered into a new Part object and returns the user to the first screen
     *
     * @param actionEvent when the Save button is clicked
     * @throws IOException
     *
     * RUNTIME ERROR: As I was testing the Add Part screen, I would get a Runtime error after clicking the Outsourced Radio button,
     * adding test data, and clicking save. After reviewing the code, the issue was that for one of the if statements, I had only
     * put a single "=", and so I was not doing a comparison, but an assigment. Corrected the mistake and used "==" to compare.
     */
    public void onAddPartSave(ActionEvent actionEvent) throws IOException {

        if(addPartNameTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No part name entered. Please enter a name for this part");
            alert.showAndWait();
            return;
        }

        try {
            String priceEntered = addPartPriceTF.getText();
            Double priceNum = Double.parseDouble(priceEntered);
            String invEntered = addPartInvTF.getText();
            int invNum = Integer.parseInt(invEntered);
            String minEntered = addPartMinTF.getText();
            int minNum = Integer.parseInt(minEntered);
            String maxEntered = addPartMaxTF.getText();
            int maxNum = Integer.parseInt(maxEntered);

            if (maxNum < minNum) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Min cannot be higher than max.");
                alert.showAndWait();
                return;
            }

            if (invNum > maxNum || invNum < minNum) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Current Stock must fall between Min and Max");
                alert.showAndWait();
                return;
            }

            if (inHouse == true) {
                int machIdNum = Integer.parseInt(addPartLabelTF.getText());
                InHouse p = new InHouse(partIdStart, addPartNameTF.getText(), priceNum, invNum, minNum, maxNum, machIdNum);
                partIdStart++;
                Inventory.addPart(p);
            }
            if (inHouse == false) {
                Outsourced p = new Outsourced(partIdStart, addPartNameTF.getText(), priceNum, invNum, minNum, maxNum, addPartLabelTF.getText());
                partIdStart++;
                Inventory.addPart(p);
            }
        }
        catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("User must enter valid info for all fields");
            alert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root));
        stage.show();

    }

    /** Closes this window and returns user to the first screen without saving any info */
    public void onAddPartCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
