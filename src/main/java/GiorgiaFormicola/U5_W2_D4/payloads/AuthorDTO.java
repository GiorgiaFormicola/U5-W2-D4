package GiorgiaFormicola.U5_W2_D4.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AuthorDTO(
        @NotBlank(message = "Name is mandatory and it can't contain only blank spaces")
        @Size(min = 2, max = 30, message = "Name must contain between 2 and 30 characters")
        String name,
        @NotBlank(message = "Surname is mandatory and it can't contain only blank spaces")
        @Size(min = 2, max = 30, message = "Surname must contain between 2 and 30 characters")
        String surname,
        @NotNull(message = "Email is mandatory")
        @Email(message = "Email must follow a valid email format")
        String email,
        @NotNull(message = "Birth date is mandatory")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate
) {
}
