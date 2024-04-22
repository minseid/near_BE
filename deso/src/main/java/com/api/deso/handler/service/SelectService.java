package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.dto.UserDtoClass;
import com.api.deso.model.entity.*;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.response.*;
import com.api.deso.repository.*;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SelectService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventHashtagRepository eventHashtagRepository;

    @Autowired
    private BookMarkRepository bookMarkRepository;

    @Autowired
    private ThemeEventRepository themeEventRepository;

    @Autowired
    private EventPlaceRepository eventPlaceRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    public Header<List<EventDtoClass.SimpleEventDto>> searchAllEvent(Pageable pageable) {

        return responseEvent(eventRepository.findAll(pageable).getContent());
    }
    public Header<List<EventDtoClass.SimpleEventDto>> searchEventBeforeAWeekAgo() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime after7 = localDateTime.plusDays(7);
        return responseEvent(eventRepository.findAllByEndDateIsBetween(localDateTime,after7));

    }
    public Header<List<EventDtoClass.SimpleEventDto>> searchHashTagEvent(String name){
        return responseEvent(eventHashtagRepository.findByContentLike(name));
    }

    public Header<List<EventDtoClass.SimpleEventDto>> searchNameEvent(String name) {
        return responseEvent(eventRepository.findByTitleContaining(name));
    }

    public Header<List<EventDtoClass.SimpleEventDto>> searchCategoryEvent(String category) {
        return responseEvent(eventRepository.findByCategoryContaining(category));
    }

    public Header<List<EventDtoClass.SimpleEventDto>> eventRandomSearch(Long num) {
        if(num>100L)
            num = 100L;
        return responseEvent(eventRepository.eventRandomSearch(num));
    }


    private Header<List<EventDtoClass.SimpleEventDto>> responseEvent(List<Event> eventList) {
        List<EventDtoClass.SimpleEventDto> simpleEventDtoList = new ArrayList<>();
        for(Event event : eventList){
            EventDtoClass.SimpleEventDto simpleEventDto =
                    EventDtoClass.SimpleEventDto.builder()
                            .id(event.getId())
                            .title(event.getTitle())
                            .category(event.getCategory())
                            .startDate(event.getStartDate())
                            .endDate(event.getEndDate())
                            .createdAt(event.getCreatedAt())
                            .updatedAt(event.getUpdatedAt())
                            .view(event.getView())
                            .description(event.getDescription())
                            .homepage(event.getHomepage())
                            .build();
            simpleEventDtoList.add(simpleEventDto);
        }

        return Header.OK(simpleEventDtoList);
    }

    public Header<List<BookMarkApiResponse>> searchUserBookMark(Long userId) {
        List<BookMark> bookMarkList = bookMarkRepository.findByUserId(userId);
        List<BookMarkApiResponse> bookMarkApiResponseList = new ArrayList<>();
        for(BookMark bookMark : bookMarkList){
            BookMarkApiResponse bookMarkApiResponse =  BookMarkApiResponse.builder()
                    .id(bookMark.getId())
                    .eventId(bookMark.getEvent().getId())
                    .userId(bookMark.getUser().getId())
                    .build();
            bookMarkApiResponseList.add(bookMarkApiResponse);
        }
        return Header.OK(bookMarkApiResponseList);
    }

    public Header<List<ThemeApiResponse>> searchContentTheme(String content) {
        List <Theme> themeList = themeRepository.findByContentContains(content);
        return Header.OK(responseTheme(themeList));
    }

    public Header<List<ThemeApiResponse>> searchAllTheme() {
        List <Theme> themeList = themeRepository.findAll();
        return Header.OK(responseTheme(themeList));
    }

    public Header<List<ThemeEventApiResponse>> searchThemeEventByEventId(Long id) {
        List <ThemeEvent> themeEventList = themeEventRepository.findByEventId(id);

        return Header.OK(responseThemeEvent(themeEventList));
    }

    public Header<List<ThemeEventApiResponse>> searchThemeEventByThemeId(Long id) {
        List <ThemeEvent> themeEventList = themeEventRepository.findByThemeId(id);

        return Header.OK(responseThemeEvent(themeEventList));
    }

    public Header<List<ThemeApiResponse>> themeRandomSearch(Long num) {
        List <Theme> themeList = themeRepository.themeRandomSearch(num);

        return Header.OK(responseTheme(themeList));
    }

    public  List<ThemeEventApiResponse> responseThemeEvent(List <ThemeEvent> themeEventList){
        List<ThemeEventApiResponse> themeEventApiResponseList = new ArrayList<>();
        for(ThemeEvent themeEvent : themeEventList ){
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
        return themeEventApiResponseList;
    }
    public List<ThemeApiResponse> responseTheme(List <Theme> themeList){

        List <ThemeApiResponse> themeApiResponseList = new ArrayList<>();
        for(Theme theme : themeList){
            List <ThemeEventApiResponse> themeEventApiResponseList =  responseThemeEvent(theme.getThemeEventList());
            ThemeApiResponse themeApiResponse = ThemeApiResponse.builder()
                    .id(theme.getId())
                    .content(theme.getContent())
                    .type(theme.getType())
                    .themeEventList(themeEventApiResponseList)
                    .build();
            themeApiResponseList.add(themeApiResponse);
        }

        return themeApiResponseList;
    }

    private Header<List<UserDtoClass.SimpleInfo>> responseUser(List<User> userList) {
        List<UserDtoClass.SimpleInfo> simpleUserDtoList = new ArrayList<>();
        for(User user : userList){
            UserDtoClass.SimpleInfo simpleUserDto =
                    UserDtoClass.SimpleInfo.builder()
                            .id(user.getId())
                            .nickname(user.getNickname())
                            .email(user.getEmail())
                            .build();
            simpleUserDtoList.add(simpleUserDto);
        }

        return Header.OK(simpleUserDtoList);
    }

    public Header<List<UserDtoClass.SimpleInfo>> searchAllUser(Pageable pageable) {
        return responseUser(userRepository.findAll(pageable).getContent());
    }

    public Header<List<EventDtoClass.SimpleEventDto>> searchLocationEvent(String location) {
        List<EventPlace> eventPlaceList = eventPlaceRepository.findByLocationContaining(location);
        List<Event> eventList = new ArrayList<>();
        for(EventPlace eventPlace : eventPlaceList){
            Event event = eventPlace.getEvent();
            eventList.add(event);
        }
        return responseEvent(eventList);
    }
}
