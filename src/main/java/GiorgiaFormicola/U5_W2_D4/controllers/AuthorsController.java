package GiorgiaFormicola.U5_W2_D4.controllers;

import GiorgiaFormicola.U5_W2_D4.entities.Author;
import GiorgiaFormicola.U5_W2_D4.payloads.AuthorDTO;
import GiorgiaFormicola.U5_W2_D4.services.AuthorsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorsController {
    private AuthorsService authorsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author saveAuthor(@RequestBody AuthorDTO body) {
        return this.authorsService.save(body);
    }

    @GetMapping
    public Page<Author> getAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "surname") String sortBy
    ) {
        return this.authorsService.findAll(page, size, sortBy);
    }

    @GetMapping("/{authorId}")
    public Author getAuthorById(@PathVariable UUID authorId) {
        return this.authorsService.findById(authorId);
    }

    @PutMapping("/{authorId}")
    public Author getAuthorByIdAndUpdate(@PathVariable UUID authorId, @RequestBody AuthorDTO body) {
        return this.authorsService.findByIdAndUpdate(authorId, body);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAuthorByIdAndDelete(@PathVariable UUID authorId) {
        this.authorsService.findByIdAndDelete(authorId);
    }
}
