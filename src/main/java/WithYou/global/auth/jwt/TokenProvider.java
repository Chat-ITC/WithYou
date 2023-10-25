package WithYou.global.auth.jwt;


import WithYou.domain.member.entity.Member;
import WithYou.domain.member.exception.MemberNotFoundException;
import WithYou.domain.member.repository.MemberRepository;
import WithYou.global.auth.exception.TokenDecodeException;
import WithYou.global.auth.exception.TokenException;
import WithYou.global.auth.exception.TokenExpiredException;
import WithYou.global.auth.exception.TokenUnsupportedException;
import io.jsonwebtoken.*;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

	private final MemberRepository memberRepository;
	@Value("${JWT_SECRET_KEY}")
	private String JWT_SECRET_KEY;

	private final Long accessTokenValidTime = 1000 * 60L * 60 * 24L; // 하루
	//    private final Long accessTokenValidTime = 30000L;
	private final Long refreshTokenValidTime = 1000 * 60 * 60 * 24 * 7L; // 1주

	//AccessToken 생성
	public String createAccessToken(Long id) {
		Claims claims = Jwts.claims().setSubject("accessToken");
		claims.put("id", id);
		Date currentTime = new Date();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(currentTime)
			.setExpiration(new Date(currentTime.getTime() + accessTokenValidTime))
			.signWith(SignatureAlgorithm.HS256, Base64Utils.encodeToString(JWT_SECRET_KEY.getBytes()))
			.compact();
	}

	//RefreshToken 생성
	public String createRefreshToken(Long id) {
		Claims claims = Jwts.claims().setSubject("refreshToken");
		claims.put("id", id);
		Date currentTime = new Date();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(currentTime)
			.setExpiration(new Date(currentTime.getTime() + refreshTokenValidTime))
			.signWith(SignatureAlgorithm.HS256, Base64Utils.encodeToString(JWT_SECRET_KEY.getBytes()))
			.compact();
	}

	//AccessToken 유효성 검사
	public Boolean validateAccessToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Base64Utils.encodeToString(JWT_SECRET_KEY.getBytes()))
				.build()
				.parseClaimsJws(token).getBody();
			return true;
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		} catch (UnsupportedJwtException e) {
			throw new TokenUnsupportedException();
		} catch (Exception e) {
			// 여기에서 예외 메시지를 설정해야 합니다.
			throw new TokenException();
		}
	}

	//User의 정보를 가져온다.
	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		//@AuthenticationPrincipal에서 필요한 정보 여기에 담기
		Long id = getIdFromToken(token);
		Member member = memberRepository.findMemberById(id)
			.orElseThrow(() -> new MemberNotFoundException());
		MemberPrincipal memberPrincipal = new MemberPrincipal(member);
		return new UsernamePasswordAuthenticationToken(memberPrincipal, token,
			member.getAuthorities());
	}

	//Token으로부터 Id 추출
	public Long getIdFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Base64Utils.encodeToString(JWT_SECRET_KEY.getBytes()))
			.build().parseClaimsJws(token)
			.getBody()
			.get("id",
				Long.class);
	}

	//Jwt Token의 유효성 검사
	public Claims decodeJwtToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
				.setSigningKey(Base64Utils.encodeToString(JWT_SECRET_KEY.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody();
			return claims;
		} catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				 | IllegalStateException e) {
			throw new TokenDecodeException();
		}
	}
}
