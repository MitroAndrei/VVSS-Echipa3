package inventory.repository;

import inventory.errors.ValidationException;
import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.validators.IPartValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;

class InventoryTestWithMock {

    @Mock
    private IPartValidator validator;

    @InjectMocks
    private Inventory repo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddValidInHousePart() {
        InhousePart part = new InhousePart(1, "test1", 1.0, 7, 5, 10, 1);
        Mockito.doNothing().when(validator).validatePart(part);
        repo.addPart(part);
        Mockito.verify(validator, times(1)).validatePart(part);
    }

    @Test
    void testAddInvalidInHousePart() {
        InhousePart part = new InhousePart(1, "", 1.0, 7, 5, 10, 1);
        Mockito.doThrow(ValidationException.class).when(validator).validatePart(part);
        try {
            repo.addPart(part);
            fail("Part added");
        } catch (ValidationException e) {
            Mockito.verify(validator, times(1)).validatePart(part);
        }
    }

    @Test
    void testLookupValidInHousePart() {
        InhousePart part = new InhousePart(1, "test1", 1.0, 7, 5, 10, 1);
        Mockito.doNothing().when(validator).validateSearchString("test1");
        Mockito.doNothing().when(validator).validatePart(part);
        repo.addPart(part);
        Part found = repo.lookupPart("test1");
        Mockito.verify(validator, times(1)).validateSearchString("test1");
        assertEquals(part, found);
    }

    @Test
    void testLookupInvalidInHousePart() {
        InhousePart part = new InhousePart(1, "test1", 1.0, 7, 5, 10, 1);
        Mockito.doThrow(ValidationException.class).when(validator).validatePart(part);
        try {
            repo.lookupPart("");
        } catch (ValidationException e) {
            Mockito.verify(validator, times(1)).validatePart(part);
            assertEquals("A name has not been entered. ", e.getMessage());
        }
    }
}
