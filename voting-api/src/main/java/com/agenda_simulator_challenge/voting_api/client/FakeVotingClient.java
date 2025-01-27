package com.agenda_simulator_challenge.voting_api.client;

import com.agenda_simulator_challenge.voting_api.model.response.FakeDataResponse;
import com.agenda_simulator_challenge.voting_api.util.CpfValitadorUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeVotingClient {

    private static final String API_URL = "https://api.invertexto.com/v1/faker?token=17600|RgNhP0U8SACJKW53AflQsTi6HmTKbiPg&fields=name,cpf&locale=pt_BR";
    private final RestTemplate restTemplate;

    public FakeVotingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateCpf() {
        ResponseEntity<FakeDataResponse> response = restTemplate.getForEntity(API_URL, FakeDataResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getCpf();
        } else {
            throw new IllegalStateException("Erro ao gerar CPF");
        }
    }

    public boolean validateCpf(String cpf) {

        if (!CpfValitadorUtil.isCpfValid(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        return Math.random() > 0.5;
    }
}
