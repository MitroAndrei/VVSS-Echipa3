package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

public class InventoryStub implements IInventory {

    private ObservableList<Part> parts;

    public InventoryStub() {
       List<Part> partsobs = Arrays.asList(new InhousePart(1, "test1", 10.10, 5, 2, 10, 1),
                                        new InhousePart(2, "test2", 34.6, 15, 5, 20, 2),
                                        new InhousePart(3, "test3", 12.5, 28, 5, 20, 3));
        parts = FXCollections.observableArrayList(partsobs);
    }
    @Override
    public void addProduct(Product product) {
        // default behavior does nothing
    }

    @Override
    public Part lookupPart(String searchItem) {
        return parts.stream()
                .filter(part -> part.getName().equals(searchItem))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ObservableList<Part> getAllParts() {
        return parts;
    }

    // -------------------------------------

    @Override
    public ObservableList<Product> getProducts() {
        return null;
    }

    @Override
    public void removeProduct(Product product) {

    }



    @Override
    public void updateProduct(int index, Product product) {

    }

    @Override
    public void setProducts(ObservableList<Product> list) {

    }

    @Override
    public void addPart(Part part) {

    }

    @Override
    public void deletePart(Part part) {

    }

    @Override
    public Product lookupProduct(String searchItem) {
        return null;
    }

    @Override
    public List<Part> lookupParts(String searchItem) {
        return null;
    }

    @Override
    public void updatePart(int index, Part part) {

    }



    @Override
    public void setAllParts(ObservableList<Part> list) {

    }

    @Override
    public int getAutoPartId() {
        return 0;
    }

    @Override
    public int getAutoProductId() {
        return 0;
    }

    @Override
    public void setAutoPartId(int id) {

    }

    @Override
    public void setAutoProductId(int id) {

    }
}
