package com.oms.shared.event;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxEvent{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;

    private Long aggregateId;

    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxEventStatus status;

    private LocalDateTime createdAt;
}
