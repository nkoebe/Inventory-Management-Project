package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class that controls the Modify Product screen of the application */
public class ModifyProduct implements Initializable {

    /** The text field for the selected Product's ID */
    public TextField modProdIdText;

    /** The text field for the selected Product's name */
    public TextField modProdNameText;

    /** The text field for the selected Product's inventory level */
    public TextField modProdInvText;

    /** The text field for the selected Product's price */
    public TextField modProdPriceText;

    /** The text field for the selected Product's max inventory level */
    public TextField modProdMaxText;

    /** The text field for the selected Product's minimum inventory level */
    public TextField modProdMinText;

    /** The table that displays all the parts that can be added to the associated parts for this product */
    public TableView modProdPartTable;

    /** The table that displays all the associated parts for this product */
    public TableView modProdAssocPartTable;

    /** The text field to enter a name or id to search for a product */
    public TextField modProdSearchText;

    /** The add button for this screen */
    public Button modProdAddButton;

    /** The remove button for this screen */
    public Button modProdRemoveButton;

    /** The save button for this screen */
    public Button modProdSaveButton;

    /** The cancel button for this screen */
    public Button modProdCancelButton;

    /** The table column in the parts table that displays the part ID */
    public TableColumn partIdCol;

    /** The table column in the parts table that displays the part's name */
    public TableColumn partNameCol;

    /** The table column in the parts table that displays the part's inventory level */
    public TableColumn invCol;

    /** The table column in the parts table that displays the part price */
    public TableColumn priceCol;

    /** The table column in the associated parts table that displays the part ID */
    public TableColumn assocIdCol;

    /** The table column in the associated parts table that displays the part name */
    public TableColumn assocNameCol;

    /** The table column in the associated parts table that displays the part inventory level */
    public TableColumn assocInvCol;

    /** The table column in the associated parts table that displays the part price */
    public TableColumn assocPriceCol;


    /** An empty product used when updating the selected product */
    public static Product testProd = new Product(-1, "hold", -1, -1, -1, -1);

    /** This method receives the Product information that is passed from the first screen
     *
     * @param selProduct The product that is passed from the first screen
     */
    public void populateTables(Product selProduct) {
        testProd = selProduct;
        System.out.println(selProduct.getAllAssociatedParts().size() +" " + selProduct.getName());
        modProdIdText.setText(String.valueOf(testProd.getId()));
        modProdNameText.setText(testProd.getName());
        modProdInvText.setText(String.valueOf(testProd.getStock()));
        modProdPriceText.setText(String.valueOf(testProd.getPrice()));
        modProdMaxText.setText(String.valueOf(testProd.getMax()));
        modProdMinText.setText(String.valueOf(testProd.getMin()));

        modProdAssocPartTable.setItems(testProd.getAllAssociatedParts());

    }

    /** Adds the selected part from the parts table to the associated parts table
     *
     * @param actionEvent clicking the add button
     */
    public void onModProdAddButton(ActionEvent actionEvent) {
        Part k = (Part) modProdPartTable.getSelectionModel().getSelectedItem();
        testProd.addAssociatedPart(k);
        modProdAssocPartTable.setItems(testProd.getAllAssociatedParts());
    }

    /** Removes the selected part from the associated parts table
     *
     * @param actionEvent clicking the remove button
     */
    public void onModProdRemove(ActionEvent actionEvent) {
        boolean isPartSelected = false;
        if (modProdAssocPartTable.getSelectionModel().getSelectedItem() != null) {
            isPartSelected = true;
        }

        if (isPartSelected == true) {
            //Part q = (Part) modProdAssocPartTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the selected Part from the Associated Parts list for " + modProdNameText.getText() + " Continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Part q = (Part) modProdAssocPartTable.getSelectionModel().getSelectedItem();
                testProd.deleteAssociatedPart(q);
            }
        }
    }

    /** Updates the selected product with the new information that was entered on this screen
     *
     * @param actionEvent clicking the save button
     * @throws IOException
     */
    public void onModProdSaveButton(ActionEvent actionEvent) throws IOException {

        if(modProdNameText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No product name entered. Please enter a name for this product");
            alert.showAndWait();
            return;
        }

        try {
            if (Integer.parseInt(modProdMaxText.getText()) < Integer.parseInt(modProdMinText.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Min cannot be higher than max.");
                alert.showAndWait();
                return;
            }

            if (Integer.parseInt(modProdInvText.getText()) > Integer.parseInt(modProdMaxText.getText()) || Integer.parseInt(modProdInvText.getText()) < Integer.parseInt(modProdMinText.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Current Stock must fall between Min and Max");
                alert.showAndWait();
                return;
            }

            testProd.setId(Integer.parseInt(modProdIdText.getText()));
            testProd.setName(modProdNameText.getText());
            testProd.setPrice(Double.parseDouble(modProdPriceText.getText()));
            testProd.setMax(Integer.parseInt(modProdMaxText.getText()));
            testProd.setMin(Integer.parseInt(modProdMinText.getText()));
            testProd.setStock(Integer.parseInt(modProdInvText.getText()));

            Inventory.updateProduct(testProd.getId() - 1000, testProd);
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

    /** Returns the user to the first screen without saving any of the changes */
    public void onModProdCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Initialize the screen. Ran everytime this screen is opened. Populates the tables.
     *
     * @param url
     * @param resource
     */
    @Override
    public void initialize(URL url, ResourceBundle resource) {

        modProdPartTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
