package com.agenda_simulator_challenge.voting_api.model.request;

import com.agenda_simulator_challenge.voting_api.domain.enums.VoteValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteRequest {
    private Long sessionId;
    private Long associateId;
    private VoteValue voteValue;
    private Long agendaId;
}
