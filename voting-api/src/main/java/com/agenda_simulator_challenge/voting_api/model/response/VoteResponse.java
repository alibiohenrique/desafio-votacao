package com.agenda_simulator_challenge.voting_api.model.response;

import com.agenda_simulator_challenge.voting_api.domain.enums.VoteValue;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VoteResponse {
    private Long id;
    private Long associateId;
    private Long sessionId;
    private VoteValue voteValue;
    private LocalDateTime voteTime;
}
