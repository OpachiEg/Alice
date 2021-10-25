package com.alice.dto.alice.session;

import lombok.Data;

@Data
public class SessionDto {

    private String session_id;
    private String message_id;
    private String skill_id;
    private String user_id;
    private UserDto user;
    private ApplicationDto application;

}
