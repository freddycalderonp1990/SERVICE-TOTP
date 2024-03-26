package ec.gob.policia.service.algorithmtotp.controllers;


import ec.gob.policia.service.algorithmtotp.dtos.CodeValidateRequestDto;
import ec.gob.policia.service.algorithmtotp.utils.ResponseGenerico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ValidateCodeController {

    @Tag(name = "Algoritm-Totp", description =
            "REST API Que utiliza el Algoritmo Topt para la autenticacion de doble factor")

    @Operation(
            summary =
                    "Metodo para Validar Códigos Temporales",
            description =
                    "Valida el codigo Temporal basado en el algoritmo TOTP")
    // @RequestMapping("/algorithm-topt")

    @PostMapping("/v1/algorithm-topt/validate")
    ResponseEntity<ResponseGenerico> post(@Valid @RequestBody CodeValidateRequestDto request) throws Exception;
}
