package WithYou.domain.comment.controller;

import WithYou.domain.comment.dto.request.CommentRegistDto;
import WithYou.domain.comment.service.CommentService;
import WithYou.global.jwt.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/regist/{id}")
    public ResponseEntity<?> registComment(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                           @RequestBody CommentRegistDto commentRegistDto,
                                           @PathVariable Long id) {
        commentService.registComment(id, commentRegistDto, memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body(commentRegistDto);
    }
}
