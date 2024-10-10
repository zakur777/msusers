package globallogic.dto;

import lombok.*;

import javax.validation.constraints.Positive;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    private Long id;

    @Positive(message = "El número debe ser positivo")
    private Long number;

    @Positive(message = "El código de ciudad debe ser positivo")
    private Integer cityCode;

    private String countryCode;
}
