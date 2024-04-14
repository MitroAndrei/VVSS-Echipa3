package inventory.repository;

import inventory.errors.ValidationException;
import inventory.model.InhousePart;
import inventory.model.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;

class InventoryTestWithMock {
    @InjectMocks
    private Inventory repo;

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
        InhousePart p = new InhousePart(1, validName, validPrice, validStock, validMin, validMax, validMachineId);
        assertEquals(0, repo.getAllParts().size());
        try (MockedStatic<Part> part = Mockito.mockStatic(Part.class)) {
            part.when(() -> Part.isValidPart(validName, validPrice, validStock, validMin, validMax, ""))
                    .thenReturn("");
            repo.addPart(p);
            part.verify(times(1), () -> Part.isValidPart(validName, validPrice, validStock, validMin, validMax, ""));
        }
        assertEquals(1, repo.getAllParts().size());
    }

    @Test
    void testAddInvalidInHousePart() {
        InhousePart p = new InhousePart(1, invalidName, validPrice, validStock, validMin, validMax, validMachineId);
        assertEquals(0, repo.getAllParts().size());
        try (MockedStatic<Part> part = Mockito.mockStatic(Part.class)) {
            part.when(() -> Part.isValidPart(invalidName, validPrice, validStock, validMin, validMax, ""))
                    .thenReturn("A name has not been entered. ");
            try {
                repo.addPart(p);
                fail("Part added");
            } catch (ValidationException e) {
                assertEquals(0, repo.getAllParts().size());
                assertEquals("A name has not been entered. ", e.getMessage());
            }
            part.verify(times(1), () -> Part.isValidPart(invalidName, validPrice, validStock, validMin, validMax, ""));
        }
    }

}
