package WithYou.domain.member.controller;

import WithYou.domain.member.dto.request.MemberLoginDto;
import WithYou.domain.member.dto.request.MemberSignupDto;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
}
