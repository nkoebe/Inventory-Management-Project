package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.net.URL;
import java.util.ResourceBundle;
/** This is the controller class that controls the Modify Part screen of the application */
public class ModifyPart {

    /** The Radio button that indicates whether the part is In House or not */
    public RadioButton modPartInHouseRadio;

    /** The toggle group that includes both radio buttons used in this screen */
    public ToggleGroup modPartGroup;

    /** The Radio button that indicates whether the part is Outsourced or not */
    public RadioButton modPartOutsourcedRadio;

    /** The label for the text field that displays either the Company Name or Machine ID depending on which type of part this is. */
    public Label modPartFlexLabel;

    /** The text field for the Part's ID */
    public TextField partIdTF;

    /** The Text Field for the part's name */
    private TextField partNameTF;

    /** The text field for the part's inventory level */
    public TextField invTF;

    /** The text field for the part's price */
    public TextField priceTF;

    /** The text field for the part's max inventory level */
    public TextField maxTF;

    /** The text field for the part's Company Name or Machine ID depending on the type of the part */
    public TextField flexTF;

    /** The text field for the part's minimum inventory level */
    public TextField minTF;

    /** The save button for this screen */
    public Button modProductSaveButton;

    /** The cancel button for this screen */
    public Button modProductCancelButton;

    /** Whether the part is inhouse or not */
    public boolean inHouse = true;

    /** A empty Part to use during the modifying process */
    private static Part selectedPart = null;

    //private static InHouse updatedInHouse;
    //private static Outsourced updatedOutSrc;

    /** This method is used to pass the selected part's info from the first screen to this screen if the part is In House. Populates the text fields. */
    public void passInHouseInfo(InHouse inHousePart) {

        partIdTF.setText(String.valueOf(inHousePart.getId()));
        partNameTF.setText(inHousePart.getName());
        invTF.setText(String.valueOf(inHousePart.getStock()));
        priceTF.setText(String.valueOf(inHousePart.getPrice()));
        maxTF.setText(String.valueOf(inHousePart.getMax()));
        minTF.setText(String.valueOf(inHousePart.getMin()));
        flexTF.setText(String.valueOf(inHousePart.getMachineId()));
        modPartInHouseRadio.setSelected(true);

    }

    /** This method is used to pass the selected part's info from the first screen to this screen if the part is Outsourced. Populates the text fields. */
    public void passOutSourcedInfo(Outsourced outsourcedPart) {
        partIdTF.setText(String.valueOf(outsourcedPart.getId()));
        partNameTF.setText(outsourcedPart.getName());
        invTF.setText(String.valueOf(outsourcedPart.getStock()));
        priceTF.setText(String.valueOf(outsourcedPart.getPrice()));
        maxTF.setText(String.valueOf(outsourcedPart.getMax()));
        minTF.setText(String.valueOf(outsourcedPart.getMin()));
        flexTF.setText(String.valueOf(outsourcedPart.getCompanyName()));
        modPartOutsourcedRadio.setSelected(true);
        modPartFlexLabel.setText("Company Name");
    }

    /** changes the flex label to Machine ID when the In House radio button is clicked
     *
     * @param actionEvent Clicking the In House radio button
     */
    public void onModPartInHouseRadio(ActionEvent actionEvent) {
        inHouse = true;
        modPartFlexLabel.setText("Machine ID");

    }

    /** Changes the flex label to Company Name when the Outsourced radio button is clicked
     *
     * @param actionEvent Clicking the Outsourced radio button
     */
    public void onModPartOutsourcedRadio(ActionEvent actionEvent) {
        inHouse = false;
        modPartFlexLabel.setText("Company Name");
    }

    /** Updates the product that was passed from the first screen with the new values in each text field
     *
     * @param actionEvent Clicking the Save button
     * @throws IOException
     */
    public void onModProductSave(ActionEvent actionEvent) throws IOException {

        if(partNameTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No part name entered. Please enter a name for this part");
            alert.showAndWait();
            return;
        }

        try {
            String priceEntered = priceTF.getText();
            Double priceNum = Double.parseDouble(priceEntered);
            String invEntered = invTF.getText();
            int invNum = Integer.parseInt(invEntered);
            String minEntered = minTF.getText();
            int minNum = Integer.parseInt(minEntered);
            String maxEntered = maxTF.getText();
            int maxNum = Integer.parseInt(maxEntered);
            int idEntered = Integer.parseInt(partIdTF.getText());

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
                int machId = Integer.parseInt(flexTF.getText());
                InHouse p = new InHouse(idEntered, partNameTF.getText(), priceNum, invNum, minNum, maxNum, machId);

                Inventory.updatePart(Integer.parseInt(partIdTF.getText()) - 1, p);
            } else if (inHouse == false) {
                String compName = flexTF.getText();
                Outsourced p = new Outsourced(idEntered, partNameTF.getText(), priceNum, invNum, minNum, maxNum, compName);

                Inventory.updatePart(Integer.parseInt(partIdTF.getText()) - 1, p);
            }
        }
        catch (RuntimeException runtimeException) {
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

    /** Returns the user to the first screen without saving any changes to the part
     *
     * @param actionEvent Clicking the Cancel button
     * @throws IOException
     */
    public void onModProductCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
