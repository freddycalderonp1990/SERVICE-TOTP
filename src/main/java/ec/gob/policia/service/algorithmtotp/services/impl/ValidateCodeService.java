package ec.gob.policia.service.algorithmtotp.services.impl;

import ec.gob.policia.service.algorithmtotp.dtos.CodeValidateRequestDto;
import ec.gob.policia.service.algorithmtotp.services.usecases.ValidateCodeUseCase;
import ec.gob.policia.service.algorithmtotp.utils.AlgoritmoTOTP;
import org.springframework.stereotype.Service;

@Service
public class ValidateCodeService implements ValidateCodeUseCase {
    @Override
    public boolean validate(CodeValidateRequestDto requestDto) throws Exception {
        boolean result= AlgoritmoTOTP.verificarCodigo(requestDto);
        return result;
    }
}
