package com.agenda_simulator_challenge.voting_api.controller;


import com.agenda_simulator_challenge.voting_api.domain.enums.VoteValue;
import com.agenda_simulator_challenge.voting_api.model.request.VoteRequest;
import com.agenda_simulator_challenge.voting_api.model.response.VoteResponse;
import com.agenda_simulator_challenge.voting_api.service.VoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private static final Logger log = LogManager.getLogger(VoteController.class);
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    /**
     * Submete um voto para uma sessão.
     *
     * Este metodo recebe uma solicitação de voto através do corpo da requisição,
     *      processa o voto utilizando o serviço `voteService`,
     *      e retorna uma resposta com o resultado do envio do voto.
     *      Em caso de erro, retorna uma resposta de erro.
     *
     * @param voteRequest O objeto que contém as informações do voto, como o valor e o ID da sessão.
     * @return Um `ResponseEntity` contendo o resultado do envio do voto.
     *      Se o voto for processado com sucesso, retorna um `VoteResponse`;
     *      caso contrário, retorna uma resposta de erro com o status 400 (Bad Request).
     */
    @PostMapping("/submit")
    public ResponseEntity<VoteResponse> submitVote(@RequestBody VoteRequest voteRequest) {
        log.info("Submitting vote: {}", voteRequest);
        try {
            VoteResponse voteResponse = voteService.submitVote(voteRequest);
            return ResponseEntity.ok(voteResponse);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Conta os votos de uma determinada sessão.
     *
     * Este metodo vai recuperar a contagem dos votos
     *      "SIM" e "NÃO" para a sessão identificada pelo ID de sessão fornecido.
     *      Ele chama o metodo `countVotes` do serviço `voteService` para os dois valores de voto
     *      "SIM" e "NÃO" e retorna o resultado em um mapa.
     *
     * @param sessionId O ID da sessão de votação para a qual os votos estão sendo contados.
     * @return Um `ResponseEntity` contendo um mapa com as contagens dos votos. O mapa inclui:
     *         - "SIM" : contagem dos votos SIM para a sessão
     *         - "NÃO"  : contagem dos votos NÃO para a sessão
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countVotes(@RequestParam Long sessionId) {
        log.info("Counting votes for session: {}", sessionId);

        Map<String, Long> voteCounts = new HashMap<>();
        voteCounts.put("YES", voteService.countVotes(sessionId, VoteValue.YES));
        voteCounts.put("NO", voteService.countVotes(sessionId, VoteValue.NO));

        return ResponseEntity.ok(voteCounts);
    }
}
