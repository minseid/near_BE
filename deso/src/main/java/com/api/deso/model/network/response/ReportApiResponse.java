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
public class ReportApiResponse {

    private Long id;

    private Long userId;

    private Long targetId;

    private String content;

    private String type;

    private LocalDateTime createdAt;
}
