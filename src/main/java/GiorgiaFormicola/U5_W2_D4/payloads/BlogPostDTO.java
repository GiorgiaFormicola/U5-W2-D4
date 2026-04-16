package GiorgiaFormicola.U5_W2_D4.payloads;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record BlogPostDTO(
        @NotBlank(message = "Category is mandatory and it can't contain only blank spaces")
        @Size(min = 2, max = 30, message = "Category must contain between 2 and 30 characters")
        String category,
        @NotBlank(message = "Title is mandatory and it can't contain only blank spaces")
        @Size(min = 2, max = 255, message = "Title must contain between 2 and 255 characters")
        String title,
        @NotBlank(message = "Content is mandatory and it can't contain only blank spaces")
        @Size(min = 2, max = 255, message = "Content must contain between 2 and 255 characters")
        String content,
        @NotNull(message = "Reading time is mandatory")
        @Positive(message = "Reading time must be a positive value")
        int readingTime,
        @NotNull(message = "Author id is mandatory")
        @Pattern(
                regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
                message = "Author id must follow a valid UUID format"
        )
        UUID authorId
) {
}
