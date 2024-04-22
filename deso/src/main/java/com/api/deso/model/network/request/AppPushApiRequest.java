package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppPushApiRequest {

    private String title;

    private String body;

//    private String image;

    private String token;

    private String type;

}
