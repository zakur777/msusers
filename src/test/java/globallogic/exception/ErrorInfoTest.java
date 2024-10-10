package globallogic.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorInfoTest {
    @Test
    void shouldCreateEmptyErrorInfo() {
        ErrorInfo errorInfo = new ErrorInfo();

        assertNotNull(errorInfo);
        assertNull(errorInfo.getTimestamp());
        assertEquals(0, errorInfo.getCodigo());
        assertNull(errorInfo.getDetail());
    }

    @Test
    void shouldCreateErrorInfoWithAllArguments() {
        LocalDateTime now = LocalDateTime.now();
        int codigo = 404;
        String detail = "Not Found";

        ErrorInfo errorInfo = new ErrorInfo(now, codigo, detail);

        assertNotNull(errorInfo);
        assertEquals(now, errorInfo.getTimestamp());
        assertEquals(codigo, errorInfo.getCodigo());
        assertEquals(detail, errorInfo.getDetail());
    }

    @Test
    void shouldAllowSettingAndGettingTimestamp() {
        ErrorInfo errorInfo = new ErrorInfo();
        LocalDateTime now = LocalDateTime.now();

        errorInfo.setTimestamp(now);

        assertEquals(now, errorInfo.getTimestamp());
    }

    @Test
    void shouldAllowSettingAndGettingCodigo() {
        ErrorInfo errorInfo = new ErrorInfo();
        int codigo = 500;

        errorInfo.setCodigo(codigo);

        assertEquals(codigo, errorInfo.getCodigo());
    }

    @Test
    void shouldAllowSettingAndGettingDetail() {
        ErrorInfo errorInfo = new ErrorInfo();
        String detail = "Internal Server Error";

        errorInfo.setDetail(detail);

        assertEquals(detail, errorInfo.getDetail());
    }

    @Test
    void shouldHandleNullValues() {
        ErrorInfo errorInfo = new ErrorInfo(null, 0, null);

        assertNotNull(errorInfo);
        assertNull(errorInfo.getTimestamp());
        assertEquals(0, errorInfo.getCodigo());
        assertNull(errorInfo.getDetail());
    }

}