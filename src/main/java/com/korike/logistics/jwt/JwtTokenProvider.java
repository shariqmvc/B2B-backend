package com.korike.logistics.jwt;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.korike.logistics.controller.AuthenticationController;
import com.korike.logistics.entity.BlocklistedUserTokens;
import com.korike.logistics.entity.User;
import com.korike.logistics.repository.BlocklistedTokenRepository;
import com.korike.logistics.repository.UserRepository;
import com.korike.logistics.util.CommonUtils;
import io.jsonwebtoken.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.korike.logistics.common.Constants;
import com.korike.logistics.exception.InvalidJwtAuthenticationException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Component
public class JwtTokenProvider {

    private static Logger logger = Logger.getLogger(JwtTokenProvider.class);
    @Autowired
    BlocklistedTokenRepository blocklistedTokenRepository;

    @Autowired
    private UserRepository userRepository;


	@Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds; // 1h

    @Value("${security.jwt.token.expire-length}")
    private long refreshValInMilliseconds;// 1h

	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
	
	public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, secretKey)//
            .compact();
    }
	
	public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
	
	public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
	
	public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    public String createRefreshToken(String token, String username, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValInMilliseconds);
        String genToken = Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
        //invalidateToken(token);
        return genToken;

    }

    public boolean invalidateToken(String token) throws NoSuchAlgorithmException {

        BlocklistedUserTokens blocklistedUserTokens = new BlocklistedUserTokens();
            if(!token.equals(Constants.TEST_TOKEN)) {
                try {
                    Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                    claims.getBody().clear();
                    User exisitingUser = userRepository.findByAuthToken(CommonUtils.toHexString(CommonUtils.getSHA(token)));
                    if(null!=exisitingUser) {
                        logger.info("Invalidating token.");
                        blocklistedUserTokens.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        blocklistedUserTokens.setToken(CommonUtils.toHexString(CommonUtils.getSHA(token)));
                        blocklistedUserTokens.setUser_id(exisitingUser.getUserId());
                        blocklistedTokenRepository.save(blocklistedUserTokens);
                        }

                }catch(ExpiredJwtException e){
                    System.out.println("Token has already expired or is invalid");
                    return true;
                }
            }
            return true;
    }

    public boolean validateToken(String token, ServletRequest req) throws NoSuchAlgorithmException {
        try {
            if(!token.equals(Constants.TEST_TOKEN)) {
                User exisitingUser = userRepository.findByAuthToken(CommonUtils.toHexString(CommonUtils.getSHA(token)));
                if(null!=exisitingUser) {
                    if (blocklistedTokenRepository.getBlocklistedUserToken(exisitingUser.getUserId(), CommonUtils.toHexString(CommonUtils.getSHA(token))) != null) {
                        logger.info("Token blocklisted.");
                        return false;
                    }
                } else {
                    logger.info("User logged out or not present");
                    return false;
                }
                Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                if (claims.getBody().getExpiration().before(new Date())) {
                    return false;
                }
            }
            return true;
        } catch (ExpiredJwtException e) {
//            String isRefreshToken = ((HttpServletRequest) req).getHeader("isRefreshToken");
            String requestURL = ((HttpServletRequest) req).getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (/*isRefreshToken != null && isRefreshToken.equals("true") && */requestURL.contains("refreshtoken")) {
                allowForRefreshToken(e, req);
                return true;
            }
                throw new InvalidJwtAuthenticationException("Authentication Failed: Token has expired or is invalid");
        }
    }
    private void allowForRefreshToken(ExpiredJwtException e, ServletRequest req) {

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        req.setAttribute("claims", e.getClaims());

    }


}
