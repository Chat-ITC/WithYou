package WithYou.domain.post.service;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.dto.request.PostRegistDto;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.repository.PostReporitoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostReporitoy postReporitoy;

    public void savePost(PostRegistDto postRegistDto, Member member) {
        Post post = postRegistDto.toEntity(member);
        postReporitoy.save(post);
    }

}
