package GiorgiaFormicola.U5_W2_D4.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "blog_posts")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogPost {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String title;

    @Column(name = "cover_url", nullable = false)
    private String coverURL;

    @Column(nullable = false)
    private String content;

    @Column(name = "reading_time", nullable = false)
    private int readingTime;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public BlogPost(String category, String title, String content, int readingTime, Author author) {
        this.category = category;
        this.title = title;
        this.coverURL = "https://picsum.photos/800/300";
        this.content = content;
        this.readingTime = readingTime;
        this.author = author;
    }
}
