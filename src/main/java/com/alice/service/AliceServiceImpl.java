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
        Range range = null;
        if (text.contains("номер")) {
            range = getValueStartAndEnd(text, "номер");
        }
        if (text.contains("почту")) {
            range = getValueStartAndEnd(text, "почту");
        }
        text = text.substring(range.start, range.end);
        text = text.replaceAll(" ", "");
        if (text.matches("[0-9]+") && text.length() == 10) {
            responseDto.setText(text);
            responseDto.setTts(text.substring(0, 3) + " " + text.substring(3, 6) + " " + text.substring(6, 8) + " " + text.substring(8, 10));
        } else if (EmailValidator.getInstance().isValid(text)) {
            responseDto.setText(text);
            responseDto.setTts(text);
        } else {
            responseDto.setText("Введённое значение не является номером или адресом электронной почты");
            responseDto.setTts("Введённое значение не является номером или адресом электронной почты");
        }
        aliceResponseDto.setResponse(responseDto);
        return aliceResponseDto;
    }

    public Range getValueStartAndEnd(String text, String word) {
        Range range = new Range();
        range.start = text.indexOf(word) + 6;
        for (int i = range.start; i < text.length(); i++) {
            if (text.charAt(i) >= 1072 && text.charAt(i) <= 1105) {
                range.end = i;
                break;
            }
        }
        if (range.end == 0) {
            range.end = text.length();
        }
        return range;
    }

    class Range {
        private int start;
        private int end;
    }

}
