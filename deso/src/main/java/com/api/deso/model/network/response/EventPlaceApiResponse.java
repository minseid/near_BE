package com.api.deso.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPlaceApiResponse {

    private Long id;

    private String location;

    private String placeName;

    private String homepage;

    private String locationX;

    private String locationY;

    private Long eventId;
}
