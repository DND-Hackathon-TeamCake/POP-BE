package com.cake.pop.global.auth.jwt;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cake.pop.domain.user.exception.UserErrorCode;
import com.cake.pop.domain.user.repository.UserRepository;
import com.cake.pop.entity.User;
import com.cake.pop.global.auth.oauth.CustomOauth2User;
import com.cake.pop.global.exception.RestApiException;
import com.cake.pop.global.redis.util.RedisUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {

	private static final String ROLE_KEY = "ROLE";
	private static final String[] BLACKLIST = new String[] {"false", "delete"};
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 90L;
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24L;
	private final UserRepository userRepository;
	private final RedisUtil redisUtil;
	@Value("${spring.jwt.key}")
	private String key;
	private SecretKey secretKey;

	@PostConstruct
	private void initSecretKey() {
		this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
	}

	public String generateAccessToken(User findUser, CustomOauth2User authentication, Date now) {
		return generateToken(findUser, authentication, ACCESS_TOKEN_EXPIRE_TIME, now);
	}

	public void generateRefreshToken(User findUser, CustomOauth2User authentication, Date now) {
		String refreshToken = generateToken(findUser, authentication, REFRESH_TOKEN_EXPIRE_TIME, now);

		// redis Refresh 저장
		// redisUtil.setValues("RT:" + authentication.getEmail(), refreshToken,
		// 	Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME));
	}

	private String generateToken(User findUser, CustomOauth2User authentication, long tokenExpireTime, Date now) {
		Date expiredTime = createExpiredDateWithTokenType(now, tokenExpireTime);
		String authorities = getAuthorities(authentication);

		return Jwts.builder()
			.subject(String.valueOf(findUser.getId()))
			.claim(ROLE_KEY, authorities)
			.issuedAt(now)
			.expiration(expiredTime)
			.signWith(secretKey, Jwts.SIG.HS512)
			.compact();
	}

	private String getAuthorities(CustomOauth2User authentication) {
		return authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining());
	}

	private Date createExpiredDateWithTokenType(Date date, long tokenExpireTime) {
		return new Date(date.getTime() + tokenExpireTime);
	}

	public Authentication getAuthentication(String token) {
		Claims claims = parseToken(token);
		List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

		String subject = claims.getSubject();
		User principal = userRepository.findById(Long.valueOf(subject))
			.orElseThrow(() -> new RestApiException(UserErrorCode.USER_NOT_FOUND));

		return new UsernamePasswordAuthenticationToken(principal.getId(), token, authorities);
	}

	public boolean validateToken(String token, Date date) {
		if (!StringUtils.hasText(token)) {
			return false;
		}

		Claims claims = parseToken(token);
		if (!claims.getExpiration().after(date)) {
			throw new CustomJwtException(JwtErrorCode.EXPIRED_TOKEN);
		}

		return true;
	}

	private Claims parseToken(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build()
				.parseSignedClaims(token).getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		} catch (MalformedJwtException e) {
			throw new CustomJwtException(JwtErrorCode.MALFORMED_TOKEN);
		} catch (JwtException e) {
			throw new CustomJwtException(JwtErrorCode.INVALID_TOKEN);
		} catch (Exception e) {
			throw new CustomJwtException(JwtErrorCode.INVALID_TOKEN);
		}
	}

	private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
		return Collections.singletonList(new SimpleGrantedAuthority(
			claims.get(ROLE_KEY).toString()
		));
	}

	public Long getExpiration(String token, Date date) {
		Claims claims = parseToken(token);
		Date expiration = claims.getExpiration();
		return (expiration.getTime() - date.getTime());
	}

	public boolean verifyBlackList(String accessToken) {
		String value = redisUtil.getValues(accessToken);
		return Arrays.asList(BLACKLIST).contains(value);
	}

	public User getMemberAllowExpired(String token) {
		Claims claims = parseToken(token);

		String subject = claims.getSubject();
		return userRepository.findById(Long.valueOf(subject))
			.orElseThrow(() -> new RestApiException(UserErrorCode.USER_NOT_FOUND));
	}

}
