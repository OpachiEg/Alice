package com.alice.dto.alice.request;

import lombok.Data;

@Data
public class RequestDto {

    private String command;
    private String original_utterance;
    // тип ввода
    private String type;

}
