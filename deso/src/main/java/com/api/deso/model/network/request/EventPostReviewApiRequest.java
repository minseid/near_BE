package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPostReviewApiRequest {

    private Long id;

    private String title;

    private String content;

    private String image;

    private String url;

    private Long warning;

    private Long eventId;

    private Long userId;

}
