package com.agenda_simulator_challenge.voting_api.controller;

import com.agenda_simulator_challenge.voting_api.client.FakeVotingClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class CpfValidatorController {

    private static final Logger log = LogManager.getLogger(VoteController.class);
    private final FakeVotingClient fakeVotingClient;

    public CpfValidatorController(FakeVotingClient fakeVotingClient) {
        this.fakeVotingClient = fakeVotingClient;
    }

    /**
     * Metodo responsável por gerar um CPF fictício.
     *
     * @return ResponseEntity contendo o CPF gerado ou um erro se ocorrer uma falha.
     */
    @GetMapping("/generate")
    public ResponseEntity<Map<String, String>> generateCpf() {
        log.info("Generating CPF...");
        try {
            String cpf = fakeVotingClient.generateCpf();
            Map<String, String> response = new HashMap<>();
            response.put("cpf", cpf);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException ex) {
            log.error("Failed to generate CPF.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Metodo responsável por validar um CPF informado.
     *
     * @param cpf O CPF a ser validado.
     * @return ResponseEntity com o status de votação ou erro caso o CPF seja inválido.
     */
    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validateCpf(@RequestParam String cpf) {
        log.info("Validating CPF: {}", cpf);

        try {
            if (!fakeVotingClient.validateCpf(cpf)) {
                log.error("Invalid CPF: {}", cpf);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "CPF inválido"));
            }

            String voteStatus = Math.random() > 0.5 ? "ABLE_TO_VOTE" : "UNABLE_TO_VOTE";

            Map<String, String> response = new HashMap<>();
            response.put("cpf", cpf);
            response.put("voteStatus", voteStatus);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}