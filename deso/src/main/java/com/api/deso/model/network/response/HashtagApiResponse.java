package com.api.deso.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HashtagApiResponse {

    private Long id;

    private String content;

    private Long eventId;

}
