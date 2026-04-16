package GiorgiaFormicola.U5_W2_D4.payloads;

import java.util.UUID;

public record BlogPostDTO(
        String category,
        String title,
        String content,
        int readingTime,
        UUID authorId
) {
}
