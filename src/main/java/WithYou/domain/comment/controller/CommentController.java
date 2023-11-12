package WithYou.domain.comment.controller;

import WithYou.domain.comment.dto.request.CommentRegistDto;
import WithYou.domain.comment.entity.Comment;
import WithYou.domain.comment.service.CommentService;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.service.PostService;
import WithYou.domain.post.vo.CommentPostVo;
import WithYou.global.jwt.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/comment/regist/{id}")
    public ResponseEntity<CommentPostVo> registComment(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                          @RequestBody CommentRegistDto commentRegistDto,
                                                          @PathVariable Long id) {
        Post post = postService.findPostAndVerifyMember(id, memberPrincipal.getMember());
        commentService.registComment(post, commentRegistDto, memberPrincipal.getMember());
        CommentPostVo resultVo = commentService.createResponseDto(post);
        return ResponseEntity.ok()
                .body(resultVo);
    }
}
