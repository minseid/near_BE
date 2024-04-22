package com.api.deso.model.network.response;

import com.api.deso.dto.EventDtoClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrawlingApiResponse {

    List<List<String>> crawlingData;

}
