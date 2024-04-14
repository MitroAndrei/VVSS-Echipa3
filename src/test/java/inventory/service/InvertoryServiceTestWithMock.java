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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;

public class InvertoryServiceTestWithMock {
    @Mock
    private IInventory repo;

    @InjectMocks
    private InventoryService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddValidInHousePart() {
        Mockito.doNothing().when(repo).addPart(Mockito.any());
        service.addInhousePart("piesa", 1.0, 7, 5, 10, 1);
        Mockito.verify(repo, times(1)).addPart(Mockito.any());
    }

    @Test
    void testAddInvalidInHousePart() {
        Mockito.doThrow(ValidationException.class).when(repo).addPart(Mockito.any());
        try {
            service.addInhousePart("", 1.0, 7, 5, 10, 1);
        } catch (ValidationException e) {
            Mockito.verify(repo, times(0)).addPart(Mockito.any());
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
