package com.api.deso.model.network.request;

import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventReviewApiRequest {

    private Long id;

    private String content;

    private Long starRating;

    private Long warning;

    private Long eventId;

    private Long userId;

    List<String> images;

}
