package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimApiRequest {

    private  Long id;

    private String title;

    private String content;

    private Long to;

    private Long from;

    private String type;

    private String link;

}

