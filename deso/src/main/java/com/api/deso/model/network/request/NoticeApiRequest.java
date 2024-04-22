package com.api.deso.model.network.request;

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
public class NoticeApiRequest {

    private Long id;

    private String title;

    private String content;

    private Long userId;

    private String link;

    private String type;

    private String status;

    List<String> images;

}
