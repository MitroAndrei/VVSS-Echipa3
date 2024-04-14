package integration;

import inventory.errors.ValidationException;
import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.Inventory;
import inventory.service.InventoryService;
import inventory.validators.PartValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SrvRepoWithEntityIntegrationTest {

    static private Inventory repo;
    static private InventoryService service;
    private PartValidator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new InventoryService(repo);
    }

    @Test
    void testAddValidInHousePart() {
        Mockito.doNothing().when(validator).validatePart(Mockito.any());
        Part part = new InhousePart(1, "piesa", 1.0, 7, 5, 10, 1);
        assertEquals(0, service.getAllParts().size());
        service.addInhousePart("piesa", 1.0, 7, 5, 10, 1);
        Mockito.verify(validator, Mockito.times(1)).validatePart(Mockito.any());
        assertEquals(1, service.getAllParts().size());
        assertEquals(part, service.lookupPart("piesa"));
    }

    @Test
    void testAddInvalidInHousePart() {
        Mockito.doThrow(new ValidationException("A name has not been entered. ")).when(validator).validatePart(Mockito.any());
        assertEquals(0, service.getAllParts().size());
        try {
            service.addInhousePart("", 1.0, 7, 5, 10, 1);
        } catch (ValidationException e) {
            Mockito.verify(validator, Mockito.times(1)).validatePart(Mockito.any());
            assertEquals(0, service.getAllParts().size());
            assertEquals(e, new ValidationException("A name has not been entered. "));
        }

    }

    @Test
    void testLookupValidInHousePart() {
        Part part = new InhousePart(1, "piesa", 1.0, 7, 5, 10, 1);
        Mockito.doNothing().when(validator).validateSearchString("piesa");
        Mockito.doNothing().when(validator).validatePart(part);
        repo.addPart(part);
        Part p = service.lookupPart("piesa");
        Mockito.verify(validator, Mockito.times(1)).validatePart(part);
        assertEquals(part, p);
    }

    @Test
    void testLookupInvalidInHousePart() {
        Mockito.doThrow(new ValidationException("A name has not been entered. ")).when(validator).validatePart(Mockito.any());
        try {
            service.lookupPart("");
        } catch (ValidationException e) {
            Mockito.verify(validator, Mockito.times(1)).validatePart(Mockito.any());
            assertEquals("A name has not been entered. ", e.getMessage());
        }
    }
}
