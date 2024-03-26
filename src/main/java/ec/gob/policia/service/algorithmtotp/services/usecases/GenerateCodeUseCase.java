package ec.gob.policia.service.algorithmtotp.services.usecases;

import ec.gob.policia.service.algorithmtotp.dtos.CodeRequestDto;

@FunctionalInterface
public interface GenerateCodeUseCase {
    String getCode(CodeRequestDto requestDto) throws Exception;
}
