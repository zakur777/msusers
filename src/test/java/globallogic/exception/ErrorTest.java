package globallogic.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorTest {
    @Test
    void shouldCreateEmptyError() {
        Error error = new Error();

        assertNotNull(error);
        assertNull(error.getError());
    }

    @Test
    void shouldCreateErrorWithErrorInfoList() {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(LocalDateTime.now(), 404, "Not Found"));
        errorInfoList.add(new ErrorInfo(LocalDateTime.now(), 500, "Internal Server Error"));

        Error error = new Error(errorInfoList);

        assertNotNull(error);
        assertNotNull(error.getError());
        assertEquals(2, error.getError().size());
    }

    @Test
    void shouldAllowSettingAndGettingErrorInfoList() {
        Error error = new Error();
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(LocalDateTime.now(), 400, "Bad Request"));

        error.setError(errorInfoList);

        assertNotNull(error.getError());
        assertEquals(1, error.getError().size());
        assertEquals(400, error.getError().get(0).getCodigo());
        assertEquals("Bad Request", error.getError().get(0).getDetail());
    }

    @Test
    void shouldHandleNullErrorInfoList() {
        Error error = new Error(null);

        assertNotNull(error);
        assertNull(error.getError());
    }

    @Test
    void shouldAllowAddingErrorInfoToList() {
        Error error = new Error(new ArrayList<>());
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now(), 403, "Forbidden");

        error.getError().add(errorInfo);

        assertEquals(1, error.getError().size());
        assertEquals(errorInfo, error.getError().get(0));
    }

    @Test
    void shouldAllowRemovingErrorInfoFromList() {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        ErrorInfo errorInfo1 = new ErrorInfo(LocalDateTime.now(), 404, "Not Found");
        ErrorInfo errorInfo2 = new ErrorInfo(LocalDateTime.now(), 500, "Internal Server Error");
        errorInfoList.add(errorInfo1);
        errorInfoList.add(errorInfo2);

        Error error = new Error(errorInfoList);
        error.getError().remove(errorInfo1);

        assertEquals(1, error.getError().size());
        assertEquals(errorInfo2, error.getError().get(0));
    }

}