package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//import java.util.PrimitiveIterator;
/** This class Models an Inventory that contains all the parts and products */
public class Inventory {

    /** A list of all of products */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** A method to add a Product to the list of all products
     *
     * @param newProduct The product to be added to the inventory
     */
    public static void addProduct (Product newProduct) {
        allProducts.add(newProduct);
    }

    /** A method used to search for a part based on an ID
     *
     * @param productId The ID to search for in our products
     * @return Returns the product with the matching ID, otherwise returns null if there are no matching IDs.
     */
    public static Product lookUpProduct (int productId) {
        for (Product q : allProducts) {

            if (q.getId() == productId) {
                return q;
            }

        }
        return null;
    }

    /** A method that would be used to search a product by its name, or partial name
     * This method was not used as the search was taken care of in the first screen controller
     *
     * @param productName the Name or partial name to be searched
     * @return Returns an observable list with any product that matches the name or partial name that was entered in the search bar.
     */
    public static ObservableList<Product> lookUpProduct (String productName) {

        return null;
    }

    /** A method that updates a specific product.
     *
     * @param index The index of the product to be updated in the list of all products
     * @param selectedProduct the Product that will replace the old product, updating it via replacement
     */
    public static void updateProduct (int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /** A method that deletes a specified product from the list of all products
     *
     * @param selectedProduct The product to be deleted
     * @return Returns true when a Product is deleted
     */
    public static boolean deleteProduct (Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /** The getter for the list of all products
     *
     * @return The list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** A list of all Parts */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** A method to add a new part to the list of parts
     *
     * @param newPart The part to be added to the list
     */
    public static void addPart (Part newPart) {
        allParts.add(newPart);
    }

    /** A method used to search for a part by its ID
     *
     * @param partId The specified ID to search for
     * @return Returns the part with the matching ID, or null if there is no matching ID
     */
    public static Part lookUpPart (int partId) {
        for (Part q : allParts) {

            if (q.getId() == partId) {
                return q;
            }

        }
        return null;
    }

    /** A method used to search for a part by its name or partial name
     *
     * @param partName The name or partial name to search for
     * @return Returns a list of all the parts that have a matching name
     */
    public static ObservableList<Part> lookUpPart (String partName) {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part searchPart : allParts) {
            if (searchPart.getName().contains(partName)) {
                searchedParts.add(searchPart);
            }
        }

        return searchedParts;
    }

    /** A method used to update a specific part with new info
     *
     * @param index The index of the part to be updated in the All Parts list
     * @param selectedPart The part that will replace the original part. Updated via replacement
     */
    public static void updatePart (int index, Part selectedPart) {
        Inventory.getAllParts().set(index, selectedPart);
    }

    /** A Method used to delete a selected part from the parts list
     *
     * @param selectedPart the Part to be deleted
     * @return returns true when a part is deleted
     */
    public static boolean deletePart (Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /** The getter for the list of all parts
     *
     * @return returns the list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


}
