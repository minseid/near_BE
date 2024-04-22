package com.api.deso.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseLogApiReaponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String payment;
    private Long userId;
    private Long ticketId;
}
