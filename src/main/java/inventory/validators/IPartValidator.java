package inventory.validators;

import inventory.model.Part;

public interface IPartValidator {
    void validatePart(Part part);

    void validateSearchString(String searchItem);
}
