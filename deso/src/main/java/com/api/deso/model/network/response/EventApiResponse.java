package com.api.deso.model.network.response;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.model.network.request.HashtagApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventApiResponse {
    private Long id;

    private String title;

    private String category;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long view;

    private String description;

    private String homepage;

    private Long userId;

    List<EventDtoClass.EventImageDto> eventImageList;

    List<HashtagApiResponse> eventHashtagList;

    List<EventDtoClass.EventPriceDto> eventPriceList;

    List<EventDtoClass.EventOpenDto> eventOpenList;

    List<EventDtoClass.EventCloseDto> eventCloseList;

    EventDtoClass.EventPlaceDto eventPlace;

    //티켓

    List<EventDtoClass.EventImageDto> promotionImageList;

    List<EventDtoClass.EventReviewDto> eventReviewList;

    List<EventDtoClass.EventPostReviewIdDto> eventPostReviewList;

    List <BookMarkApiResponse> bookMarkList;
}
