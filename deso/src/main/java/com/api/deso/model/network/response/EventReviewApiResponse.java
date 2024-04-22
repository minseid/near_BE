package com.api.deso.model.network.response;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventReviewApiResponse {

    private Long id;

    private String content;

    private Long starRating;

    private Long warning;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private Long eventId;

    private Long userId;

    private List<EventDtoClass.EventReviewImageDto> eventReviewImageDtoList;


}
