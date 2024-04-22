package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCloseApiRequest {

    private Long id;

    private boolean type;

    private LocalDateTime date;

    private LocalTime startAt;

    private LocalTime closeAt;

    private Long eventId;
}
