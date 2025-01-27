package com.agenda_simulator_challenge.voting_api.repository;

import com.agenda_simulator_challenge.voting_api.domain.enums.VoteValue;
import com.agenda_simulator_challenge.voting_api.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsBySessionIdAndAssociateId(Long votingSessionId, Long associateId);
    Long countBySessionIdAndVoteValue(Long sessionId, VoteValue value);
}
