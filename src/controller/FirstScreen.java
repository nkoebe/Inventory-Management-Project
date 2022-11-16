package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

/** This is the controller class that controls the First Screen of the application */
public class FirstScreen implements Initializable {

    /** The exit button for this screen. */
    @FXML
    private Button exit;

    /** The Column that present the Part ID in the Table */
    @FXML
    private TableColumn<?, ?> partIdCol;

    /** The Table column that presents the Inventory of the part */
    @FXML
    private TableColumn<?, ?> partInventoryCol;

    /** The Table column that presents the Name of the part */
    @FXML
    private TableColumn<?, ?> partNameCol;

    /** The Anchor Pane that displays the table */
    @FXML
    private AnchorPane partTable;

    /** The button to add a new part */
    @FXML
    private Button partsAdd;

    /** The button to delete a selected part */
    @FXML
    private Button partsDelete;

    /** The button to modify the selected part */
    @FXML
    private Button partsModify;

    /** The table column that presents the price of the part */
    @FXML
    private TableColumn<?, ?> partsPriceCol;

    /** The button that initiates a search of the parts list */
    @FXML
    private Button partsSearch;

    /** A text field to enter the name or ID number that you would like to search */
    @FXML
    private TextField partsSearchText;

    /** The table that holds the parts from the parts list */
    @FXML
    private TableView<Part> partsTable;

    /** The table that holds the Products from the product list */
    @FXML
    private TableView<Product> partsTable1;

    /** The button to add a new product */
    @FXML
    private Button prodAdd1;

    /** The button to delete a selected Product */
    @FXML
    private Button prodDelete1;

    /** The table column that presents the Product ID number */
    @FXML
    private TableColumn<?, ?> prodIdCol1;

    /** The table column that presents the product inventory level */
    @FXML
    private TableColumn<?, ?> prodInventoryCol1;

    /** The button to modify a selected product */
    @FXML
    private Button prodModify1;

    /** The table column that presents the Product names */
    @FXML
    private TableColumn<?, ?> prodNameCol1;

    /** The table column that presents the Product prices */
    @FXML
    private TableColumn<?, ?> prodPriceCol1;

    /** The button to search the product list for a specified name or ID */
    @FXML
    private Button prodSearch1;

    /** The text field to enter the desired name, partial name, or ID to search for a product */
    @FXML
    private TextField prodSearchText1;

    /** The Anchor Pane that holds the product table */
    @FXML
    private AnchorPane prodTable;

