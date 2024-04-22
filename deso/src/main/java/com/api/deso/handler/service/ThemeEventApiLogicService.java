package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.Theme;
import com.api.deso.model.entity.ThemeEvent;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.ThemeEventApiRequest;
import com.api.deso.model.network.request.ThemeEventApiRequest;
import com.api.deso.model.network.response.ThemeEventApiResponse;
import com.api.deso.repository.EventRepository;
import com.api.deso.repository.ThemeEventRepository;
import com.api.deso.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThemeEventApiLogicService implements CrudInterface<ThemeEventApiRequest, ThemeEventApiResponse> {

    @Autowired
    private ThemeEventRepository themeEventRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private EventRepository eventRepository;
    @Override
    public Header<ThemeEventApiResponse> create(Header<ThemeEventApiRequest> request) {

        ThemeEventApiRequest themeEventApiRequest = request.getData();
        Event event = eventRepository.findById(themeEventApiRequest.getEventId()).orElse(null);
        Theme theme  = themeRepository.findById(themeEventApiRequest.getThemeId()).orElse(null);

        if(event==null)
            return Header.ERROR("Wrong Event");
        if(theme==null)
            return Header.ERROR("Wrong Theme");

        ThemeEvent themeEvent = ThemeEvent.builder()
                .event(event)
                .theme(theme)
                .build();

        ThemeEvent newThemeEvent = themeEventRepository.save(themeEvent);

        return response(newThemeEvent);
    }

    @Override
    public Header<ThemeEventApiResponse> read(Long id) {
        return themeEventRepository.findById(id)
                .map(themeEvent -> response(themeEvent))
                .orElseGet(
                        () -> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<ThemeEventApiResponse> update(Header<ThemeEventApiRequest> request) {

        return null;
    }

    @Override
    public Header<ThemeEventApiResponse> delete(Long id) {
        themeEventRepository.deleteById(id);
        return Header.OK();
    }

    private Header<ThemeEventApiResponse> response(ThemeEvent themeEvent) {
        EventDtoClass.SimpleEventDto eventDto = EventDtoClass.SimpleEventDto.builder()
                .id(themeEvent.getEvent().getId())
                .title(themeEvent.getEvent().getTitle())
                .category(themeEvent.getEvent().getCategory())
                .startDate(themeEvent.getEvent().getStartDate())
                .endDate(themeEvent.getEvent().getEndDate())
                .createdAt(themeEvent.getEvent().getCreatedAt())
                .updatedAt(themeEvent.getEvent().getUpdatedAt())
                .view(themeEvent.getEvent().getView())
                .description(themeEvent.getEvent().getDescription())
                .homepage(themeEvent.getEvent().getHomepage())
                .build();
        ThemeEventApiResponse themeEventApiResponse = ThemeEventApiResponse.builder()
                .id(themeEvent.getId())
                .event(eventDto)
                .themeId(themeEvent.getTheme().getId())
                .build();

        return Header.OK(themeEventApiResponse);
    }
}
