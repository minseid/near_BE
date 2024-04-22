package com.api.deso.model.network.response;

import com.api.deso.dto.EventDtoClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeEventApiResponse {

    private Long id;

    private EventDtoClass.SimpleEventDto event;

    private Long themeId;

}
