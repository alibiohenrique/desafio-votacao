package com.agenda_simulator_challenge.voting_api.entity;

import com.agenda_simulator_challenge.voting_api.domain.enums.VoteValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long associateId;

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    private Agenda agenda;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private VotingSession session;

    @Enumerated(EnumType.STRING)
    private VoteValue voteValue;

    @CreationTimestamp
    LocalDateTime creationTime;


}
