package com.agenda_simulator_challenge.voting_api.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgendaRequest {
    @NotBlank(message = "Title cannot be null or empty")
    private String title;
    @NotBlank(message = "Description cannot be null or empty")
    private String description;
}
