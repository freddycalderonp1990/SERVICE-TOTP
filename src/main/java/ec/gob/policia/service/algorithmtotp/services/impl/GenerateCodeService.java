package ec.gob.policia.service.algorithmtotp.services.impl;

import ec.gob.policia.service.algorithmtotp.dtos.CodeRequestDto;
import ec.gob.policia.service.algorithmtotp.services.usecases.GenerateCodeUseCase;
import ec.gob.policia.service.algorithmtotp.utils.AlgoritmoTOTP;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GenerateCodeService implements GenerateCodeUseCase {


    @Override
    public String getCode(CodeRequestDto requestDto) throws Exception {

      String codigo= AlgoritmoTOTP.getCode(requestDto);

        return codigo;
    }
}
