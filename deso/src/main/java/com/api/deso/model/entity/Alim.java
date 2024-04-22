package com.api.deso.model.entity;

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
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
public class Alim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키

    private String title; // 알림 제목

    private String content; // 알림 내용

    @ManyToOne
    private User to; // 알림 대상

    @ManyToOne
    private User from; //알림 보낸 사람

    private String type; // 알림 종류

    private String link; // 알림 링크

    private Boolean showFrom;

    private Boolean showTo;

    private LocalDateTime createdAt;

}
