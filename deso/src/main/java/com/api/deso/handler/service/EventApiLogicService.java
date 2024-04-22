package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.*;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventApiRequest;
import com.api.deso.model.network.response.*;
import com.api.deso.repository.*;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventApiLogicService implements CrudInterface<EventApiRequest, EventApiResponse> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventImageRepository eventImageRepository;

    @Autowired
    private  EventReviewRepository eventReviewRepository;

    @Autowired
    private EventPostReviewRepository eventPostReviewRepository;

    @Autowired
    private RecommendRepository recommendRepository;

    @Autowired
    private EventHashtagRepository eventHashtagRepository;

    @Autowired
    private PromotionImageRepository promotionImageRepository;

    @Autowired
    private BookMarkRepository bookMarkRepository;
    @Autowired
    private ThemeEventRepository themeEventRepository;

    @Autowired
    private EventCloseRepository eventCloseRepository;
    @Autowired
    private EventOpenRepository eventOpenRepository;
    @Autowired
    private EventPlaceRepository eventPlaceRepository;
    @Autowired
    private EventPriceRepository eventPriceRepository;

    @Override
    public Header<EventApiResponse> create(Header<EventApiRequest> request) {
        // 1. request data
        EventApiRequest eventApiRequest = request.getData();

        User user = userRepository.findById(eventApiRequest.getUserId()).orElse(null);
        // 2. User 검증
        if (user == null) return Header.ERROR("Unknown user");

        Event event = Event.builder()
                .title(eventApiRequest.getTitle())
                .category(eventApiRequest.getCategory())
                .startDate(eventApiRequest.getStartDate())
                .endDate(eventApiRequest.getEndDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .view(0L)
                .description(eventApiRequest.getDescription())
                .homepage(eventApiRequest.getHomepage())
                .user(user)
                .build();

        Event newEvent = eventRepository.save(event);

        List<BookMark> bookMarkList = new ArrayList<>();
        newEvent.setBookMarkList(bookMarkList);
        // 5. eventImage 생성
        List<EventImage> eventImageList = new ArrayList<>();
        if(eventApiRequest.getEventImageList() != null) {
            eventApiRequest.getEventImageList()
                    .forEach(image -> {
                        EventImage eventImage = EventImage.builder()
                                .src(image)
                                .event(newEvent)
                                .build();
                        eventImageList.add(eventImage);

                    });

            eventImageRepository.saveAll(eventImageList);
        }

        List<PromotionImage> promotionImageList = new ArrayList<>();
        if(eventApiRequest.getPromotionImageList() != null) {
            eventApiRequest.getPromotionImageList().forEach(image -> {
                        PromotionImage promotionImage =  PromotionImage.builder()
                                .src(image)
                                .event(newEvent)
                                .build();
                        promotionImageList.add(promotionImage);
                    });
            promotionImageRepository.saveAll(promotionImageList);
        }

        List<EventHashtag> eventHashtagList = new ArrayList<>();
        if(eventApiRequest.getEventHashtagList() != null) {
            eventApiRequest.getEventHashtagList().forEach(hashtag -> {
                       EventHashtag eventHashTag =  EventHashtag.builder()
                                .content(hashtag)
                                .event(newEvent)
                                .build();
                        eventHashtagList.add(eventHashTag);
                    });
            eventHashtagRepository.saveAll(eventHashtagList);
        }

        List<EventOpen> eventOpenList = new ArrayList<>();
        if(eventApiRequest.getEventOpenList()!=null){
            eventApiRequest.getEventOpenList().forEach(open-> {
                EventOpen eventOpen =  EventOpen.builder()
                        .day(open.getDay())
                        .startAt(open.getStartAt())
                        .closeAt(open.getCloseAt())
                        .event(newEvent)
                        .build();
                eventOpenList.add(eventOpen);


            });
            eventOpenRepository.saveAll(eventOpenList);
        }

        List<EventClose> eventCloseList = new ArrayList<>();
        if(eventApiRequest.getEventCloseList()!=null){
            eventApiRequest.getEventCloseList().forEach(close-> {
                EventClose eventClose =  EventClose.builder()
                        .type(close.isType())
                        .date(close.getDate())
                        .startAt(close.getStartAt())
                        .closeAt(close.getCloseAt())
                        .event(newEvent)
                        .build();
                eventCloseList.add(eventClose);


            });
            eventCloseRepository.saveAll(eventCloseList);
        }

        List<EventPrice> eventPriceList = new ArrayList<>();
        if(eventApiRequest.getEventPriceList() != null) {
            eventApiRequest.getEventPriceList().forEach(Price -> {
                EventPrice eventPrice =  EventPrice.builder()
                        .target(Price.getTarget())
                        .price(Price.getPrice())
                        .event(newEvent)
                        .build();
                eventPriceList.add(eventPrice);

            });
        }
        eventPriceRepository.saveAll(eventPriceList);
        EventPlace eventPlace=null;
        if(eventApiRequest.getEventPlace() != null){
                eventPlace =  EventPlace.builder()
                        .location(eventApiRequest.getEventPlace().getLocation())
                        .locationX(eventApiRequest.getEventPlace().getLocationX())
                        .locationY(eventApiRequest.getEventPlace().getLocationY())
                        .placeName(eventApiRequest.getEventPlace().getPlaceName())
                        .homepage(eventApiRequest.getEventPlace().getHomepage())
                        .event(newEvent)
                        .build();
                eventPlaceRepository.save(eventPlace);
        }


        newEvent.setEventImageList(eventImageList);
        newEvent.setPromotionImageList(promotionImageList);
        List<EventReview> eventReviewList = new ArrayList<>();
        newEvent.setEventReviewList(eventReviewList);
        List<EventPostReview> eventPostReviewList = new ArrayList<>();
        newEvent.setEventPostReviewList(eventPostReviewList);
        newEvent.setEventHashtagList(eventHashtagList);
        newEvent.setEventPlace(eventPlace);
        newEvent.setEventOpenList(eventOpenList);
        newEvent.setEventCloseList(eventCloseList);
        newEvent.setEventPriceList(eventPriceList);
        return response(newEvent);
    }

    @Override
    public Header<EventApiResponse> read(Long id) {

        return eventRepository.findById(id)
                .map(event -> {
                    eventRepository.increaseViewById(id);
                    event.setView(event.getView());
                    event.setEventImageList(eventImageRepository.findByEventId(id));
                    event.setEventReviewList(eventReviewRepository.findByEventId(id));
                    event.setEventPostReviewList(eventPostReviewRepository.findByEventId(id));
                    event.setEventHashtagList(eventHashtagRepository.findByEventId(id));
                    event.setPromotionImageList(promotionImageRepository.findByEventId(id));
                    event.setBookMarkList(bookMarkRepository.findByEventId(id));
                    event.setEventCloseList(eventCloseRepository.findByEventId(id));
                    event.setEventOpenList(eventOpenRepository.findByEventId(id));
                    event.setEventPlace(eventPlaceRepository.findByEventId(id));
                    event.setEventPriceList(eventPriceRepository.findByEventId(id));
                    return response(event);
                })
                .orElseGet(
                        () -> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<EventApiResponse> update(Header<EventApiRequest> request) {

        EventApiRequest eventApiRequest = request.getData();

        Event event = eventRepository.findById(eventApiRequest.getId()).orElse(null);
        User writer = userRepository.findById(eventApiRequest.getUserId()).orElse(null);
        if (writer==null)
            return Header.ERROR("Wrong User");

        if (event==null)
            return Header.ERROR("Wrong User");

        if (writer != event.getUser())
            return Header.ERROR("Forbidden");


        List<EventReview> eventReviewList  = eventReviewRepository.findByEventId(eventApiRequest.getId());
        List<EventPostReview> eventPostReviewList = eventPostReviewRepository.findByEventId(eventApiRequest.getId());
        Event newEvent = Event.builder()
                .id(eventApiRequest.getId())
                .title(eventApiRequest.getTitle())
                .category(eventApiRequest.getCategory())
                .startDate(eventApiRequest.getStartDate())
                .endDate(eventApiRequest.getEndDate())
                .createdAt(event.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .view(event.getView())
                .description(eventApiRequest.getDescription())
                .homepage(eventApiRequest.getHomepage())
                .user(writer)
                .eventImageList(new ArrayList<EventImage>())
                .promotionImageList(new ArrayList<PromotionImage>())
                .eventOpenList(new ArrayList<EventOpen>())
                .eventCloseList(new ArrayList<EventClose>())
                .eventPriceList(new ArrayList<EventPrice>())
                .eventHashtagList(new ArrayList<EventHashtag>())
                .eventReviewList(new ArrayList<EventReview>())
                .eventPostReviewList(new ArrayList<EventPostReview>())
                .bookMarkList(new ArrayList<BookMark>())
                .build();

        Event newEvent1 = eventRepository.save(newEvent);
        return response(newEvent1);
    }

    @Override
    public Header<EventApiResponse> delete(Long id) {
        List<EventImage> eventImageList = eventImageRepository.findByEventId(id);
        List<EventReview> eventReviewList = eventReviewRepository.findByEventId(id);
        List<EventPostReview> eventPostReviewList = eventPostReviewRepository.findByEventId(id);
        List<Recommend> recommendList = recommendRepository.findByEventId(id);
        List<EventHashtag> eventHashtagList = eventHashtagRepository.findByEventId(id);
        List<PromotionImage> promotionImageList = promotionImageRepository.findByEventId(id);
        List<BookMark> bookMarkList = bookMarkRepository.findByEventId(id);
        List <ThemeEvent> themeEventList = themeEventRepository.findByEventId(id);
        List<EventOpen> eventOpenList = eventOpenRepository.findByEventId(id);
        List<EventClose> eventCloseList = eventCloseRepository.findByEventId(id);
        List<EventPrice> eventPriceList = eventPriceRepository.findByEventId(id);
        EventPlace eventPlace = eventPlaceRepository.findByEventId(id);

        eventImageRepository.deleteAll(eventImageList);
        eventReviewRepository.deleteAll(eventReviewList);
        eventPostReviewRepository.deleteAll(eventPostReviewList);
        recommendRepository.deleteAll(recommendList);
        eventHashtagRepository.deleteAll(eventHashtagList);
        promotionImageRepository.deleteAll(promotionImageList);
        bookMarkRepository.deleteAll(bookMarkList);
        themeEventRepository.deleteAll(themeEventList);
        eventOpenRepository.deleteAll(eventOpenList);
        eventCloseRepository.deleteAll(eventCloseList);
        eventPriceRepository.deleteAll(eventPriceList);
        eventPlaceRepository.delete(eventPlace);
        eventRepository.deleteById(id);
        return Header.OK();
    }


    private Header<EventApiResponse> response(Event event) {

        List <EventDtoClass.EventReviewDto> eventReviewIdDtoList  = new ArrayList<>();
        List <EventDtoClass.EventPostReviewIdDto> eventPostReviewIdDtoList  = new ArrayList<>();
        List<EventReview> eventReviewList = event.getEventReviewList();
        List<EventPostReview> eventPostReviewList = event.getEventPostReviewList();

        List <BookMark> bookMarkList = event.getBookMarkList();
        List <BookMarkApiResponse> bookMarkApiResponseList = new ArrayList<>();

        List <EventHashtag> eventHashtagList =  event.getEventHashtagList();
        List<HashtagApiResponse> hashtagApiResponseList = new ArrayList<>();

        List <EventOpen> eventOpenList =  event.getEventOpenList();
        List<EventDtoClass.EventOpenDto> eventOpenIdDtoList = new ArrayList<>();
        List <EventClose> eventCloseList =  event.getEventCloseList();
        List<EventDtoClass.EventCloseDto> eventCloseIdDtoList = new ArrayList<>();
        List <EventPrice> eventPriceList =  event.getEventPriceList();
        List<EventDtoClass.EventPriceDto> eventPriceIdDtoList = new ArrayList<>();
        EventPlace eventPlace = event.getEventPlace();
        EventDtoClass.EventPlaceDto eventPlaceDto = EventDtoClass.EventPlaceDto.builder()
                .id(eventPlace.getId())
                .location(eventPlace.getLocation())
                .placeName(eventPlace.getPlaceName())
                .locationX(eventPlace.getLocationX())
                .locationY(eventPlace.getLocationY())
                .homepage(eventPlace.getHomepage())
                .build();

        for (EventReview eventReview : eventReviewList) {
            EventDtoClass.EventReviewDto eventReviewDto = new EventDtoClass.EventReviewDto();
            eventReviewDto.setId(eventReview.getId());
            eventReviewDto.setStarRating(eventReview.getStarRating());
            eventReviewIdDtoList.add(eventReviewDto);
        }

        for(int i=0;i<eventPostReviewList.size();i++){
            EventDtoClass.EventPostReviewIdDto eventPostReviewIdDto = new EventDtoClass.EventPostReviewIdDto();
            eventPostReviewIdDto.setId(eventReviewList.get(i).getId());
            eventPostReviewIdDtoList.add(eventPostReviewIdDto);
        }

        for (BookMark bookMark : bookMarkList) {
            BookMarkApiResponse bookMarkApiResponse = new BookMarkApiResponse();
            bookMarkApiResponse.setId(bookMark.getId());
            bookMarkApiResponse.setUserId(bookMark.getUser().getId());
            bookMarkApiResponse.setEventId(bookMark.getEvent().getId());
            bookMarkApiResponseList.add(bookMarkApiResponse);
        }

        for (EventHashtag eventHashtag : eventHashtagList) {
            HashtagApiResponse hashtagApiResponse = new HashtagApiResponse();
            hashtagApiResponse.setId(eventHashtag.getId());
            hashtagApiResponse.setContent(eventHashtag.getContent());
            hashtagApiResponse.setEventId(eventHashtag.getEvent().getId());
            hashtagApiResponseList.add(hashtagApiResponse);
        }

        for (EventOpen eventOpen : eventOpenList) {
            EventDtoClass.EventOpenDto eventOpenDto = new EventDtoClass.EventOpenDto();
            eventOpenDto.setId(eventOpen.getId());
            eventOpenDto.setDay(eventOpen.getDay());
            eventOpenDto.setStartAt(eventOpen.getStartAt());
            eventOpenDto.setCloseAt(eventOpen.getCloseAt());
            eventOpenIdDtoList.add(eventOpenDto);
        }
        for (EventClose eventClose : eventCloseList) {
            EventDtoClass.EventCloseDto eventCloseDto = new EventDtoClass.EventCloseDto();
            eventCloseDto.setId(eventClose.getId());
            eventCloseDto.setType(eventClose.isType());
            eventCloseDto.setDate(eventClose.getDate());
            eventCloseDto.setStartAt(eventClose.getStartAt());
            eventCloseDto.setCloseAt(eventClose.getCloseAt());
            eventCloseIdDtoList.add(eventCloseDto);
        }
        for (EventPrice eventPrice : eventPriceList) {
            EventDtoClass.EventPriceDto eventPriceDto = new EventDtoClass.EventPriceDto();
            eventPriceDto.setId(eventPrice.getId());
            eventPriceDto.setTarget(eventPrice.getTarget());
            eventPriceDto.setPrice(eventPrice.getPrice());
            eventPriceIdDtoList.add(eventPriceDto);
        }


        EventApiResponse eventApiResponse = EventApiResponse.builder()
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
                .userId(event.getUser().getId())
                .eventReviewList(eventReviewIdDtoList)
                .eventPostReviewList(eventPostReviewIdDtoList)
                .bookMarkList(bookMarkApiResponseList)
                .eventHashtagList(hashtagApiResponseList)
                .eventOpenList(eventOpenIdDtoList)
                .eventCloseList(eventCloseIdDtoList)
                .eventPriceList(eventPriceIdDtoList)
                .eventPlace(eventPlaceDto)
                .build();

                if(event.getEventImageList() != null) {
                   eventApiResponse.setEventImageList(Arrays.asList(modelMapper.map(event.getEventImageList(), EventDtoClass.EventImageDto[].class)));
                }
                if(event.getPromotionImageList() != null) {
                    eventApiResponse.setPromotionImageList(Arrays.asList(modelMapper.map(event.getPromotionImageList(), EventDtoClass.EventImageDto[].class)));
                }

        return Header.OK(eventApiResponse);
    }
}
