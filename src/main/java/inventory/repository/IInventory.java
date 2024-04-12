package inventory.repository;

import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.ObservableList;

import java.util.List;

public interface IInventory {
    void addProduct(Product product);

    void removeProduct(Product product);

    Product lookupProduct(String searchItem);

    void updateProduct(int index, Product product);

    ObservableList<Product> getProducts();

    void setProducts(ObservableList<Product> list);

    void addPart(Part part);

    void deletePart(Part part);

    Part lookupPart(String searchItem);

    List<Part> lookupParts(String searchItem);

    void updatePart(int index, Part part);

    ObservableList<Part> getAllParts();

    void setAllParts(ObservableList<Part> list);

    int getAutoPartId();

    int getAutoProductId();

    void setAutoPartId(int id);

    void setAutoProductId(int id);
}
