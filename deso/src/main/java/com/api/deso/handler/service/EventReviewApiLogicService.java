package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.EventReview;
import com.api.deso.model.entity.EventReviewImage;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventReviewApiRequest;
import com.api.deso.model.network.response.EventReviewApiResponse;
import com.api.deso.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventReviewApiLogicService implements CrudInterface<EventReviewApiRequest, EventReviewApiResponse> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventReviewRepository eventReviewRepository;

    @Autowired
    private EventReviewImageRepository eventReviewImageRepository;
    @Override
    public Header< EventReviewApiResponse> create(Header<EventReviewApiRequest> request) {
        EventReviewApiRequest eventReviewApiRequest = request.getData();

        if (userRepository.findById(eventReviewApiRequest.getUserId()) == null) return Header.ERROR("Unknown user");

        EventReview eventReview = EventReview.builder()
                .content(eventReviewApiRequest.getContent())
                .starRating(eventReviewApiRequest.getStarRating())
                .warning(0L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .event(eventRepository.findById(eventReviewApiRequest.getEventId()).get())
                .user(userRepository.findById(eventReviewApiRequest.getUserId()).get())
                .build();

        EventReview newEventReview = eventReviewRepository.save(eventReview);

        newEventReview.setEventReviewImages(new ArrayList<>());
        return response(newEventReview);
    }

    @Override
    public Header<EventReviewApiResponse> read(Long id) {
        return eventReviewRepository.findById(id)
                .map(eventReview ->  response(eventReview))
                .orElseGet(
                        ()-> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<EventReviewApiResponse> update(Header<EventReviewApiRequest> request) {
        EventReviewApiRequest eventReviewApiRequest = request.getData();
        Optional<User> optionalWriter = userRepository.findById(eventReviewApiRequest.getUserId());
        User writer;
        if (optionalWriter.isPresent()) writer = optionalWriter.get();
        else return Header.ERROR("Wrong User");

        List<EventReviewImage> eventReviewImage =  eventReviewImageRepository.findByEventReviewId(eventReviewApiRequest.getId());
        EventReview eventReview = eventReviewRepository.findById(eventReviewApiRequest.getId()).get();
        EventReview eventReview1 = EventReview.builder()
                .id(eventReviewApiRequest.getId())
                .content(eventReviewApiRequest.getContent())
                .starRating(eventReviewApiRequest.getStarRating())
                .warning(eventReview.getWarning())
                .createdAt(eventReview.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .event(eventRepository.findById(eventReviewApiRequest.getEventId()).get())
                .user(userRepository.findById(eventReviewApiRequest.getUserId()).get())
                .eventReviewImages(eventReviewImage)
                .build();

        EventReview newEventReview = eventReviewRepository.save(eventReview1);
        return response(newEventReview);
    }

    @Override
    public Header<EventReviewApiResponse> delete(Long id) {
        List<EventReviewImage> eventReviewImageList  = eventReviewImageRepository.findByEventReviewId(id);
        eventReviewImageRepository.deleteAll(eventReviewImageList);
        eventReviewRepository.deleteById(id);
        return Header.OK();
    }

    private Header<EventReviewApiResponse> response(EventReview eventReview) {
        List<EventReviewImage> eventReview1=eventReview.getEventReviewImages();
        List <EventDtoClass.EventReviewImageDto> eventReviewImageDtoList = new ArrayList<>();
        for(int i=0; i<eventReview1.size();i++){
            EventDtoClass.EventReviewImageDto eventReviewImageDto = new EventDtoClass.EventReviewImageDto();
            eventReviewImageDto.setId(eventReview1.get(i).getId());
            eventReviewImageDto.setSrc(eventReview1.get(i).getSrc());
            eventReviewImageDtoList.add(eventReviewImageDto);
        }

        EventReviewApiResponse eventReviewApiResponse = EventReviewApiResponse.builder()
                .id(eventReview.getId())
                .content(eventReview.getContent())
                .starRating(eventReview.getStarRating())
                .warning(eventReview.getWarning())
                .userId(eventReview.getUser().getId())
                .createdAt(eventReview.getCreatedAt())
                .updatedAt(eventReview.getUpdatedAt())
                .eventId(eventReview.getEvent().getId())
                .eventReviewImageDtoList(eventReviewImageDtoList)
                .build();

        return Header.OK(eventReviewApiResponse);
    }
}
