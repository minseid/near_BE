package com.api.deso.dto;
import lombok.*;
import java.time.LocalDateTime;
public class CompanyDtoClass {
    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    @Data
    public static class companyDto {
        private Long id;
        private String name;
        private String phoneNumber;
        private String businessNum;
        private String location;
        private String email;
        private String onlineBusinessNum;
    }
}
