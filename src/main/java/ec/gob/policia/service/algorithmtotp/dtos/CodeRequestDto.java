package ec.gob.policia.service.algorithmtotp.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CodeRequestDto {
    @NotNull
    @NotEmpty
    private String password;

}
