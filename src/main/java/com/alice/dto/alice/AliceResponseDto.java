package com.alice.dto.alice;

import com.alice.dto.alice.response.ResponseDto;
import com.alice.dto.alice.state.UserStateDto;
import lombok.Data;

@Data
public class AliceResponseDto {

    private ResponseDto response;
    private Object session_state;
    private UserStateDto user_state_update;
    private String version = "1.0";

}
