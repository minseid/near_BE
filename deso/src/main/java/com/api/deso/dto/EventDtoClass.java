package com.api.deso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventDtoClass {
    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class Userinfo {

        private String role;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventCommentDto {
        @JsonProperty
        private Long id;

        @JsonProperty
        private String writer;

        @JsonProperty
        private String content;

        @JsonProperty
        private Long relatedCommentId;

        @JsonProperty
        private LocalDateTime createdAt;

        @JsonProperty
        private LocalDateTime updatedAt;

        public EventCommentDto() {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventManageDto {
        @JsonProperty
        private Long id;

        @JsonProperty
        private String role;

        @JsonProperty
        private Long userId;

        @JsonProperty
        private Long eventId;

        public EventManageDto() {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class SimpleEventDto{

        @JsonProperty
        private Long id;
        @JsonProperty
        private String title;
        @JsonProperty
        private String category;
        @JsonProperty
        private LocalDateTime startDate;
        @JsonProperty
        private LocalDateTime endDate;
        @JsonProperty
        private LocalDateTime createdAt;
        @JsonProperty
        private LocalDateTime updatedAt;
        @JsonProperty
        private Long view;
        @JsonProperty
        private String description;
        @JsonProperty
        private String homepage;
        @JsonProperty
        private Long userId;
        public SimpleEventDto() {

        }

    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class SimpleEventIdDto{

        @JsonProperty
        private Long id;

        public SimpleEventIdDto() {

        }

    }


    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventImageDto {
        @JsonProperty
        private Long id;

        @JsonProperty
        private String src;

        public EventImageDto () {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventReviewDto {
        @JsonProperty
        private Long id;

        private Long starRating;

        public  EventReviewDto() {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventPostReviewIdDto {
        @JsonProperty
        private Long id;

        public  EventPostReviewIdDto() {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventReviewImageDto {
        @JsonProperty
        private Long id;

        @JsonProperty
        private String src;

        public EventReviewImageDto () {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class NoticeImageDto {
        @JsonProperty
        private Long id;

        @JsonProperty
        private String src;

        public NoticeImageDto () {

        }
    }
    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class RecommendDto {
        @JsonProperty
        private Long id;
        @JsonProperty
        private String title;
        @JsonProperty
        private String category;
        @JsonProperty
        private LocalDateTime startDate;
        @JsonProperty
        private LocalDateTime endDate;
        @JsonProperty
        private String location;
        @JsonProperty
        private String description;
        @JsonProperty
        private String src;
        public RecommendDto() {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventPriceDto {
        @JsonProperty
        private Long id;
        @JsonProperty
        private String target;
        @JsonProperty
        private Long price;
        public EventPriceDto(){

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventOpenDto {
        @JsonProperty
        private Long id;
        @JsonProperty
        private Long day;
        @JsonProperty
        private LocalTime startAt;
        @JsonProperty
        private LocalTime closeAt;
        public EventOpenDto(){

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventCloseDto {
        @JsonProperty
        private Long id;
        @JsonProperty
        private boolean type;
        @JsonProperty
        private LocalDateTime date;
        @JsonProperty
        private LocalTime startAt;
        @JsonProperty
        private LocalTime closeAt;

        public EventCloseDto(){

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class EventPlaceDto {
        @JsonProperty
        private Long id;
        @JsonProperty
        private String location;
        @JsonProperty
        private String placeName;
        @JsonProperty
        private String homepage;
        @JsonProperty
        private String locationX;
        @JsonProperty
        private String locationY;

        public EventPlaceDto(){

        }
    }
    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public  static  class TicketDto{
        @JsonProperty
        private Long id;
        @JsonProperty
        private String code;
        @JsonProperty
        private LocalDateTime startAt;
        @JsonProperty
        private LocalDateTime date;
        public TicketDto() {

        }

    }
}


