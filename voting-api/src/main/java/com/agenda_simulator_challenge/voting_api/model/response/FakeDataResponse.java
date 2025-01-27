package com.agenda_simulator_challenge.voting_api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FakeDataResponse {
    private String name;
    private String cpf;
}
