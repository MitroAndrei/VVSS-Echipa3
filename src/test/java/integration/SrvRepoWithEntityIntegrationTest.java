package integration;

import inventory.errors.ValidationException;
import inventory.model.Part;
import inventory.repository.Inventory;
import inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class SrvRepoWithEntityIntegrationTest {

    static private InventoryService service;
    private final String validName = "test1";
    private final String invalidName = "";
    private final double validPrice = 1.0;
    private final int validStock = 7;
    private final int validMin = 5;
    private final int validMax = 10;
    private final int validMachineId = 1;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Inventory repo = new Inventory();
        service = new InventoryService(repo);
    }

    @Test
    void testAddValidInHousePart() {
        assertEquals(0, service.getAllParts().size());
        service.addInhousePart(validName, validPrice, validStock, validMin, validMax, validMachineId);
        assertEquals(1, service.getAllParts().size());
        Part part = service.lookupPart(validName);
        assertNotNull(part);
        assertEquals(validName, part.getName());
        assertEquals(validPrice, part.getPrice());
        assertEquals(validStock, part.getInStock());
        assertEquals(validMin, part.getMin());
        assertEquals(validMax, part.getMax());

    }

    @Test
    void testAddInvalidInHousePart() {
        assertEquals(0, service.getAllParts().size());
        try {
            service.addInhousePart(invalidName, validPrice, validStock, validMin, validMax, validMachineId);
            fail("Part added");
        } catch (ValidationException e) {
            assertEquals(0, service.getAllParts().size());
            assertNull(service.lookupPart(invalidName));
            assertEquals(e.getMessage(), "A name has not been entered. ");
        }

    }

}
