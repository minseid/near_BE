package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventPlaceApiRequest {

    private Long id;

    private String location;

    private String placeName;

    private String homepage;

    private String locationX;

    private String locationY;

    private Long eventId;
}
