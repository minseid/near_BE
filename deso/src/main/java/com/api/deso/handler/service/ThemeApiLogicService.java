package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.Theme;
import com.api.deso.model.entity.ThemeEvent;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.ThemeApiRequest;
import com.api.deso.model.network.response.ThemeApiResponse;
import com.api.deso.model.network.response.ThemeEventApiResponse;
import com.api.deso.repository.EventRepository;
import com.api.deso.repository.ThemeEventRepository;
import com.api.deso.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeApiLogicService implements CrudInterface<ThemeApiRequest, ThemeApiResponse> {

    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ThemeEventRepository themeEventRepository;

    @Override
    public Header<ThemeApiResponse> create(Header<ThemeApiRequest> request) {

        ThemeApiRequest themeApiRequest = request.getData();

        Theme theme = Theme.builder()
                .content(themeApiRequest.getContent())
                .type(themeApiRequest.getType())
                .build();
        Theme newTheme = themeRepository.save(theme);

        List<ThemeEvent> themeEventList = new ArrayList<>();
        for(Long id : themeApiRequest.getThemeEventList()){

           Event event  =  eventRepository.findById(id).orElse(null);
           if(event!=null){
               ThemeEvent themeEvent = ThemeEvent.builder().theme(newTheme)
                       .event(event).build();
               ThemeEvent newThemeEvent = themeEventRepository.save(themeEvent);
               themeEventList.add(newThemeEvent);
           }

        }

        newTheme.setThemeEventList(themeEventList);

        return response(newTheme);
    }

    @Override
    public Header<ThemeApiResponse> read(Long id) {
        return themeRepository.findById(id)
                .map(theme -> response(theme))
                .orElseGet(
                        () -> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<ThemeApiResponse> update(Header<ThemeApiRequest> request) {
        ThemeApiRequest themeApiRequest = request.getData();
        Theme theme = themeRepository.findById(themeApiRequest.getId()).orElse(null);
        if(theme == null){
            return Header.ERROR("Wrong theme");
        }

        theme.setContent(themeApiRequest.getContent())
                .setType(themeApiRequest.getType());

        Theme newTheme = themeRepository.save(theme);
        return response(newTheme);
    }

    @Override
    public Header<ThemeApiResponse> delete(Long id) {
        List<ThemeEvent> themeEventList = themeEventRepository.findByThemeId(id);
        themeEventRepository.deleteAll(themeEventList);
        themeRepository.deleteById(id);
        return Header.OK();
    }


    private Header<ThemeApiResponse> response(Theme theme) {
        List<ThemeEventApiResponse> themeEventApiResponseList = new ArrayList<>();

        for( ThemeEvent themeEvent: theme.getThemeEventList() ){
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
            ThemeEventApiResponse themeEventApiResponse =
                    ThemeEventApiResponse.builder()
                    .id(themeEvent.getId())
                    .event(eventDto)
                    .themeId(themeEvent.getTheme().getId())
                    .build();

            themeEventApiResponseList.add(themeEventApiResponse);
        }

        ThemeApiResponse themeApiResponse = ThemeApiResponse.builder()
                .id(theme.getId())
                .type(theme.getType())
                .content(theme.getContent())
                .themeEventList(themeEventApiResponseList)
                .build();

        return Header.OK(themeApiResponse);
    }
}
