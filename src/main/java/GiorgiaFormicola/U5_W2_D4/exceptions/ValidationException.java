package GiorgiaFormicola.U5_W2_D4.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private List<String> errorsList;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<String> errorsList) {
        super("Errors in the validation process");
        this.errorsList = errorsList;
    }
}
