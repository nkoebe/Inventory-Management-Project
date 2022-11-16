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

/** This is the controller class that controls the Add Product screen of the application */
public class AddProduct implements Initializable {

    /** The text field for the Product's name */
    public TextField addProdNameText;

    /** The text field for the Product's inventory level */
    public TextField addProdInvText;

    /** The text field for the Product's price */
    public TextField addProdPriceText;

    /** The text field for the Product's Max inventory level */
    public TextField addProdMaxText;

    /** The text field for the Product's min inventory level */
    public TextField addProdMinText;

    /** The table that shows all parts that we can add to the associated parts list for this product */
    public TableView addProdPartTable;

    /** The text field to enter a part name to search for in the parts table */
    public TextField addProdSearchText;

    /** The table that shows all associated parts for this product */
    public TableView addProdAssocPartTable;

    /** The button to add a selected part from the part table to the associated parts table */
    public Button addProdAddButton;

    /** The button to remove a selected part from the associated parts table */
    public Button removeAssocPartButton;

    /** The save button for this screen */
    public Button addProdSaveButton;

    /** The cancel button for this page */
    public Button prodAddCancelButton;

    /** The table column that shows the part ID in the parts table */
    public TableColumn partIdCol;

    /** The table column that shows the part name in the parts table */
    public TableColumn partNameCol;

    /** The table column that shows the part inventory level in the parts table */
    public TableColumn invCol;

    /** The table column that shows the part price in the parts table */
    public TableColumn priceCol;

    /** The table column that shows the part ID in the associated parts table */
    public TableColumn assocIdCol;

    /** The table column that shows the part name in the associated parts table */
    public TableColumn assocNameCol;

    /** The table column that shows the part inventory level in the associated parts table */
    public TableColumn assocInvCol;

    /** The table column that shows the part price in the associated parts table */
    public TableColumn assocPriceCol;

    /** The search button for the parts table */
    public Button searchButton;

    /** An empty list to use for adding/removing associated parts to this product */
    public static ObservableList<Part> selectedRows = FXCollections.observableArrayList();

    /** An empty product to use when creating the new product */
    public static Product newProd = new Product(-1, "hold", -1, -1, -1, -1);

    /** The start of the ID numbers for the products. I have chosen to start at 1000 and increment by 1 for each new product */
    public static int idStart = 1000;

    /** Adds the selected part from the parts table to the associated parts table
     *
     * @param actionEvent clicking the add button
     */
    public void onAddProdAddButton(ActionEvent actionEvent) {
        newProd.addAssociatedPart((Part) addProdPartTable.getSelectionModel().getSelectedItem());
        addProdAssocPartTable.setItems(newProd.getAllAssociatedParts());

        assocIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Removes the selected part from the associated parts table
     *
     * @param actionEvent clicking the remove button
     */
    public void onRemoveAssocPartButton(ActionEvent actionEvent) {
        boolean isPartSelected = false;
        ObservableList<Part> selRows;
        selRows = addProdAssocPartTable.getSelectionModel().getSelectedItems();

        if (addProdAssocPartTable.getSelectionModel().getSelectedItem() != null) {
            isPartSelected = true;
        }

        if (isPartSelected == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the part from the Associated Parts list for this Product Continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                for (Part part : selRows) {
                    newProd.deleteAssociatedPart(part);
                    return;
                }
            }
        }

    }

    /** Saves the information entered into a new product object and returns the user to the first screen
     *
     * @param actionEvent clicking the save button
     * @throws IOException
     */
    public void onAddProdSaveButton(ActionEvent actionEvent) throws IOException {

        if(addProdNameText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No product name entered. Please enter a name for this product");
            alert.showAndWait();
            return;
        }

        try {
            String priceEntered = addProdPriceText.getText();
            double priceNum = Double.parseDouble(priceEntered);
            String invEntered = addProdInvText.getText();
            int invNum = Integer.parseInt(invEntered);
            String minEntered = addProdMinText.getText();
            int minNum = Integer.parseInt(minEntered);
            String maxEntered = addProdMaxText.getText();
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

            Product p = new Product(idStart, addProdNameText.getText(), priceNum, invNum, minNum, maxNum);
            idStart++;

            for (Part b : newProd.getAllAssociatedParts()) {
                p.addAssociatedPart(b);
            }

            Inventory.addProduct(p);
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

    /** Returns the user to the first screen without saving the entered info into a new product */
    public void onProdAddCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Searchs the parts table for a part that matches the text entered into the search text field
     *
     * @param actionEvent clicking the search button
     */
    public void onSearch(ActionEvent actionEvent) {
        String q = addProdSearchText.getText();

        ObservableList<Part> parts = Inventory.lookUpPart(q);

        if(parts.size() == 0) {
            try {
                int partId = Integer.parseInt(q);
                Part p = Inventory.lookUpPart(partId);
                if (p != null) {
                    parts.add(p);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }


        addProdPartTable.setItems(parts);
    }

    /** Initialize the screen. Ran everytime this screen is opened. Populates the tables.
     *
     * @param url
     * @param resource
     */
    @Override
    public void initialize(URL url, ResourceBundle resource) {
        selectedRows.clear();

        addProdPartTable.setItems(Inventory.getAllParts());
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
