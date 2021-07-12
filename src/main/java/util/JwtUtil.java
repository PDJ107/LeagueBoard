package util;

import annotation.Auth;
import exception.AuthException;
import exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class JwtUtil {

    @Value("${json.web.token.secret.key}")
    String secret;

    public Long getIdFromToken(String token) throws Exception{
        if ( token == null) {
            //throw new Exception("null임");
            throw new AuthException(ErrorCode.Token_Is_Null);
        }
        else if ( !token.startsWith("Bearer ") ){
            //throw new Exception("Bearer 로 시작안함");
            throw new AuthException(ErrorCode.Invalid_Token_Bearer);
        }
        token = token.substring(7); // "Bearer " 제거

        // JWT Exception
        // ExpiredJwtException : JWT 유효기간 초과
        // UnsupportedJwtException : 예상하는 형식과 다른 형식이나 구성
        // MalformedJwtException : JWT가 올바르지 않을 때
        // SignatureException : JWT의 기존서명 확인이 안될 때

        Long id = 0L;
        try {
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
            id = claims.get("id", Long.class);

        }catch (ExpiredJwtException e1){
            //throw new Exception("만료됨");
            throw new AuthException(ErrorCode.Expired_Token);
        }
        catch(Throwable e2){
            //throw new Exception("잘못됨");
            throw new AuthException(ErrorCode.Invalid_Token);
        }
        return id;
    }

    /** 유저의 id값을 payload에 담아 만들어진 토큰을 통해 유저를 인증합니다 */
    public String genJsonWebToken(Long id){
        Map<String, Object> headers = new HashMap<String, Object>(); // header
        headers.put("typ", "JWT");
        headers.put("alg","HS256");
        Map<String, Object> payloads = new HashMap<String, Object>(); //payload
        payloads.put("id", id );
        Calendar calendar = Calendar.getInstance(); // singleton object java calendar
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 24); // access token expire 24h later
        Date exp = calendar.getTime();

        String token = Jwts.builder().
                setHeader(headers).
                setClaims(payloads).
                setExpiration(exp).
                signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();

        return token;
    }

    public boolean isValid(String token) throws Exception{
        if ( token == null) {
            throw new AuthException(ErrorCode.Token_Is_Null);
        }
        else if ( !token.startsWith("Bearer ") ){
            throw new AuthException(ErrorCode.Invalid_Token_Bearer);
        }

        token = token.substring(7); // "Bearer " 제거
        try {
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e1){
            throw new AuthException(ErrorCode.Expired_Token);
        }
        catch(Throwable e2){
            throw new AuthException(ErrorCode.Invalid_Token);
        }
        return true;
    }
}

