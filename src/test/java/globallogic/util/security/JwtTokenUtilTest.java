package globallogic.util.security;

import io.jsonwebtoken.Claims;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenUtilTest {
    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetails userDetails;

    private String token;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "testSecretKeyWithAtLeast256BitsForHS512Algorithm");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getPassword()).thenReturn("testPassword");
        //when(userDetails.getAuthorities()).thenReturn(any());
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
        //assertEquals("ROLE_USER", claims.get("role"));
        assertEquals("testPassword", claims.get("password"));
        assertEquals("value-test", claims.get("test"));
    }

    @Test
    void shouldGetClaimFromToken() {
        String subject = jwtTokenUtil.getClaimFromToken(token, Claims::getSubject);
        assertEquals("testUser", subject);
    }

}