package com.agenda_simulator_challenge.voting_api.service;

import com.agenda_simulator_challenge.voting_api.entity.Agenda;
import com.agenda_simulator_challenge.voting_api.entity.VotingSession;
import com.agenda_simulator_challenge.voting_api.model.request.VotingSessionRequest;
import com.agenda_simulator_challenge.voting_api.model.response.VotingSessionResponse;
import com.agenda_simulator_challenge.voting_api.repository.AgendaRepository;
import com.agenda_simulator_challenge.voting_api.repository.VotingSessionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotingSessionService {
    private static final Logger log = LogManager.getLogger(VotingSessionService.class);
    private VotingSessionRepository votingSessionRepository;
    private final AgendaRepository agendaRepository;

    public VotingSessionService(VotingSessionRepository votingSessionRepository, AgendaRepository agendaRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.agendaRepository = agendaRepository;
    }

    public VotingSessionResponse openVotingSession(VotingSessionRequest votingSessionRequest) {

        validateVotingSessionRequest(votingSessionRequest);

        Agenda agenda = agendaRepository.findById(votingSessionRequest.getAgendaId())
                .orElseThrow(() -> new IllegalArgumentException("Agenda not found with id: " + votingSessionRequest.getAgendaId()));

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(votingSessionRequest.getDurationInMinutes());

        VotingSession votingSession = VotingSession.builder()
                .agenda(agenda)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        VotingSession savedVotingSession = votingSessionRepository.save(votingSession);
        return mapToResponse(savedVotingSession);
    }

    private VotingSessionResponse mapToResponse(VotingSession votingSession) {
        return VotingSessionResponse.builder()
                .sessionId(votingSession.getId())
                .agenda(votingSession.getAgenda())
                .startTime(votingSession.getStartTime())
                .endTime(votingSession.getEndTime())
                .build();
    }

    private void validateVotingSessionRequest(VotingSessionRequest votingSessionRequest) {
        if (votingSessionRequest == null) {
            log.warn("Validation failed: Voting session request is null.");
            throw new IllegalArgumentException("Voting session request cannot be null!");
        }

        if (votingSessionRequest.getAgendaId() == null) {
            log.warn("Validation failed: Agenda ID is null - Request: {}", votingSessionRequest);
            throw new IllegalArgumentException("Agenda ID cannot be null!");
        }

        if (votingSessionRequest.getDurationInMinutes() == null || votingSessionRequest.getDurationInMinutes() <= 0) {
            log.info("Duration not provided or invalid, defaulting to 1 minute. Request: {}", votingSessionRequest);
            votingSessionRequest.setDurationInMinutes(1);
        }
    }

}
