package com.agenda_simulator_challenge.voting_api.controller;


import com.agenda_simulator_challenge.voting_api.model.request.VotingSessionRequest;
import com.agenda_simulator_challenge.voting_api.model.response.VotingSessionResponse;
import com.agenda_simulator_challenge.voting_api.service.VotingSessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/voting-session")
public class VotingSessionController {

    private static final Logger log = LogManager.getLogger(VotingSessionController.class);
    private VotingSessionService votingSessionService;

    public VotingSessionController(VotingSessionService votingSessionService) {
        this.votingSessionService = votingSessionService;
    }

    /**
     * Controlador responsável por gerenciar as operações relacionadas às sessões de votação.
     * Este metodo cria e abre uma nova sessão de votação com base nos dados fornecidos.
     *
     * @param votingSessionRequest objeto contendo as informações necessárias para abrir uma sessão de votação.
     *                             Deve incluir dados como o identificador da pauta (agendaId) e a duração da sessão (em minutos).
     * @return ResponseEntity contendo o objeto {@link VotingSessionResponse} com os detalhes da sessão de votação aberta
     *         e o status HTTP 200 (OK) caso a operação seja bem-sucedida.
     *
     * @throws IllegalArgumentException caso o objeto {@link VotingSessionRequest} seja inválido ou tenha dados incorretos.
     *
     * Exemplo de request JSON:
     * <pre>
     * {
     *   "agendaId": 1,
     *   "durationInMinutes": 60
     * }
     * </pre>
     *
     * Exemplo de response JSON:
     * <pre>
     * {
     *   "sessionId": 10,
     *   "agendaId": 1,
     *   "startTime": "2025-01-27T12:00:00Z",
     *   "endTime": "2025-01-27T13:00:00Z",
     *   "status": "OPEN"
     * }
     * </pre>
     *
     * Logs:
     * O metodo registra logs informativos com os detalhes da solicitação para criar a sessão de votação.
     */

    @PostMapping(value = "/create")
    public ResponseEntity<VotingSessionResponse> createAgenda(@RequestBody VotingSessionRequest votingSessionRequest) {
        log.info("Trying to create a voting session. votingSessionRequest: {}", votingSessionRequest);
        return ResponseEntity.ok(votingSessionService.openVotingSession(votingSessionRequest));
    }
}
