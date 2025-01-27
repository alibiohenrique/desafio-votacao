package com.agenda_simulator_challenge.voting_api.service;

import com.agenda_simulator_challenge.voting_api.domain.enums.AgendaStatus;
import com.agenda_simulator_challenge.voting_api.entity.Agenda;
import com.agenda_simulator_challenge.voting_api.model.request.AgendaRequest;
import com.agenda_simulator_challenge.voting_api.model.response.AgendaResponse;
import com.agenda_simulator_challenge.voting_api.repository.AgendaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AgendaService {
    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public AgendaResponse createAgenda(AgendaRequest agendaRequest) {
        if (agendaRequest.getTitle() == null || agendaRequest.getTitle().isEmpty()) {
            log.warn("Agenda creation validation failed: itle cannot be null or empty. AgendaRequest: {}", agendaRequest);
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (agendaRequest.getDescription() == null || agendaRequest.getDescription().isEmpty()) {
            log.warn("Agenda creation validation failed: description cannot be null or empty. AgendaRequest: {}", agendaRequest);
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        Agenda agenda = Agenda.builder()
                .title(agendaRequest.getTitle())
                .description(agendaRequest.getDescription())
                .status(AgendaStatus.OPEN)
                .build();

        Agenda savedAgenda = agendaRepository.save(agenda);

        return mapToResponse(savedAgenda);
    }

    private AgendaResponse mapToResponse(Agenda agenda) {
        return AgendaResponse.builder()
                .id(agenda.getId())
                .title(agenda.getTitle())
                .creationDate(agenda.getCreationDate())
                .status(agenda.getStatus())
                .description(agenda.getDescription())
                .build();
    }
}
