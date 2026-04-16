package GiorgiaFormicola.U5_W2_D4.payloads;

import java.time.LocalDateTime;

public record ErrorDTO(
        String message,
        LocalDateTime timestamp
) {
}