    /** Exits the application with an exit code of 0 when the exit button is pressed
     *
     * @param event Clicking the Exit button
     */
    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }

    /** Opens the "Add Part" screen of the application
     *
     * @param event Clicking the "Add" button
     * @throws IOException
     */
    @FXML
    public void onPartsAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Part");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Deletes a selected part from the all Parts list in the Inventory
     *
     * @param event clicking the delete button
     */
    @FXML
    void onPartsDelete(ActionEvent event) {
        boolean isPartSelected = false;
        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            isPartSelected = true;
        }

        if (isPartSelected == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete " + partsTable.getSelectionModel().getSelectedItem().getName() + " and its data. Continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
            }
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SELECTION ERROR");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
    }

    /** Opens the Modify Part screen, used to change info of an existing part
     *
     * @param event Clicking the Modify button on the Parts table
     * @throws IOException
     */
    @FXML
    public void onPartsModify(ActionEvent event) throws IOException {
        boolean isPartSelect = false;
        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            isPartSelect = true;
        }

        if (isPartSelect == true) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPart MPController = loader.getController();

            if (partsTable.getSelectionModel().getSelectedItem() instanceof InHouse) {
                MPController.passInHouseInfo((InHouse) partsTable.getSelectionModel().getSelectedItem());
            } else if (partsTable.getSelectionModel().getSelectedItem() instanceof Outsourced) {
                MPController.passOutSourcedInfo((Outsourced) partsTable.getSelectionModel().getSelectedItem());
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setTitle("Modify Part form");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }
    }

    /** Opens the screen to add a new Product
     *
     * @param event clicking the add button under the Product table
     * @throws IOException
     */
    @FXML
    void onProdAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddProduct.fxml"));
        loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();
        stage.setTitle("Add Product form");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Deletes the selected product from the product list
     *
     * @param event clicking the delete button under the product table
     */
    @FXML
    void onProdDelete(ActionEvent event) {
        boolean isProdSelected = false;
        if (partsTable1.getSelectionModel().getSelectedItem() != null) {
            isProdSelected = true;
        }

        if(isProdSelected == true && partsTable1.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SELECTION ERROR");
            alert.setContentText(partsTable1.getSelectionModel().getSelectedItem().getName() + " has Associated Part(s). User cannot delete Product with Associated Part(s)");
            alert.showAndWait();
            return;
        }

        if (isProdSelected == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete " + partsTable1.getSelectionModel().getSelectedItem().getName() + " and its data. Continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deleteProduct(partsTable1.getSelectionModel().getSelectedItem());
            }
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SELECTION ERROR");
            alert.setContentText("Please select a product to delete");
            alert.showAndWait();
        }
    }

    /** Opens the Modify Product screen where the user can change the info of an existing product
     *
     * @param event clicking the Modify button under the product table
     * @throws IOException
     */
    @FXML
    void onProdModify(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProduct ModProdController = loader.getController();
            ModProdController.populateTables(partsTable1.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setTitle("Modify Product form");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (RuntimeException runtimeException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SELECTION ERROR");
            alert.setContentText("Please select a product to modify");
            alert.showAndWait();
        }
    }

    /** Searches the parts list for a part that matches what is entered in the search text field
     *
     * @param event clicking the search button above the parts table
     */
    @FXML
    void partsSearchHandler(ActionEvent event) {
        String q = partsSearchText.getText();

        ObservableList<Part> parts = searchByPartName(q);

        if(parts.size() == 0) {
            try {
                int partId = Integer.parseInt(q);
                Part p = searchByPartID(partId);
                if (p != null) {
                    parts.add(p);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }


        partsTable.setItems(parts);

    }

    /** Searches for a part by the part name or partial name that is entered
     *
     * @param partialName the name or partial name to search for
     * @return Returns a list of parts that match the name or partial name
     */
    private ObservableList<Part> searchByPartName (String partialName) {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part searchPart : allParts) {
            if (searchPart.getName().contains(partialName)) {
                searchedParts.add(searchPart);
            }
        }

        return searchedParts;
    }

    /** Searches for a part by the part ID that is entered
     *
     * @param partId the ID that is entered to be searched for
     * @return returns the part that matches the given ID. Returns null if no matches are found
     */
    private Part searchByPartID (int partId) {

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part p = allParts.get(i);

            if (p.getId() == partId) {
                return p;
            }

        }
        return null;
    }

    /** Searches the product list for a product that matches what is entered in the search text field
     *
     * @param event clicking the search button above the product table
     */
    @FXML
    void prodSearchHandler(ActionEvent event) {
        String q = prodSearchText1.getText();

        ObservableList<Product> products = searchByProdName(q);

        if(products.size() == 0) {
            try {
                int prodId = Integer.parseInt(q);
                Product p = searchByProdID(prodId);
                if (p != null) {
                    products.add(p);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }

        partsTable1.setItems(products);
    }

    /** Searches the product list for a name or partial name that is entered into the text field
     *
     * @param partialName the name that is entered in the text field
     * @return returns a list of all the products that match the name or partial name entered
     */
    private ObservableList<Product> searchByProdName (String partialName) {
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product searchProduct : allProducts) {
            if (searchProduct.getName().contains(partialName)) {
                searchedProducts.add(searchProduct);
            }
        }

        return searchedProducts;
    }

    /** Searches the product list for a ID that is entered in the text field
     *
     * @param prodId the given ID to be searched for
     * @return Returns the product with the matching ID, or null if no matching ID is found
     */
    private Product searchByProdID (int prodId) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);

            if (p.getId() == prodId) {
                return p;
            }

        }
        return null;
    }

      // Used to add data to the tables so that I could test the program as I worked on it

    private static boolean firstTime = true;


    private void addTestData() {
        if (!firstTime) {
            return;
        }
        firstTime = false;

        InHouse brakes = new InHouse(1, "Brakes", 15.00, 10, 0, 20, 1);
        Inventory.addPart(brakes);
        InHouse wheel = new InHouse(2, "Wheel", 11.00, 16, 0, 30, 2);
        Inventory.addPart(wheel);
        InHouse seat = new InHouse(3, "Seat", 15.000, 10, 0, 20, 3);
        Inventory.addPart(seat);

        Product giantBike = new Product(1000, "Giant Bike", 299.99, 5, 0, 20);
        Inventory.addProduct(giantBike);
        Product tricycle = new Product(1001, "Tricycle", 99.99, 3, 0, 10);
        Inventory.addProduct(tricycle);

    }

    /** Initialize the screen. Ran everytime this screen is opened. Populates the tables.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestData();
        partsTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable1.setItems(Inventory.getAllProducts());
        prodIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInventoryCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
