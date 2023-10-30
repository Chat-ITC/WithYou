package WithYou.domain.post.repository;

import WithYou.domain.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReporitoy extends JpaRepository<Post, Long> {
    Optional<Post> findPostById(Long id);
}
