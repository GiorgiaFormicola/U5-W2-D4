package GiorgiaFormicola.U5_W2_D4.repositories;

import GiorgiaFormicola.U5_W2_D4.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogPostsRepository extends JpaRepository<BlogPost, UUID> {
}
