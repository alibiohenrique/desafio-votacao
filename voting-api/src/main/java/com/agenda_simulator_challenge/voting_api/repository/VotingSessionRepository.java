package com.agenda_simulator_challenge.voting_api.repository;

import com.agenda_simulator_challenge.voting_api.entity.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
}
