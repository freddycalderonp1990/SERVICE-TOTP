package ec.gob.policia.service.algorithmtotp.services.usecases;

import ec.gob.policia.service.algorithmtotp.dtos.CodeValidateRequestDto;

@FunctionalInterface
public interface ValidateCodeUseCase {

    boolean validate(CodeValidateRequestDto requestDto) throws Exception;
}
