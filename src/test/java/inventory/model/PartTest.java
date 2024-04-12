package inventory.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    @Test
    void isValidPart() {
        Part part = new InhousePart(1, "test", 10.0, 10, 1, 100, 1);
        String errorMsj = "";
        assertEquals(part.isValidPart("test", 10.0, 10, 1, 100, errorMsj), errorMsj);
    }

    @Test
    void isInvalidNamedPart() {
        Part part = new InhousePart(1, "test", 10.0, 10, 1, 100, 1);
        String errorMsj = "";
        assertEquals(part.isValidPart("", 10.0, 10, 1, 100, errorMsj), "A name has not been entered. ");
    }

    @Test
    void isInvalidPricedPart() {
        Part part = new InhousePart(1, "test", 10.0, 10, 1, 100, 1);
        String errorMsj = "";
        assertEquals(part.isValidPart("test", 10.0, 10, 1, 100, errorMsj), "The price must be greater than 0.0 ");
    }

}