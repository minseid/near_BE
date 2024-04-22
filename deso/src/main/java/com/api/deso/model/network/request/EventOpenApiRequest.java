package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventOpenApiRequest {

    private Long id;

    private Long day;

    private LocalTime startAt;

    private LocalTime closeAt;

    private Long eventId;
}
