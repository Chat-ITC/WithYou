package WithYou.domain.comment.service;

import WithYou.domain.comment.dto.request.CommentRegistDto;
import WithYou.domain.comment.dto.response.CommentResponseDto;
import WithYou.domain.comment.entity.Comment;
import WithYou.domain.comment.repository.CommentRepository;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.service.MemberService;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.exception.PostNotFoundException;
import WithYou.domain.post.repository.PostReporitoy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostReporitoy postReporitoy;

    public void registComment(Post post, CommentRegistDto commentRegistDto, Member member) {
        pluscommentCount(post);
        Comment comment = commentRegistDto.of(member, post);
        memberService.upgradeMemberLevelUp(member);
        commentRepository.save(comment);
    }

    public List<CommentResponseDto> changeCommentListToDtoList(List<Comment> commentList) {
        return commentList.stream()
                .map(CommentResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<Comment> findCommentByPostId(Long id) {
        Post post = postReporitoy.findPostById(id).orElseThrow(() -> new PostNotFoundException());
        return post.getCommentList();
    }

    private void pluscommentCount(Post post) {
        post.plusCommentCount();
    }
}
