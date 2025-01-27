package com.agenda_simulator_challenge.voting_api.service;

import com.agenda_simulator_challenge.voting_api.domain.enums.VoteValue;
import com.agenda_simulator_challenge.voting_api.entity.Agenda;
import com.agenda_simulator_challenge.voting_api.entity.Vote;
import com.agenda_simulator_challenge.voting_api.entity.VotingSession;
import com.agenda_simulator_challenge.voting_api.model.request.VoteRequest;
import com.agenda_simulator_challenge.voting_api.model.response.VoteResponse;
import com.agenda_simulator_challenge.voting_api.repository.AgendaRepository;
import com.agenda_simulator_challenge.voting_api.repository.VoteRepository;
import com.agenda_simulator_challenge.voting_api.repository.VotingSessionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VoteService {

    private static final Logger log = LogManager.getLogger(VoteService.class);
    private final VoteRepository voteRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final AgendaRepository agendaRepository;

    public VoteService(VoteRepository voteRepository, VotingSessionRepository votingSessionRepository, AgendaRepository agendaRepository) {
        this.voteRepository = voteRepository;
        this.votingSessionRepository = votingSessionRepository;
        this.agendaRepository = agendaRepository;
    }

    public VoteResponse submitVote(VoteRequest voteRequest) {
        log.info("Processing vote for associateId: {} in session: {}", voteRequest.getAssociateId(), voteRequest.getSessionId());

        boolean hasVoted = voteRepository.existsBySessionIdAndAssociateId(voteRequest.getSessionId(), voteRequest.getAssociateId());
        if (hasVoted) {
            log.warn("User with associateId: {} has already voted in session: {}", voteRequest.getAssociateId(), voteRequest.getSessionId());
            throw new RuntimeException("Member has already voted.");
        }

        VotingSession votingSession = votingSessionRepository.findById(voteRequest.getSessionId())
                .orElseThrow(() -> {
                    log.error("Voting session not found for sessionId: {}", voteRequest.getSessionId());
                    return new RuntimeException("Voting session not found");
                });

        Agenda agenda = agendaRepository.findById(voteRequest.getAgendaId())
                .orElseThrow(() -> {
                    log.error("Agenda not found for agendaId: {}", voteRequest.getAgendaId());
                    return new RuntimeException("Agenda not found");
                });


        Vote vote = Vote.builder()
                .associateId(voteRequest.getAssociateId())
                .session(votingSession)
                .agenda(agenda)
                .voteValue(voteRequest.getVoteValue())
                .creationTime(LocalDateTime.now()) // Make sure to set the creation time
                .build();

        voteRepository.save(vote);
        log.info("Vote submitted successfully for associateId: {}", voteRequest.getAssociateId());

        return mapToResponse(vote);
    }

    public Long countVotes(Long sessionId, VoteValue value) {
        return voteRepository.countBySessionIdAndVoteValue(sessionId, value);
    }

    private VoteResponse mapToResponse(Vote vote) {
        return VoteResponse.builder()
                .id(vote.getId())
                .associateId(vote.getAssociateId())
                .sessionId(vote.getSession().getId())
                .voteValue(vote.getVoteValue())
                .voteTime(vote.getCreationTime())
                .build();
    }
}

