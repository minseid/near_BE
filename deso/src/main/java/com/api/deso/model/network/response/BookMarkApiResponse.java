package com.api.deso.model.network.response;

import com.api.deso.model.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookMarkApiResponse {

        private Long id;

        private Long userId;

        private Long eventId;

}
