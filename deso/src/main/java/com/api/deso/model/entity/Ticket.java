package com.api.deso.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)

@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"company","event"})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private LocalDateTime date;
    private LocalDateTime startAt;

    @JsonIgnore
    @ManyToOne
    private Company company;

    @JsonIgnore
    @ManyToOne
    private Event event;
}
