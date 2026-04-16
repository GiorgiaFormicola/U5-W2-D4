package GiorgiaFormicola.U5_W2_D4.services;

import GiorgiaFormicola.U5_W2_D4.entities.Author;
import GiorgiaFormicola.U5_W2_D4.exceptions.BadRequestException;
import GiorgiaFormicola.U5_W2_D4.exceptions.NotFoundException;
import GiorgiaFormicola.U5_W2_D4.exceptions.ValidationException;
import GiorgiaFormicola.U5_W2_D4.payloads.AuthorDTO;
import GiorgiaFormicola.U5_W2_D4.repositories.AuthorsRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorsService {
    private AuthorsRepository authorsRepository;
    private Cloudinary cloudinary;

    public Author save(AuthorDTO body) {
        if (this.authorsRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email address " + body.email() + " already in use!");
        Author newAuthor = new Author(body.name(), body.surname(), body.email(), body.birthDate());
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

    public Author findByIdAndUpdate(UUID authorId, AuthorDTO body) {
        Author found = this.findById(authorId);
        if (!found.getEmail().equals(body.email())) {
            if (this.authorsRepository.existsByEmail(body.email()))
                throw new BadRequestException("Email address " + body.email() + " already in use!");
        }
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setBirthDate(body.birthDate());
        found.setAvatarURL("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        Author updatedAuthor = this.authorsRepository.save(found);
        log.info("Author with id " + updatedAuthor.getId() + " successfully modified");
        return updatedAuthor;
    }

    public void findByIdAndDelete(UUID authorId) {
        //Cancella prima tutti i posti annessi
        Author found = this.findById(authorId);
        this.authorsRepository.delete(found);
    }

    public Author findByIdAndUploadAvatar(UUID authorId, MultipartFile file) {
        if (file.getContentType() == null || !file.getContentType().startsWith("image/") || file.isEmpty())
            throw new ValidationException("Invalid type of file provided");
        if (file.getSize() > 2 * 1024 * 1024)
            throw new ValidationException("File size must be smaller than 2 MB");
        Author found = findById(authorId);
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            found.setAvatarURL((String) result.get("secure_url"));
            Author updatedAuthor = this.authorsRepository.save(found);
            return updatedAuthor;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
