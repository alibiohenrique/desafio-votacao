package com.agenda_simulator_challenge.voting_api.service;

import com.agenda_simulator_challenge.voting_api.domain.enums.AgendaStatus;
import com.agenda_simulator_challenge.voting_api.entity.Agenda;
import com.agenda_simulator_challenge.voting_api.model.request.AgendaRequest;
import com.agenda_simulator_challenge.voting_api.model.response.AgendaResponse;
import com.agenda_simulator_challenge.voting_api.repository.AgendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private AgendaService agendaService;

    @Test
    void shouldCreateAgendaSuccessfully() {
        // Arrange
        AgendaRequest agendaRequest = new AgendaRequest("Test Title", "Test Description");
        Agenda agenda = Agenda.builder()
                .id(1L)
                .title("Test Title")
                .description("Test Description")
                .status(AgendaStatus.OPEN)
                .creationDate(LocalDateTime.now())
                .build();

        Mockito.when(agendaRepository.save(Mockito.any(Agenda.class))).thenReturn(agenda);

        // Act
        AgendaResponse response = agendaService.createAgenda(agendaRequest);

        // Assert
        assertNotNull(response);
        assertEquals(agenda.getId(), response.getId());
        assertEquals(agenda.getTitle(), response.getTitle());
        assertEquals(agenda.getDescription(), response.getDescription());
        assertEquals(agenda.getStatus(), response.getStatus());
        Mockito.verify(agendaRepository, Mockito.times(1)).save(Mockito.any(Agenda.class));
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        // Arrange
        AgendaRequest agendaRequest = new AgendaRequest(null, "Test Description");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> agendaService.createAgenda(agendaRequest));

        assertEquals("Title cannot be null or empty", exception.getMessage());
        Mockito.verify(agendaRepository, Mockito.never()).save(Mockito.any(Agenda.class));
    }

    @Test
    void shouldThrowExceptionWhenTitleIsEmpty() {
        // Arrange
        AgendaRequest agendaRequest = new AgendaRequest("", "Test Description");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> agendaService.createAgenda(agendaRequest));

        assertEquals("Title cannot be null or empty", exception.getMessage());
        Mockito.verify(agendaRepository, Mockito.never()).save(Mockito.any(Agenda.class));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        // Arrange
        AgendaRequest agendaRequest = new AgendaRequest("Test Title", null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> agendaService.createAgenda(agendaRequest));

        assertEquals("Description cannot be null or empty", exception.getMessage());
        Mockito.verify(agendaRepository, Mockito.never()).save(Mockito.any(Agenda.class));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        // Arrange
        AgendaRequest agendaRequest = new AgendaRequest("Test Title", "");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> agendaService.createAgenda(agendaRequest));

        assertEquals("Description cannot be null or empty", exception.getMessage());
        Mockito.verify(agendaRepository, Mockito.never()).save(Mockito.any(Agenda.class));
    }
}