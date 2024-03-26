package ec.gob.policia.service.algorithmtotp.controllers;


import ec.gob.policia.service.algorithmtotp.dtos.CodeRequestDto;
import ec.gob.policia.service.algorithmtotp.utils.ResponseGenerico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

public interface GenerateCodeController {

    @Tag(name = "Generate-TOPT", description =
            "REST API Que utiliza el Algoritmo Topt para la autenticacion de doble factor")

    @Operation(
            summary =
                    "Metodo para Generar Códigos Temporales",
            description =
                    "Obtiene el codigo Temporal basado en el algoritmo TOTP")
   // @RequestMapping("/algorithm-topt")

    @PostMapping("/v1/algorithm-topt")
    ResponseEntity<ResponseGenerico> post(@Valid @RequestBody CodeRequestDto request) throws Exception;
}
