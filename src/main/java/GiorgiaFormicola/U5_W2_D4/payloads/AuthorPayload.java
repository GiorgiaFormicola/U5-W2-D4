package GiorgiaFormicola.U5_W2_D4.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AuthorPayload {
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
}
