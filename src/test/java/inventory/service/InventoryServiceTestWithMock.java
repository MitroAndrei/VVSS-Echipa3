package inventory.service;

import inventory.errors.ValidationException;
import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.IInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class InventoryServiceTestWithMock {
    @Mock
    private IInventory repo;

    @InjectMocks
    private InventoryService service;

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
    }

    @Test
    void testAddValidInHousePart() {
        Mockito.doNothing().when(repo).addPart(Mockito.any());
        service.addInhousePart(validName, validPrice, validStock, validMin, validMax, validMachineId);
        Mockito.verify(repo, times(1)).addPart(Mockito.any());
        Mockito.verify(repo,never()).getAllParts();
    }

    @Test
    void testAddInvalidInHousePart() {
        Mockito.doThrow(ValidationException.class).when(repo).addPart(Mockito.any());
        try {
            service.addInhousePart(invalidName, validPrice, validStock, validMin, validMax, validMachineId);
            fail("Part added");
        } catch (ValidationException e) {
            Mockito.verify(repo, times(1)).addPart(Mockito.any());
            Mockito.verify(repo,never()).getAllParts();
        }
    }

    @Test
    void testLookupValidInHousePart() {
        Part part = new InhousePart(1, "test1", 1.0, 7, 5, 10, 1);
        Mockito.when(repo.lookupPart("test1")).thenReturn(part);
        Part p = service.lookupPart("test1");
        Mockito.verify(repo, times(1)).lookupPart("test1");
        assertEquals(part, p);
    }

    @Test
    void testLookupInvalidInHousePart() {
        Part part = new InhousePart(1, "test1", 1.0, 7, 5, 10, 1);
        Mockito.when(repo.lookupPart("test1")).thenReturn(null);
        Part p = service.lookupPart("test1");
        Mockito.verify(repo, times(1)).lookupPart("test1");
        assertNull(p);
    }
}
