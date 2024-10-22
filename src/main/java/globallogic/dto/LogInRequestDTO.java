package globallogic.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequestDTO implements Serializable {
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "El email no cumple con el formato requerido")
    private String username;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "La contraseña no cumple con los requisitos de seguridad")
    private String password;
    @NotBlank(message = "El Token no puede estar vacío")
    private String token;
}
