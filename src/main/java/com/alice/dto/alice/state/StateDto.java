package com.alice.dto.alice.state;

import lombok.Data;

@Data
public class StateDto {

    private Object session;
    private UserStateDto user;
    private Object application;

}
