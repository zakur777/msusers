package globallogic.util.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import globallogic.exception.CustomErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class JwtAuthenticationEntryPointTest {
    @InjectMocks
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Mock
    private AuthenticationException authException;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void shouldCommenceWithCustomException() throws IOException, ServletException {
        // Arrange
        String exceptionMsg = "Custom exception message";
        request.setAttribute("exception", exceptionMsg);

        // Act
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        CustomErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), CustomErrorResponse.class);
        assertEquals(exceptionMsg, errorResponse.getMessage());
        assertEquals(request.getRequestURI(), errorResponse.getPath());
        assertNotNull(errorResponse.getDatetime());
    }

    @Test
    void shouldCommenceWithDefaultException() throws IOException, ServletException {
        // Act
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        CustomErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), CustomErrorResponse.class);
        assertEquals("Token not found", errorResponse.getMessage());
        assertEquals(request.getRequestURI(), errorResponse.getPath());
        assertNotNull(errorResponse.getDatetime());
    }


}