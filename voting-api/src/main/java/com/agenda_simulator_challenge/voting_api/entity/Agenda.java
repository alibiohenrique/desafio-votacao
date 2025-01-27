package com.agenda_simulator_challenge.voting_api.entity;

import com.agenda_simulator_challenge.voting_api.domain.enums.AgendaStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private AgendaStatus status = AgendaStatus.OPEN;

    @CreationTimestamp
    private LocalDateTime creationDate;

}
