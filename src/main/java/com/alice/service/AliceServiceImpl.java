package com.alice.service;

import com.alice.dto.alice.AliceRequestDto;
import com.alice.dto.alice.AliceResponseDto;
import com.alice.dto.alice.response.ResponseDto;
import com.ibm.icu.text.Transliterator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class AliceServiceImpl implements AliceService {

    @Override
    public AliceResponseDto returnValue(AliceRequestDto aliceRequestDto) {
        AliceResponseDto aliceResponseDto = new AliceResponseDto();
        ResponseDto responseDto = new ResponseDto();
        String text = aliceRequestDto.getRequest().getCommand();

        Range range = null;
        if (text.contains("номер")) {
            range = getValueStartAndEnd(text, "номер");
        }
        if (text.contains("почту")) {
            range = getValueStartAndEnd(text, "почту");
        }
        if(range!=null) {
            text = text.substring(range.start, range.end);
        }

        text = text.replaceAll("[, .;()-]", "");
        String emailNormalForm = null;

        if (text.matches("[0-9]+") && text.length() == 10) {
            responseDto.setText(text);
            responseDto.setTts(text.substring(0, 3) + " " + text.substring(3, 6) + " " + text.substring(6, 8) + " " + text.substring(8, 10));
        } else if (EmailValidator.getInstance().isValid(text) || (text.contains("собака") && text.contains("точка") && EmailValidator.getInstance().isValid((emailNormalForm=transformEmailInNormalForm(text))))) {
            responseDto.setText(emailNormalForm);
            responseDto.setTts(emailNormalForm);
        } else if(text.equals("")) {
            responseDto.setText("Скажите номер или почту");
            responseDto.setTts("Скажите номер или почту");
        } else {
            responseDto.setText("Введённое значение не является номером или адресом электронной почты");
            responseDto.setTts("Введённое значение не является номером или адресом электронной почты");
        }
        aliceResponseDto.setResponse(responseDto);
        return aliceResponseDto;
    }

    private Range getValueStartAndEnd(String text, String word) {
        Range range = new Range();
        range.start = text.indexOf(word) + word.length() + 1;
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

    public String transformEmailInNormalForm(String email) {
        email = email.replaceAll("собака","@").replaceAll("точка",".").replaceAll("ком","com");
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        String normalForm = toLatinTrans.transliterate(email);
        return normalForm;
    }

    class Range {
        private int start;
        private int end;
    }

}
