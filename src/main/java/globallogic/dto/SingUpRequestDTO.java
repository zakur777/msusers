package globallogic.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SingUpRequestDTO {

    private String name;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "El email no cumple con el formato requerido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "La contraseña no cumple con los requisitos de seguridad")
    private String password;

    private List<PhoneDTO> phones;
}
