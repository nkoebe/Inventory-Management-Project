package model;

/** This class Models the Outsourced Part object */
public class Outsourced extends Part {

    /** The Company Name of the Outsourced Part */
    private String companyName;

    /** The constructor for the Outsourced Part Object
     *
     * @param id The ID of the part
     * @param name The name of the part
     * @param price The price of the part
     * @param stock The current Inventory level of the part
     * @param min The Minimum Inventory level of the part
     * @param max The Maximum Inventory level of the part
     * @param companyName The Company Name for the Outsourced Part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** The Setter for the Company Name of the Outsourced Part
     *
     * @param companyName The name to be assigned to the Company Name for this part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** The Getter for the Company Name attribute
     *
     * @return Returns the Company Name of the Outsourced Part
     */
    public String getCompanyName() {
        return companyName;
    }
}
