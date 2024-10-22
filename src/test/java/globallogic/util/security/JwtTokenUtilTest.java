package globallogic.util.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenUtilTest {
    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetails userDetails;

    private String token;
    private Key key;

    @BeforeEach
    void setUp() {
        // Generar una clave segura para HS512
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", base64Key);

        // Verificar la longitud de la clave
        byte[] keyBytes = key.getEncoded();
        assertTrue(keyBytes.length >= 64, "La clave debe tener al menos 64 bytes para HS512");

        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getPassword()).thenReturn("testPassword");
        doReturn(Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        )).when(userDetails).getAuthorities();
        token = jwtTokenUtil.generateToken(userDetails);
    }

    @Test
    void shouldGenerateToken() {
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void shouldGetUsernameFromToken() {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        assertEquals("testUser", username);
    }

    @Test
    void shouldGetPasswordFromToken() {
        String password = jwtTokenUtil.getPasswordFromToken(token);
        assertEquals("testPassword", password);
    }

    @Test
    void shouldGetExpirationDateFromToken() {
        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void shouldValidateToken() {
        boolean isValid = jwtTokenUtil.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void shouldGetAllClaimsFromToken() {
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        assertNotNull(claims);
        assertEquals("testUser", claims.getSubject());
        assertEquals("ROLE_USER", claims.get("role"));
        assertEquals("testPassword", claims.get("password"));
        assertEquals("value-test", claims.get("test"));
    }

    @Test
    void shouldGetClaimFromToken() {
        String subject = jwtTokenUtil.getClaimFromToken(token, Claims::getSubject);
        assertEquals("testUser", subject);
    }

//    @Test
//    void shouldNotValidateTokenWhenTokenIsExpired() {
//        String expiredToken = Jwts.builder()
//                .setSubject("testUser")
//                .setIssuedAt(new Date(System.currentTimeMillis() - (10 * 60 * 1000))) // Hace 10 minutos
//                .setExpiration(new Date(System.currentTimeMillis() - (5 * 60 * 1000))) // Expiró hace 5 minutos
//                .signWith(key, SignatureAlgorithm.HS512)
//                .compact();
//
//        boolean isValid = jwtTokenUtil.validateToken(expiredToken, userDetails);
//
//        assertFalse(isValid);
//    }

    @Test
    void shouldNotValidateTokenWhenUsernameDoesNotMatch() {
        String differentUserToken = Jwts.builder()
                .setSubject("otherUser")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (5 * 60 * 1000))) // Expira en 5 minutos
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        boolean isValid = jwtTokenUtil.validateToken(differentUserToken, userDetails);

        assertFalse(isValid);
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid() {
        String invalidToken = "thisIsAnInvalidToken";

        assertThrows(JwtException.class, () -> {
            jwtTokenUtil.getUsernameFromToken(invalidToken);
        });
    }

    @Test
    void shouldThrowExceptionWhenGetAllClaimsFromInvalidToken() {
        String invalidToken = "thisIsAnInvalidToken";

        assertThrows(JwtException.class, () -> {
            jwtTokenUtil.getAllClaimsFromToken(invalidToken);
        });
    }

    @Test
    void shouldReturnNullWhenPasswordClaimIsMissing() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        claims.put("test", "value-test");

        String tokenWithoutPassword = Jwts.builder()
                .setClaims(claims)
                .setSubject("testUser")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (5 * 60 * 1000))) // Expira en 5 minutos
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String password = jwtTokenUtil.getPasswordFromToken(tokenWithoutPassword);

        assertNull(password);
    }

    @Test
    void shouldGenerateTokenWithAuthorities() {
        Collection<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );
        doReturn(authorities).when(userDetails).getAuthorities();

        String newToken = jwtTokenUtil.generateToken(userDetails);

        assertNotNull(newToken);
        assertTrue(newToken.length() > 0);

        Claims claims = jwtTokenUtil.getAllClaimsFromToken(newToken);
        String role = (String) claims.get("role");
        String expectedRoles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        assertEquals(expectedRoles, role);
    }

    @Test
    void shouldHandleNullAuthoritiesGracefully() {
        when(userDetails.getAuthorities()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            jwtTokenUtil.generateToken(userDetails);
        });
    }

    @Test
    void shouldHandleEmptyAuthoritiesGracefully() {
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        String newToken = jwtTokenUtil.generateToken(userDetails);

        assertNotNull(newToken);
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(newToken);
        String role = (String) claims.get("role");
        assertEquals("", role);
    }

    @Test
    void shouldReturnNullWhenGettingNonExistingClaim() {
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());
        String newToken = jwtTokenUtil.generateToken(userDetails);

        Object nonExistingClaim = jwtTokenUtil.getAllClaimsFromToken(newToken).get("nonExistingClaim");

        assertNull(nonExistingClaim);
    }

}