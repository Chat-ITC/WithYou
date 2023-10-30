package WithYou.domain.post.controller;

import WithYou.domain.post.dto.request.PostRegistDto;
import WithYou.domain.post.dto.response.PostLookupDto;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.service.PostService;
import WithYou.global.jwt.MemberPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/post/lookup")
    public ResponseEntity<?> lookupPost(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                        @PageableDefault(size = 5, sort = "lastModifiedDate", direction = Direction.ASC)
                                        Pageable pageable) {
        Page<Post> posts = postService.lookupDtoList(memberPrincipal.getMember(), pageable);
        List<PostLookupDto> postLookupDtos = postService.changeToPostLookupDtoList(posts);
        return ResponseEntity.ok()
                .body(postLookupDtos);
    }

    @GetMapping("/post")
    public ResponseEntity<?> findPostById(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                          @RequestParam("id") Long id) {
        Post post = postService.findPostAndVerifyMember(id, memberPrincipal.getMember());
        PostLookupDto postLookupDto = postService.changePostToDto(post);
        return ResponseEntity.ok()
                .body(postLookupDto);
    }
}
