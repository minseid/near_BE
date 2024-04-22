package com.api.deso.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventOpenApiResponse {

    private Long id;

    private Long day;

    private LocalTime startAt;

    private LocalTime closeAt;

    private Long eventId;
}
