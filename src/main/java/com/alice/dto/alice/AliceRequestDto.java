package com.alice.dto.alice;

import com.alice.dto.alice.meta.MetaDto;
import com.alice.dto.alice.request.RequestDto;
import com.alice.dto.alice.session.SessionDto;
import com.alice.dto.alice.state.StateDto;
import lombok.Data;

@Data
public class AliceRequestDto {

    private MetaDto meta;
    private RequestDto request;
    private SessionDto session;
    private StateDto state;

}
