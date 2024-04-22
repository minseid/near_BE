package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyApiRequest {
    private Long id;
    private String name;
    private String phoneNumber;
    private String businessNumber;
    private String location;
    private String email;
    private String onlineBusinessNum;

}
