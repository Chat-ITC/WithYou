package WithYou.domain.post.controller;

import WithYou.domain.comment.dto.response.CommentResponseDto;
import WithYou.domain.comment.entity.Comment;
import WithYou.domain.comment.service.CommentService;
import WithYou.domain.post.dto.response.PostLookupDto;
import WithYou.domain.post.dto.response.PostRegistResponseDto;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.service.PostService;
import WithYou.domain.post.vo.CommentPostVo;
import WithYou.global.jwt.MemberPrincipal;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/post/regist")
    public ResponseEntity<PostRegistResponseDto> registPost(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                            @RequestParam(value = "image", required = false) MultipartFile multipartFile,
                                                            @RequestParam(value = "title") String title,
                                                            @RequestParam(value = "content") String content)
            throws IOException {
        String imageUrl = postService.uploadImage(multipartFile);
        PostRegistResponseDto responseDto = new PostRegistResponseDto(title, content, imageUrl);
        postService.savePost(responseDto, memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @GetMapping("/post/lookup")
    public ResponseEntity<List<PostLookupDto>> lookupPost(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                          @PageableDefault(size = 100, sort = "id", direction = Direction.DESC)
                                                          Pageable pageable) {
        Page<Post> posts = postService.lookupDtoList(memberPrincipal.getMember(), pageable);
        List<PostLookupDto> postLookupDtos = postService.changeToPostLookupDtoList(posts);
        return ResponseEntity.ok()
                .body(postLookupDtos);
    }

    @GetMapping("/post")
    public ResponseEntity<CommentPostVo> findPostById(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                      @RequestParam("id") Long id) throws IOException {
        Post post = postService.findPostAndVerifyMember(id, memberPrincipal.getMember());

        List<Comment> comment = commentService.findCommentByPostId(id);
        List<CommentResponseDto> commentResponseDtoList = commentService.changeCommentListToDtoList(comment);

        PostLookupDto postLookupDto = postService.changePostToDto(post);
        CommentPostVo commentPostVo = new CommentPostVo(commentResponseDtoList,
                postLookupDto);
        return ResponseEntity.ok()
                .body(commentPostVo);
    }
}
