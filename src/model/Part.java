 /**
 * Supplied class Part.java
 */

/**
 *
 * @author Noah Koebe
 */
package model;

/** This class Models the Part object */
public abstract class Part {

    /** The ID of the part */
    private int id;

    /** The name of the part */
    private String name;

    /** The price of the part */
    private double price;

    /** The current inventory level of the part */
    private int stock;

    /** The minimum inventory level of the part */
    private int min;

    /** The maximum inventory level of the part */
    private int max;

    /** The Constructor for the Part object
     *
     * @param id The ID of the part
     * @param name The name of the part
     * @param price The price of the part
     * @param stock The current inventory level of the part
     * @param min The minimum inventory of the part
     * @param max The maximum inventory of the part
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
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