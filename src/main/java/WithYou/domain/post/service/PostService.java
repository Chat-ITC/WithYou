package WithYou.domain.post.service;

import WithYou.domain.member.entity.Member;
import WithYou.domain.member.service.MemberService;
import WithYou.domain.post.dto.request.PostRegistDto;
import WithYou.domain.post.dto.response.PostLookupDto;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.exception.DepartmentNotMatchException;
import WithYou.domain.post.exception.PostNotFoundException;
import WithYou.domain.post.repository.PostQueryRepository;
import WithYou.domain.post.repository.PostReporitoy;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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

    public void savePost(PostRegistDto postRegistDto, Member member) {
        Post post = postRegistDto.toEntity(member);
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
        String s3FileName = setImageName(multipartFile);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objectMetadata);
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    private String setImageName(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "nothing";
        } else {
            return UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        }
    }

    public ByteArrayResource getImageUrlResource(String imageUrl) throws IOException {
        if ("nothing".equals(imageUrl)) {
            return new ByteArrayResource(new byte[0]);
        }
        return getImageUrl(imageUrl);
    }

    public ByteArrayResource getImageUrl(String imageUrl) throws IOException {
        S3ObjectInputStream inputStream = getImageFromAmazonS3(imageUrl);
        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        return new ByteArrayResource(imageBytes);
    }

    private S3ObjectInputStream getImageFromAmazonS3(String imageUrl) {
        S3Object s3Object = amazonS3.getObject(bucket, imageUrl);
        return s3Object.getObjectContent();
    }
}
