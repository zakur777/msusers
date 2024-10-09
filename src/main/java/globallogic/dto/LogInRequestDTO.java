package globallogic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequestDTO implements Serializable {
    private String username;
    private String password;
    private String token;
}
