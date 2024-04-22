package com.api.deso.model.entity;

import com.sun.java.accessibility.util.EventID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String content;

    private Long starRating;

    private Long warning;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnore
    private Event event;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventReview")
    List<EventReviewImage> eventReviewImages;

}
