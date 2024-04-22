package com.api.deso.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(exclude = {"user"})

@EntityListeners(AuditingEntityListener.class)
public class PurchaseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id; //번호
    private String name;
    private String phoneNumber;
    private String email;
    private String payment;
    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Ticket ticket;
}

