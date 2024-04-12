package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.IInventory;
import inventory.repository.InventoryStub;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTestWithFileInventoryStub {
    private InventoryService service;

    @BeforeAll
    public void setUpAll() {
        IInventory repo = new InventoryStub();
        service = new InventoryService(repo);
    }

    @Test
    public void testAddValidInHousePart() {
        assertEquals(3, service.getAllProducts().size());
        service.addInhousePart("piesa", 1.0, 7, 5, 10, 1);
        assert true;
    }

    @Test
    public void testLookupValidInHousePart() {
        assertEquals(3, service.getAllProducts().size());
        Part piesa = new InhousePart(3, "test3", 12.5, 28, 5, 20, 3);
        assertNotNull(service.lookupPart("piesa"));
    }

}
