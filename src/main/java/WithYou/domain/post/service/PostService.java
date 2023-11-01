package WithYou.domain.post.service;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.dto.request.PostRegistDto;
import WithYou.domain.post.dto.response.PostLookupDto;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.exception.DepartmentNotMatchException;
import WithYou.domain.post.exception.PostNotFoundException;
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
        member.upgradeMemberLevel();
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

    public Post findPostAndVerifyMember(Long id, Member member) {
        Post post = postReporitoy.findPostById(id).orElseThrow(() -> new PostNotFoundException());
        verifyDepartmentMatch(post, member);
        return post;
    }

    private void verifyDepartmentMatch(Post post, Member member) {
        if (!post.getUserMajor().equals(member.getMajor())) {
            throw new DepartmentNotMatchException();
        }
    }

    public PostLookupDto changePostToDto(Post post) {
        PostLookupDto postLookupDto = PostLookupDto.of(post);
        return postLookupDto;
    }

    public Post findPostById(Long id) {
        return postReporitoy.findPostById(id).orElseThrow(() -> new PostNotFoundException());
    }
}
