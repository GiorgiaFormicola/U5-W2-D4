package GiorgiaFormicola.U5_W2_D4.services;

import GiorgiaFormicola.U5_W2_D4.entities.Author;
import GiorgiaFormicola.U5_W2_D4.exceptions.BadRequestException;
import GiorgiaFormicola.U5_W2_D4.exceptions.NotFoundException;
import GiorgiaFormicola.U5_W2_D4.payloads.AuthorPayload;
import GiorgiaFormicola.U5_W2_D4.repositories.AuthorsRepository;
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
public class AuthorsService {
    private AuthorsRepository authorsRepository;

    public Author save(AuthorPayload body) {
        if (this.authorsRepository.existsByEmail(body.getEmail()))
            throw new BadRequestException("Email address " + body.getEmail() + " already in use!");
        Author newAuthor = new Author(body.getName(), body.getSurname(), body.getEmail(), body.getBirthDate());
        Author savedAuthor = authorsRepository.save(newAuthor);
        log.info("Author with id " + savedAuthor.getId() + " successfully saved");
        return savedAuthor;
    }

    public Page<Author> findAll(int page, int size, String sortBy) {
        if (page < 0) page = 0;
        if (size < 0 || size > 100) size = 5;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.authorsRepository.findAll(pageable);
    }

    public Author findById(UUID authorId) {
        return this.authorsRepository.findById(authorId).orElseThrow(() -> new NotFoundException("author", authorId));
    }

    public Author findByIdAndUpdate(UUID authorId, AuthorPayload body) {
        Author found = this.findById(authorId);
        if (!found.getEmail().equals(body.getEmail())) {
            if (this.authorsRepository.existsByEmail(body.getEmail()))
                throw new BadRequestException("Email address " + body.getEmail() + " already in use!");
        }
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setEmail(body.getEmail());
        found.setBirthDate(body.getBirthDate());
        found.setAvatarURL("https://ui-avatars.com/api/?name=" + body.getName() + "+" + body.getSurname());
        Author updatedAuthor = this.authorsRepository.save(found);
        log.info("Author with id " + updatedAuthor.getId() + " successfully modified");
        return updatedAuthor;
    }

    public void findByIdAndDelete(UUID authorId) {
        Author found = this.findById(authorId);
        this.authorsRepository.delete(found);
    }
}
