package com.alice.dto.alice.response;

import lombok.Data;

@Data
public class ResponseDto {

    private String text;
    private String tts;
    private boolean endSession;

}
