package globallogic.util.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        // No need for MockitoAnnotations.openMocks(this) when using @ExtendWith
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldReturnTrueWhenUserHasAccess() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        doReturn(Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        )).when(authentication).getAuthorities();

        // Act
        boolean result = authService.hasAccess("/some-path");

        // Assert
        assertTrue(result);
        verify(authentication).getName();
        verify(authentication).getAuthorities();
    }

    @Test
    void shouldReturnTrueWhenUserHasNoAuthorities() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        doReturn(Collections.emptyList()).when(authentication).getAuthorities();

        // Act
        boolean result = authService.hasAccess("/some-path");

        // Assert
        assertTrue(result);
        verify(authentication).getName();
        verify(authentication).getAuthorities();
    }

//    @Test
//    void shouldReturnTrueWhenAuthenticationIsNull() {
//        // Arrange
//        when(securityContext.getAuthentication()).thenReturn(null);
//
//        // Act
//        boolean result = authService.hasAccess("/some-path");
//
//        // Assert
//        assertTrue(result);
//    }

    @Test
    void shouldIgnorePathParameter() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        doReturn(Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        )).when(authentication).getAuthorities();

        // Act
        boolean result = authService.hasAccess(null);

        // Assert
        assertTrue(result);
        verify(authentication).getName();
        verify(authentication).getAuthorities();
    }
}