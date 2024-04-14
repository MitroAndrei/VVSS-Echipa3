package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.IInventory;
import inventory.repository.InventoryStub;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTestWithFileInventoryStub {
    static private InventoryService service;

    @BeforeAll
    static public void setUp() {
        IInventory repo = new InventoryStub();
        service = new InventoryService(repo);
        System.out.println(service.getAllParts().size());
    }

    @Test
    public void testAddValidInHousePart() {
        assertEquals(3, service.getAllParts().size());
        service.addInhousePart("piesa", 1.0, 7, 5, 10, 1);
        assert true;
    }

    @Test
    public void testLookupValidInHousePart() {
        assertEquals(3, service.getAllParts().size());
        assertNotNull(service.lookupPart("test1"));
    }

}
