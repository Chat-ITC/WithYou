package WithYou.domain.post.controller;

import WithYou.domain.post.dto.request.PostRegistDto;
import WithYou.domain.post.service.PostService;
import WithYou.global.jwt.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/post/regist")
    public ResponseEntity<PostRegistDto> registPost(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                    @RequestBody PostRegistDto postRegistDto) {
        postService.savePost(postRegistDto, memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body(postRegistDto);
    }
}
