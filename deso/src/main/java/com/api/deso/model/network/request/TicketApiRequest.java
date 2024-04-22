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
public class TicketApiRequest {
    private Long id;

    private String code;

    private LocalDateTime date;
    private LocalDateTime startAt;
    private Long companyId;
    private Long eventId;
}
