package ec.gob.policia.service.algorithmtotp.controllers.impl;

import ec.gob.policia.service.algorithmtotp.controllers.ValidateCodeController;
import ec.gob.policia.service.algorithmtotp.dtos.CodeRequestDto;
import ec.gob.policia.service.algorithmtotp.dtos.CodeValidateRequestDto;
import ec.gob.policia.service.algorithmtotp.services.usecases.ValidateCodeUseCase;
import ec.gob.policia.service.algorithmtotp.utils.ResponseGenerico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/totps")
public class ValidateCodeControllerImpl implements ValidateCodeController {
    @Autowired
    private ValidateCodeUseCase validateCodeUseCase;
    @Override
    public ResponseEntity<ResponseGenerico> post(CodeValidateRequestDto request) throws Exception {
        log.info("Validando Code TOPT - POR LA APP {}",request);
        ResponseGenerico responseGenerico =new ResponseGenerico();
        boolean result=  validateCodeUseCase.validate(request);
        responseGenerico.setCodigo(200);
        responseGenerico.setData(result);
        responseGenerico.setMensaje("Ok");

        log.info("Validate Code Request {}",responseGenerico);
        return  ResponseEntity.ok(responseGenerico);
    }
}
