package com.api.deso.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeApiResponse {

    private Long id;

    private String content;

    private Long type;

    private List<ThemeEventApiResponse> themeEventList;

}
