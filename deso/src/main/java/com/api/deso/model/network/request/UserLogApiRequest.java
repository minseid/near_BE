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
public class UserLogApiRequest {
    private Long id;
    private String title;
    private String link;
    private String type;

    private LocalDateTime createAt;

    private Long UserId;

}
