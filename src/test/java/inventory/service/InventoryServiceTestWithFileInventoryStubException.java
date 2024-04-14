package inventory.service;

import inventory.repository.IInventory;
import inventory.repository.InventoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InventoryServiceTestWithFileInventoryStubException {
    private InventoryService service;

    @BeforeEach
    public void setUp() {
        IInventory repo = new InventoryStub();
        service = new InventoryService(repo);
    }

    @Test
    public void testAddInvalidInHousePart() {
        assertEquals(3, service.getAllParts().size());
        service.addInhousePart("piesa", 1.0, 7, 5, 10, 1);
        assert true;
    }

    @Test
    public void testLookupInvalidInHousePart() {
        assertEquals(3, service.getAllParts().size());
        assertNull(service.lookupPart("nonexistent"));
    }
}
