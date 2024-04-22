package com.api.deso.model.network.response;

import com.api.deso.dto.EventDtoClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeApiResponse {

    private Long id;

    private String title;

    private String content;

    private Long userId;

    private String link;

    private String type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<EventDtoClass.NoticeImageDto> noticeImageDtoList;

}
