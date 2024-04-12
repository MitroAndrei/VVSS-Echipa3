package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTestWithFileInventoryStubException {
    private InventoryService service;

    @BeforeAll
    public void setUpAll() {
        IInventory repo = new InventoryStub();
        service = new InventoryService(repo);
    }

    @Test
    public void testAddInvalidInHousePart() {
        assertEquals(3, service.getAllProducts().size());
        service.addInhousePart("piesa", 1.0, 7, 5, 10, 1);
        assert true;
    }

    @Test
    public void testLookupInvalidInHousePart() {
        assertEquals(3, service.getAllProducts().size());
        Part piesa = new InhousePart(0, "", 12.5, 28, 5, 20, 3);
        assertNull(service.lookupPart("piesa"));
    }
}
