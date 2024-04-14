package inventory.validators;

import inventory.errors.ValidationException;
import inventory.model.Part;

public class PartValidator implements IPartValidator {
    @Override
    public void validatePart(Part part) {
        String errorMessage="";
        if (part.getName().equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if (part.getPrice() < 0.00) {
            errorMessage += "The price must be greater than 0.0 ";
        }
        if (part.getInStock() < 1) {
            errorMessage += "Inventory level must be greater than 0. ";
        }
        if (part.getMin() > part.getMax()) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if (part.getInStock() < part.getMin()) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if (part.getInStock()> part.getMax()) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        if (!errorMessage.equals("")) {
            throw new ValidationException(errorMessage);
        }
    }

    @Override
    public void validateSearchString(String searchItem) {
        if (searchItem.equals("")) {
            throw new ValidationException("Search string cannot be empty.");
        }
    }
}
