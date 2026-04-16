package GiorgiaFormicola.U5_W2_D4.controllers;

import GiorgiaFormicola.U5_W2_D4.entities.Author;
import GiorgiaFormicola.U5_W2_D4.exceptions.ValidationException;
import GiorgiaFormicola.U5_W2_D4.payloads.AuthorDTO;
import GiorgiaFormicola.U5_W2_D4.services.AuthorsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorsController {
    private AuthorsService authorsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author saveAuthor(@RequestBody @Validated AuthorDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errorsList);
        }
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
    public Author getAuthorByIdAndUpdate(@PathVariable UUID authorId, @RequestBody @Validated AuthorDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errorsList);
        }
        return this.authorsService.findByIdAndUpdate(authorId, body);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAuthorByIdAndDelete(@PathVariable UUID authorId) {
        this.authorsService.findByIdAndDelete(authorId);
    }

    @PatchMapping("/{authorId}/avatar")
    public Author getAuthorByIdAndUploadAvatar(@PathVariable UUID authorId, @RequestParam("avatar_picture") MultipartFile file) {
        return this.authorsService.findByIdAndUploadAvatar(authorId, file);
    }
}
