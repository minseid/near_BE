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
public class EventPostReviewApiResponse {

    private Long id;

    private String title;

    private String type;

    private String content;

    private String url;

    private Long warning;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long eventId;

    private Long userId;

}
