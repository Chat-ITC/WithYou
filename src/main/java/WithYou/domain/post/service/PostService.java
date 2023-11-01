package WithYou.domain.post.service;

import WithYou.domain.member.entity.Member;
import WithYou.domain.member.service.MemberService;
import WithYou.domain.post.dto.response.PostLookupDto;
import WithYou.domain.post.dto.response.PostRegistResponseDto;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.exception.DepartmentNotMatchException;
import WithYou.domain.post.exception.PostNotFoundException;
import WithYou.domain.post.repository.PostQueryRepository;
import WithYou.domain.post.repository.PostReporitoy;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberService memberService;
    private final PostReporitoy postReporitoy;
    private final PostQueryRepository postQueryRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void savePost(PostRegistResponseDto responseDto, Member member) {
        Post post = responseDto.toEntity(member);
        memberService.upgradeMemberLevelUp(member);
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

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "nothing";
        }
        return uploadImageToS3(multipartFile);
    }

    private String uploadImageToS3(MultipartFile multipartFile) throws IOException {
        String s3FileName = generateS3FileName(multipartFile);
        ObjectMetadata objectMetadata = createObjectMetadata(multipartFile);
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objectMetadata);
        return getS3FileUrl(s3FileName);
    }

    private String generateS3FileName(MultipartFile multipartFile) {
        return UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
    }

    private ObjectMetadata createObjectMetadata(MultipartFile multipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());
        return objectMetadata;
    }

    private String getS3FileUrl(String s3FileName) {
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

}
