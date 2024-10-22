package globallogic.util.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtRequestFilterTest {
    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProcessValidJwtToken() throws ServletException, IOException {
        // Arrange
        String token = "valid.jwt.token";
        String username = "testuser";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        when(jwtUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtTokenUtil.validateToken(token, userDetails)).thenReturn(true);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtUserDetailsService).loadUserByUsername(username);
        verify(jwtTokenUtil).validateToken(token, userDetails);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldHandleInvalidJwtToken() throws ServletException, IOException {
        // Arrange
        String token = "invalid.jwt.token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtTokenUtil.getUsernameFromToken(token)).thenThrow(new RuntimeException("Invalid token"));

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(request).setAttribute("exception", "Invalid token");
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldHandleNoAuthorizationHeader() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtTokenUtil);
        verifyNoInteractions(jwtUserDetailsService);
    }

    @Test
    void shouldHandleNonBearerToken() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNzd29yZA==");

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtTokenUtil);
        verifyNoInteractions(jwtUserDetailsService);
    }

    @Test
    void shouldHandleInvalidTokenFormat() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer ");

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        //verifyNoInteractions(jwtTokenUtil);
        verifyNoInteractions(jwtUserDetailsService);
    }

}