package com.agenda_simulator_challenge.voting_api.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotingSessionRequest {
    private Long agendaId;
    private Integer durationInMinutes;

}
