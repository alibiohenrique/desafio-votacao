package com.agenda_simulator_challenge.voting_api.model.response;

import com.agenda_simulator_challenge.voting_api.domain.enums.AgendaStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AgendaResponse {
    private Long id;
    private String title;
    private String description;
    private AgendaStatus status;
    private LocalDateTime creationDate;

}
