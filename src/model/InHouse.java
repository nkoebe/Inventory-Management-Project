package model;

/** This class models the InHouse Part object. */
public class InHouse extends Part {
    /** The Machine ID number for In House parts */
    private int machineId;

    /** The constructor for an InHouse object
     *
     * @param id ID of the In House part
     * @param name name of the part
     * @param price price of the part
     * @param stock Inventory level of the part
     * @param min Minimum inventory level of the part
     * @param max Maximum inventory level of the part
     * @param machineId Machine ID number for the In House Part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** The setter for the Machine ID attribute
     *
     * @param machineId The number to be assigned as Machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** the getter for the Machine ID
     *
     * @return the Machine ID for the part
     */
    public int getMachineId() {
        return machineId;
    }
}
