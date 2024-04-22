package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseLogApiRequest {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String payment;
    private Long userId;
    private Long ticketId;
}
