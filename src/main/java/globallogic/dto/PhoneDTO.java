package globallogic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PhoneDTO {
    private Long id;

    @NotBlank(message = "El número no puede estar vacío")
    private Long number;

    @NotBlank(message = "El código de ciudad no puede estar vacío")
    private Integer cityCode;

    @NotBlank(message = "El código de país no puede estar vacío")
    private String countryCode;
}
