package com.alice.service;

import com.alice.dto.alice.AliceRequestDto;
import com.alice.dto.alice.AliceResponseDto;
import com.alice.dto.alice.response.ResponseDto;
import com.ibm.icu.text.Transliterator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class AliceServiceImpl implements AliceService {

    private final List<String> possibleWords;
    private final String dog;
    private final String dot;
    private final String firstMessage;
    private final String wrongValueMessage;

    public AliceServiceImpl() {
        possibleWords = new LinkedList<>();
        possibleWords.add("номер");
        possibleWords.add("почту");

        dog = "собака";
        dot = "точка";

        firstMessage = "Скажите номер или почту";

        wrongValueMessage = "Введённое значение не является номером или адресом электронной почты";
    }

    @Override
    public AliceResponseDto returnValue(AliceRequestDto aliceRequestDto) {
        AliceResponseDto aliceResponseDto = new AliceResponseDto();
        ResponseDto responseDto = new ResponseDto();
        String text = aliceRequestDto.getRequest().getCommand();

        Range range = null;
        for(String word : possibleWords) {
            if(text.contains(word)) {
                range = getValueStartAndEnd(text,word);
            }
        }
        if(range!=null) {
            text = text.substring(range.start, range.end);
        }

        text = text.replaceAll("[, .;()-]", "");
        String emailNormalForm = null;

        if (text.matches("[0-9]+") && text.length()<=10) {
            responseDto.setText(text);
            responseDto.setTts(getNumberTts(text));
        } else if (EmailValidator.getInstance().isValid(text) ||
                  (text.contains(dog) && text.contains(dot) && EmailValidator.getInstance().isValid((emailNormalForm=transformEmailToNormalForm(text))))) {
            responseDto.setText(emailNormalForm);
            responseDto.setTts(emailNormalForm);
        } else if(text.equals("")) {
            responseDto.setText(firstMessage);
            responseDto.setTts(firstMessage);
        } else {
            responseDto.setText(wrongValueMessage);
            responseDto.setTts(wrongValueMessage);
        }

        aliceResponseDto.setResponse(responseDto);
        return aliceResponseDto;
    }

    private Range getValueStartAndEnd(String text, String word) {
        Range range = new Range();
        range.end = text.length();
        range.start = text.indexOf(word) + word.length() + 1;
        for (int i = range.start; i < text.length(); i++) {
            if (text.charAt(i) >= 1072 && text.charAt(i) <= 1105) {
                range.end = i;
                break;
            }
        }
        return range;
    }

    private String transformEmailToNormalForm(String email) {
        email = email.replaceAll(dog,"@")
                .replaceAll(dot,".")
                .replaceAll("ком","com")
                .replaceAll("джимэйл","gmail")
                .replaceAll("яху","yahoo")
                .replaceAll("мэйл","mail")
                .replaceAll("яндекс","yandex");
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        String normalForm = toLatinTrans.transliterate(email);
        return normalForm;
    }

    private String getNumberTts(String number) {
        String tts = number;
        int length = number.length();
        if(length==10) {
            tts = number.substring(0, 3) + " " + number.substring(3, 6) + " " + number.substring(6, 8) + " " + number.substring(8, 10);
        } else if (length>3) {
            int remainder = length%3;
            StringBuilder ttsSb = new StringBuilder();
            int previous = 0;
            for(int i=3;i<=length;i+=3) {
                ttsSb.append(" " + number.substring(previous, (previous=i)));
            }
            if(remainder!=0) {
                ttsSb.append(" " + number.substring(previous, previous+remainder));
            }
            tts = ttsSb.toString().trim();
        }
        return tts;
    }

    class Range {
        private int start;
        private int end;
    }

}
