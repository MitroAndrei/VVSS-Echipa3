
package inventory.repository;

import inventory.errors.RepositoryException;
import inventory.errors.ValidationException;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements IInventory {

    // Declare fields
    private ObservableList<Product> products;
    private ObservableList<Part> allParts;
    private int autoPartId;
    private int autoProductId;

    public Inventory() {
        this.products = FXCollections.observableArrayList();
        this.allParts = FXCollections.observableArrayList();
        this.autoProductId = 0;
        this.autoPartId = 0;
    }

    // Declare methods

    /**
     * Add new product to observable list products
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Remove product from observable list products
     *
     * @param product
     */
    @Override
    public void removeProduct(Product product) {
        products.remove(product);
    }

    /**
     * Accepts search parameter and if an ID or name matches input, that product is returned
     *
     * @param searchItem
     * @return
     */
    @Override
    public Product lookupProduct(String searchItem) {
        for (Product p : products) {
            if (p.getName().contains(searchItem) || (p.getProductId() + "").equals(searchItem)) {
                return p;
            }
        }
        return new Product(0, null, 0.0, 0, 0, 0, null);
    }

    /**
     * Update product at given index
     *
     * @param index
     * @param product
     */
    @Override
    public void updateProduct(int index, Product product) {
        if (index >= 0 && index < products.size()) {
            products.set(index, product);
        }
    }

    /**
     * Getter for Product Observable List
     *
     * @return
     */
    @Override
    public ObservableList<Product> getProducts() {
        return products;
    }

    @Override
    public void setProducts(ObservableList<Product> list) {
        products = list;
    }

    /**
     * Add new part to observable list allParts
     *
     * @param part
     */
    @Override
    public void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Removes part passed as parameter from allParts
     *
     * @param part
     */
    @Override
    public void deletePart(Part part) {
        allParts.remove(part);
    }

    /**
     * Accepts search parameter and if an ID or name matches input, that part is returned
     *
     * @param searchItem
     * @return
     */
    @Override
    public Part lookupPart(String searchItem) {
        for (Part p : allParts) {
            if (p.getName().contains(searchItem) || (p.getPartId() + "").equals(searchItem)) return p;
        }
        return null;
    }
    @Override
    public List<Part> lookupParts(String searchItem) {
        List<Part> parts = new ArrayList<>();
        if (searchItem.isEmpty()) {
            throw new ValidationException("Search string is empty");
        } else {
            int i = 0;
            int size = allParts.size();

            while (i < size) {
                Part p = allParts.get(i);
                if (p.getName().contains(searchItem)) {
                    parts.add(p);
                }
                i++;
            }
        }
        if (parts.isEmpty()) {
            throw new RepositoryException("No parts found");
        }
        return parts;
        //this is the exit
    }

    /**
     * Update part at given index
     *
     * @param index
     * @param part
     */
    @Override
    public void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    /**
     * Getter for allParts Observable List
     *
     * @return
     */
    @Override
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @param list
     */
    @Override
    public void setAllParts(ObservableList<Part> list) {
        allParts = list;
    }

    /**
     * Method for incrementing part ID to be used to automatically
     * assign ID numbers to parts
     *
     * @return
     */
    @Override
    public int getAutoPartId() {
        autoPartId++;
        return autoPartId;
    }

    /**
     * Method for incrementing product ID to be used to automatically
     * assign ID numbers to products
     *
     * @return
     */
    @Override
    public int getAutoProductId() {
        autoProductId++;
        return autoProductId;
    }


    @Override
    public void setAutoPartId(int id) {
        autoPartId = id;
    }

    @Override
    public void setAutoProductId(int id) {
        autoProductId = id;
    }

}
