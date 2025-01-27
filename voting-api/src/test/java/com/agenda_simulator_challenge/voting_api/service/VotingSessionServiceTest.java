package com.agenda_simulator_challenge.voting_api.service;

import com.agenda_simulator_challenge.voting_api.entity.Agenda;
import com.agenda_simulator_challenge.voting_api.entity.VotingSession;
import com.agenda_simulator_challenge.voting_api.model.request.VotingSessionRequest;
import com.agenda_simulator_challenge.voting_api.model.response.VotingSessionResponse;
import com.agenda_simulator_challenge.voting_api.repository.AgendaRepository;
import com.agenda_simulator_challenge.voting_api.repository.VotingSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VotingSessionServiceTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private VotingSessionService votingSessionService;

    @Test
    void shouldOpenVotingSessionWithProvidedDuration() {
        // Arrange
        VotingSessionRequest request = new VotingSessionRequest();
        request.setAgendaId(1L);
        request.setDurationInMinutes(10);

        Agenda agenda = new Agenda();
        agenda.setId(1L);

        Mockito.when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));
        Mockito.when(votingSessionRepository.save(Mockito.any(VotingSession.class))).thenAnswer(invocation -> {
            VotingSession votingSession = invocation.getArgument(0);
            votingSession.setId(100L); // Simula o ID gerado pelo banco
            return votingSession;
        });

        // Act
        VotingSessionResponse response = votingSessionService.openVotingSession(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getAgenda().getId());
        assertEquals(10, Duration.between(response.getStartTime(), response.getEndTime()).toMinutes());
        assertEquals(100L, response.getSessionId());
    }

    @Test
    void shouldOpenVotingSessionWithDefaultDuration() {
        // Arrange
        VotingSessionRequest request = new VotingSessionRequest();
        request.setAgendaId(1L);

        Agenda agenda = new Agenda();
        agenda.setId(1L);

        Mockito.when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));
        Mockito.when(votingSessionRepository.save(Mockito.any(VotingSession.class))).thenAnswer(invocation -> {
            VotingSession votingSession = invocation.getArgument(0);
            votingSession.setId(101L); // Simula o ID gerado pelo banco
            return votingSession;
        });

        // Act
        VotingSessionResponse response = votingSessionService.openVotingSession(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getAgenda().getId());
        assertEquals(1, Duration.between(response.getStartTime(), response.getEndTime()).toMinutes()); // Duração padrão de 1 minuto
        assertEquals(101L, response.getSessionId());
    }

    @Test
    void shouldThrowExceptionWhenAgendaNotFound() {
        // Arrange
        VotingSessionRequest request = new VotingSessionRequest();
        request.setAgendaId(1L);
        request.setDurationInMinutes(5);

        Mockito.when(agendaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> votingSessionService.openVotingSession(request));

        assertEquals("Agenda not found with id: 1", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRequestIsInvalid() {
        // Arrange
        VotingSessionRequest invalidRequest = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> votingSessionService.openVotingSession(invalidRequest));

        assertEquals("Voting session request cannot be null!", exception.getMessage());
    }
}