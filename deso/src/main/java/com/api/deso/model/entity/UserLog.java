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
@ToString(exclude = {"user"})

@EntityListeners(AuditingEntityListener.class)

public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id; //번호
    private String title; //제목
    private String link;
    private String type;
    private LocalDateTime createAt;

    @JsonIgnore
    @ManyToOne
    private User user;
}

