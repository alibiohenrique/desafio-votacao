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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    void shouldSubmitVoteSuccessfully() {
        // Arrange
        VoteRequest voteRequest = new VoteRequest(1L, 1L, VoteValue.YES, 1L);
        VotingSession votingSession = new VotingSession();
        votingSession.setId(1L);
        Agenda agenda = new Agenda();
        agenda.setId(1L);

        Mockito.when(votingSessionRepository.findById(1L)).thenReturn(Optional.of(votingSession));
        Mockito.when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));
        Mockito.when(voteRepository.existsBySessionIdAndAssociateId(1L, 1L)).thenReturn(false);
        Mockito.when(voteRepository.save(Mockito.any(Vote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        VoteResponse voteResponse = voteService.submitVote(voteRequest);

        // Assert
        assertNotNull(voteResponse);
        assertEquals(1L, voteResponse.getAssociateId());
        assertEquals(1L, voteResponse.getSessionId());
        assertEquals(VoteValue.YES, voteResponse.getVoteValue());
    }

    @Test
    void shouldThrowExceptionWhenUserHasAlreadyVoted() {
        // Arrange
        VoteRequest voteRequest = new VoteRequest(1L, 1L, VoteValue.YES, 1L);

        Mockito.when(voteRepository.existsBySessionIdAndAssociateId(1L, 1L)).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> voteService.submitVote(voteRequest));
        assertEquals("Member has already voted.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenVotingSessionNotFound() {
        // Arrange
        VoteRequest voteRequest = new VoteRequest(1L, 1L, VoteValue.YES, 1L);

        Mockito.when(voteRepository.existsBySessionIdAndAssociateId(1L, 1L)).thenReturn(false);
        Mockito.when(votingSessionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> voteService.submitVote(voteRequest));
        assertEquals("Voting session not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAgendaNotFound() {
        // Arrange
        VoteRequest voteRequest = new VoteRequest(1L, 1L, VoteValue.YES, 1L);
        VotingSession votingSession = new VotingSession();
        votingSession.setId(1L);

        Mockito.when(voteRepository.existsBySessionIdAndAssociateId(1L, 1L)).thenReturn(false);
        Mockito.when(votingSessionRepository.findById(1L)).thenReturn(Optional.of(votingSession));
        Mockito.when(agendaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> voteService.submitVote(voteRequest));
        assertEquals("Agenda not found", exception.getMessage());
    }

    @Test
    void shouldCountVotesByValue() {
        // Arrange
        Long sessionId = 1L;
        VoteValue voteValue = VoteValue.YES;
        Mockito.when(voteRepository.countBySessionIdAndVoteValue(sessionId, voteValue)).thenReturn(10L);

        // Act
        Long voteCount = voteService.countVotes(sessionId, voteValue);

        // Assert
        assertEquals(10L, voteCount);
    }
}