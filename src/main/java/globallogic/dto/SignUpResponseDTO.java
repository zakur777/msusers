package globallogic.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpResponseDTO {
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;
}
