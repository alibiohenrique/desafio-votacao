package com.agenda_simulator_challenge.voting_api.model.response;

import com.agenda_simulator_challenge.voting_api.entity.Agenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotingSessionResponse {
    private Long sessionId;
    private Agenda agenda;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
