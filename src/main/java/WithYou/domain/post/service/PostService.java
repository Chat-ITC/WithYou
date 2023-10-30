package WithYou.domain.post.service;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.dto.request.PostRegistDto;
import WithYou.domain.post.dto.response.PostLookupDto;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.repository.PostQueryRepository;
import WithYou.domain.post.repository.PostReporitoy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostReporitoy postReporitoy;
    private final PostQueryRepository postQueryRepository;

    public void savePost(PostRegistDto postRegistDto, Member member) {
        Post post = postRegistDto.toEntity(member);
        postReporitoy.save(post);
    }

    public Page<Post> lookupDtoList(Member member, Pageable pageable) {
        return postQueryRepository.findPostByMemberGrade(pageable, member);

    }

    public List<PostLookupDto> changeToPostLookupDtoList(Page<Post> posts) {
        return posts.stream()
                .map(PostLookupDto::of)
                .collect(Collectors.toList());
    }

}
