package com.api.deso.model.network.response;

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
public class TicketApiResponse {
    private Long id;

    private String code;

    private LocalDateTime date;
    private LocalDateTime startAt;
    private Long companyId;
    private Long eventId;

    private List<PurchaseLogApiReaponse> purchaseLogList;
}
