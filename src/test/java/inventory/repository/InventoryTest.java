package inventory.repository;

import inventory.errors.RepositoryException;
import inventory.errors.ValidationException;
import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.validators.PartValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;
    private final String searchItemValid = "piesa_test";

    private final Part partToFind = new InhousePart(100, searchItemValid, 10.0, 10, 1, 100, 1);

    @BeforeEach
    void setUp() {
        PartValidator validator = new PartValidator();
        inventory = new Inventory(validator);
    }

    @Test
    void lookupPartValid() {
        generateParts(1);
        inventory.addPart(partToFind);
        List<Part> part = inventory.lookupParts(searchItemValid);
        assertNotNull(part, "Part not found");
        assertNotEquals(0, part.size(), "Part not found");
        assertEquals(1, part.size(), "More than one part found");
        assertEquals(partToFind, part.get(0), "Part not found");
    }

    @Test
    void lookupPartNonexistent() {
        generateParts(1);
        String searchItemNonexistent = "non_existent";
        try {
            inventory.lookupParts(searchItemNonexistent);
            fail("Part found");
        } catch (RepositoryException e){
            assertEquals("No parts found", e.getMessage(), "Wrong exception message");
        }
    }

    @Test
    void lookupPartEmpty() {
        String searchItemNoParts = "no_parts";
        try {
            inventory.lookupParts(searchItemNoParts);
            fail("Part found");
        } catch (RepositoryException e){
            assertEquals("No parts found", e.getMessage(), "Wrong exception message");
        }
    }

    @Test
    void lookupPartEmptyString() {
        String searchItemEmpty = "";
        try {
            inventory.lookupParts(searchItemEmpty);
            fail("Part found");
        } catch (ValidationException e){
            assertEquals("Search string is empty", e.getMessage(), "Wrong exception message");
        }
    }

    void generateParts(int n){
        for(int i=0; i<n; i++){
            inventory.addPart(new InhousePart(i, "Piesa"+i, 10.0, 10, 1, 100, i));
        }
    }


    @AfterEach
    void tearDown() {
        inventory = null;
    }
}