package com.alice.service;

import com.alice.dto.alice.AliceRequestDto;
import com.alice.dto.alice.AliceResponseDto;
import com.alice.dto.alice.response.ResponseDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class AliceServiceImpl implements AliceService {

    @Override
    public AliceResponseDto returnValue(AliceRequestDto aliceRequestDto) {
        AliceResponseDto aliceResponseDto = new AliceResponseDto();
        ResponseDto responseDto = new ResponseDto();
        String text = aliceRequestDto.getRequest().getOriginal_utterance();
        text.replaceAll(" ","");
        if(text.matches("[0-9]+") && text.length()==10) {
            responseDto.setText(text);
            responseDto.setTts(text.substring(0,3) + " " + text.substring(3,6) + " " + text.substring(6,8) + " " + text.substring(8,10));
        } else if(EmailValidator.getInstance().isValid(text)) {
            responseDto.setText(text);
            responseDto.setTts(text);
        }
        aliceResponseDto.setResponse(responseDto);
        return aliceResponseDto;
    }

}
