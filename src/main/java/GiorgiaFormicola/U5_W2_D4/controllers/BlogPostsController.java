package GiorgiaFormicola.U5_W2_D4.controllers;

import GiorgiaFormicola.U5_W2_D4.entities.BlogPost;
import GiorgiaFormicola.U5_W2_D4.payloads.BlogPostDTO;
import GiorgiaFormicola.U5_W2_D4.services.BlogPostsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blogPosts")
@AllArgsConstructor
public class BlogPostsController {
    private final BlogPostsService blogPostsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost saveBlogPost(@RequestBody BlogPostDTO body) {
        return this.blogPostsService.save(body);
    }

    @GetMapping
    public Page<BlogPost> getBlogPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy
    ) {
        return this.blogPostsService.findAll(page, size, sortBy);
    }

    @GetMapping("/{blogPostId}")
    public BlogPost getBlogPostById(@PathVariable UUID blogPostId) {
        return this.blogPostsService.findById(blogPostId);
    }

    @PutMapping("/{blogPostId}")
    public BlogPost getBlogPostByIdAndUpdate(@PathVariable UUID blogPostId, @RequestBody BlogPostDTO body) {
        return this.blogPostsService.findByIdAndUpdate(blogPostId, body);
    }

    @DeleteMapping("/{blogPostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getBlogPostByIdAndDelete(@PathVariable UUID blogPostId) {
        this.blogPostsService.findByIdAndDelete(blogPostId);
    }
}
