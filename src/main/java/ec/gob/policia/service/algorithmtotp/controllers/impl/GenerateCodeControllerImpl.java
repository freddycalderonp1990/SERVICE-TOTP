package ec.gob.policia.service.algorithmtotp.controllers.impl;

import ec.gob.policia.service.algorithmtotp.controllers.GenerateCodeController;
import ec.gob.policia.service.algorithmtotp.dtos.CodeRequestDto;
import ec.gob.policia.service.algorithmtotp.services.usecases.GenerateCodeUseCase;
import ec.gob.policia.service.algorithmtotp.utils.ResponseGenerico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/totps")
public class GenerateCodeControllerImpl implements GenerateCodeController {
    @Autowired
    private  GenerateCodeUseCase generateCodeUseCase;
    @Override
    public ResponseEntity<ResponseGenerico> post(CodeRequestDto request) throws Exception {
        log.info("Generando Code TOPT ");
        ResponseGenerico responseGenerico =new ResponseGenerico();
         String codeTOTP=  generateCodeUseCase.getCode(request);
         responseGenerico.setCodigo(200);
         responseGenerico.setData(codeTOTP);
         responseGenerico.setMensaje("Ok");

        log.info("Code TOPT - POR LA APP {}",responseGenerico);
         return  ResponseEntity.ok(responseGenerico);

    }
}
