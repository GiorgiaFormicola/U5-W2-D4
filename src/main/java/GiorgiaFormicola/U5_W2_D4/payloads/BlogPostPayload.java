package GiorgiaFormicola.U5_W2_D4.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BlogPostPayload {
    private String category;
    private String title;
    private String content;
    private int readingTime;
    private UUID authorId;
}
