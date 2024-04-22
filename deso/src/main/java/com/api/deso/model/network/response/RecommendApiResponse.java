package com.api.deso.model.network.response;

import com.api.deso.dto.EventDtoClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendApiResponse {

    private Long id;

    private String content;

    private Long eventId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private EventDtoClass.RecommendDto eventDto;

}
