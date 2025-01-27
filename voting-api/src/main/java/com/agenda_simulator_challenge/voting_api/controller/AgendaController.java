package com.agenda_simulator_challenge.voting_api.controller;

import com.agenda_simulator_challenge.voting_api.model.request.AgendaRequest;
import com.agenda_simulator_challenge.voting_api.model.response.AgendaResponse;
import com.agenda_simulator_challenge.voting_api.service.AgendaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = "/api/agenda")
public class AgendaController {

    private static final Logger log = LogManager.getLogger(AgendaController.class);
    private AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * Controlador responsável por gerenciar as operações relacionadas à agenda.
     * Este metodo cria uma nova agenda com base nos dados fornecidos.
     *
     * @param agendaRequest objeto contendo os dados necessários para criar uma agenda.
     *                      Deve incluir informações como título e descrição.
     * @return ResponseEntity contendo o objeto {@link AgendaResponse} com os detalhes da agenda criada
     *         e o status HTTP 200 (OK) caso a operação seja bem-sucedida.
     *
     * @throws IllegalArgumentException caso o objeto {@link AgendaRequest} seja inválido ou tenha dados incorretos.
     *
     * Exemplo de request JSON:
     * <pre>
     * {
     *   "title": "Reunião de planejamento",
     *   "description": "Definir estratégias para o próximo trimestre"
     * }
     * </pre>
     *
     * Exemplo de response JSON:
     * <pre>
     * {
     *   "id": 1,
     *   "title": "Reunião de planejamento",
     *   "description": "Definir estratégias para o próximo trimestre",
     *   "createdAt": "2025-01-27T12:00:00Z"
     * }
     * </pre>
     *
     * Logs:
     * O metodo registra logs informativos com o título e descrição da agenda que está sendo criada.
     */

    @PostMapping(value = "/create")
    public ResponseEntity<AgendaResponse> createAgenda(@RequestBody AgendaRequest agendaRequest) {
        log.info("Trying to create an agenda. Data - title: {}, description: {}",
                agendaRequest.getTitle(), agendaRequest.getDescription());
        return ResponseEntity.ok(agendaService.createAgenda(agendaRequest));
    }
}
