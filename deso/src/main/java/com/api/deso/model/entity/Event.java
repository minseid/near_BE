package com.api.deso.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@ToString(exclude = {"eventImageList", "eventHashtagList", "eventPostReviewList","eventReviewList", "bookMarkList"})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 20)
    private String category;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long view;

    @Column(length = 2000)
    private String description;

    private String homepage;

    @JsonIgnore
    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<EventImage> eventImageList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<EventHashtag> eventHashtagList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<EventPrice> eventPriceList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<EventOpen> eventOpenList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<EventClose> eventCloseList;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "event")
    EventPlace eventPlace;

    /*
    티켓
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "event")
    List<Ticket> ticketList;
     */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<PromotionImage> promotionImageList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<EventReview> eventReviewList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<EventPostReview> eventPostReviewList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<BookMark> bookMarkList;

}
