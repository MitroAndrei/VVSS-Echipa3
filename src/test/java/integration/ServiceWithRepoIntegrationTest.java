package integration;

import inventory.errors.ValidationException;
import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.Inventory;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

public class ServiceWithRepoIntegrationTest {
    // integrare R in S
    @InjectMocks
    static private Inventory repo;
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
        service = new InventoryService(repo);
    }

    @AfterEach
    public void tearDown() {
        repo.setAllParts(FXCollections.observableArrayList());
    }

    @Test
    void testAddValidInHousePart() {
        assertEquals(0, service.getAllParts().size());
        InhousePart p = new InhousePart(1, validName, validPrice, validStock, validMin, validMax, validMachineId);

        try (MockedStatic<Part> part = Mockito.mockStatic(Part.class)) {
            part.when(() -> Part.isValidPart(validName, validPrice, validStock, validMin, validMax, ""))
                    .thenReturn("");

            service.addInhousePart(validName, validPrice, validStock, validMin, validMax, validMachineId);

            assertEquals(1, service.getAllParts().size());
            assertEquals(p.getName(), service.lookupPart(validName).getName());
            part.verify(times(1), () -> Part.isValidPart(validName, validPrice, validStock, validMin, validMax, ""));
        }
    }

    @Test
    void testAddInvalidInHousePart() {
        assertEquals(0, service.getAllParts().size());

        try (MockedStatic<Part> part = Mockito.mockStatic(Part.class)) {
            part.when(() -> Part.isValidPart(invalidName, validPrice, validStock, validMin, validMax, ""))
                    .thenReturn("A name has not been entered. ");

            try {
                service.addInhousePart(invalidName, validPrice, validStock, validMin, validMax, validMachineId);
                fail("Part added");
            } catch (ValidationException e) {
                assertEquals(0, service.getAllParts().size());
                assertEquals("A name has not been entered. ", e.getMessage());
                assertNull(service.lookupPart(invalidName));
            }

            part.verify(times(1), () -> Part.isValidPart(invalidName, validPrice, validStock, validMin, validMax, ""));
        }
    }

    @Test
    void testLookupValidInHousePart() {
        Part part = new InhousePart(1, "piesa", 1.0, 7, 5, 10, 1);
        //Mockito.doNothing().when(validator).validateSearchString("piesa");
        //Mockito.doNothing().when(validator).validatePart(part);
        repo.addPart(part);
        Part p = service.lookupPart("piesa");
        //Mockito.verify(validator, Mockito.times(1)).validatePart(part);
        assertEquals(part, p);
    }

    @Test
    void testLookupInvalidInHousePart() {
        //Mockito.doThrow(new ValidationException("A name has not been entered. ")).when(validator).validatePart(Mockito.any());
        try {
            service.lookupPart("");
        } catch (ValidationException e) {
            //Mockito.verify(validator, Mockito.times(1)).validatePart(Mockito.any());
            assertEquals("A name has not been entered. ", e.getMessage());
        }
    }
}
