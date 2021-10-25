package com.alice.controller;

import com.alice.dto.alice.AliceRequestDto;
import com.alice.dto.alice.AliceResponseDto;
import com.alice.dto.alice.response.ResponseDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alice")
public class AliceRestController {

    @PostMapping
    public AliceResponseDto returnValue(@RequestBody AliceRequestDto aliceRequestDto) {
        AliceResponseDto aliceResponseDto = new AliceResponseDto();
        ResponseDto responseDto = new ResponseDto();
        String text = aliceRequestDto.getRequest().getOriginal_utterance();
        System.out.println(text);
        System.out.println(text.matches("[1-9]") && text.length()==10);
        if(text.matches("[1-9]") && text.length()==10) {
            System.out.println(true);
            responseDto.setText(text.substring(0,3) + " " + text.substring(3,6) + " " + text.substring(6,8) + " " + text.substring(8,10));
        } else if(EmailValidator.getInstance().isValid(text)) {
            System.out.println(false);
            responseDto.setText(text);
        }
        aliceResponseDto.setResponse(responseDto);
        return aliceResponseDto;
    }

}
