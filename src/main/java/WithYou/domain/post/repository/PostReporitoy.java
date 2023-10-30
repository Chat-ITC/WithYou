package WithYou.domain.post.repository;

import WithYou.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReporitoy extends JpaRepository<Post, Long> {

}
