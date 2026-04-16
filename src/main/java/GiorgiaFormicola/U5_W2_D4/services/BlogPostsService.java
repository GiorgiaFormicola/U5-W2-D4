package GiorgiaFormicola.U5_W2_D4.services;

import GiorgiaFormicola.U5_W2_D4.entities.Author;
import GiorgiaFormicola.U5_W2_D4.entities.BlogPost;
import GiorgiaFormicola.U5_W2_D4.exceptions.NotFoundException;
import GiorgiaFormicola.U5_W2_D4.payloads.BlogPostPayload;
import GiorgiaFormicola.U5_W2_D4.repositories.BlogPostsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BlogPostsService {
    private BlogPostsRepository blogPostsRepository;
    private AuthorsService authorsService;

    public BlogPost save(BlogPostPayload body) {
        Author authorFound = this.authorsService.findById(body.getAuthorId());
        BlogPost newBlogPost = new BlogPost(body.getCategory(), body.getTitle(), body.getContent(), body.getReadingTime(), authorFound);
        BlogPost savedBlogPost = this.blogPostsRepository.save(newBlogPost);
        log.info("Blog post with id " + savedBlogPost.getId() + " has been successfully saved!");
        return savedBlogPost;
    }

    public Page<BlogPost> findAll(int page, int size, String sortBy) {
        if (page < 0) page = 0;
        if (size < 0 || size > 100) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.blogPostsRepository.findAll(pageable);
    }

    public BlogPost findById(UUID blogPostId) {
        return this.blogPostsRepository.findById(blogPostId).orElseThrow(() -> new NotFoundException("blog post", blogPostId));
    }

    public BlogPost findByIdAndUpdate(UUID blogPostId, BlogPostPayload body) {
        BlogPost found = this.findById(blogPostId);
        found.setAuthor(this.authorsService.findById(body.getAuthorId()));
        found.setCategory(body.getCategory());
        found.setTitle(body.getTitle());
        found.setContent(body.getContent());
        found.setReadingTime(body.getReadingTime());
        return found;
    }

    public void findByIdAndDelete(UUID blogPostId) {
        BlogPost found = this.findById(blogPostId);
        this.blogPostsRepository.delete(found);
    }
}
