package com.api.deso.dto;

import lombok.*;

import java.time.LocalDateTime;

public class UserDtoClass {

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    @Data
    public static class SimpleInfo {
        private Long id;
        private String nickname;
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class phoneType {

        private String type;
        private String phone;

        public phoneType() {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class friendDto {

        private Long userId2;
        private String status;

        public friendDto() {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class blacklistDto {

        private Long userId2;
        private String status;

        public blacklistDto() {

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class chatDto {
        private Long id;
        private String type;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class pushDto {
        private String token;
        private String title;
        private String message;
        private String link;
        private String device;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class purchaseLogDto{
        private Long id;
        private String name;
        private String phoneNumber;
        private String email;
        private String payment;
        public purchaseLogDto(){

        }
    }
    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class userLogDto{
        private Long id;
        private String title;
        private String link;
        private String type;
        private LocalDateTime createdAt;
        public userLogDto(){

        }
    }
}
