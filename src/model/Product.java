package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Models a Product object. */
public class Product {

    /** An observable list of the parts that are associated with the product. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** The ID of the product. In this program, I started the ID at 1000. */
    private int id;

    /** The name of the product */
    private String name;

    /** The price of the product */
    private double price;

    /** The current inventory of the product. Must be above the min and below the max. */
    private int stock;

    /** The minimum inventory level of the product */
    private int min;

    /** The maximum inventory level of the product */
    private int max;

    /** The constructor for a new product object
     *
     * @param id the ID of the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the current inventory of the product
     * @param min the minimum inventory level for the product
     * @param max the maximum inventory level for the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** A method that adds a new part to the associated parts list for the product.
     *
     * @param part The part that should be added to the associated parts list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** A method to remove a selected part from the associated parts list for the product.
     *
     * @param selectedAssociatedPart The part that should be removed
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part p : associatedParts) {
            if (p.getName().contains(selectedAssociatedPart.getName())) {
                associatedParts.remove(p);
                return;
            }
        }


    }

    /** The getter for the Associated Parts list.
     *
     * @return the associated parts list for this product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
}
