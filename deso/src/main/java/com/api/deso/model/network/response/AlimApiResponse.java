package com.api.deso.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimApiResponse {

        private Long id;

        private String title;

        private String content;

        private Long to;

        private Long from;

        private String type;

        private String link;

        private Boolean showFrom;

        private Boolean showTo;

        private LocalDateTime createdAt;

}
