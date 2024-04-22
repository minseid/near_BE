package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.EventPostReview;
import com.api.deso.model.entity.EventReview;
import com.api.deso.model.entity.EventReviewImage;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventPostReviewApiRequest;
import com.api.deso.model.network.request.EventReviewApiRequest;
import com.api.deso.model.network.response.EventPostReviewApiResponse;
import com.api.deso.model.network.response.EventReviewApiResponse;
import com.api.deso.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventPostReviewApiLogicService implements CrudInterface<EventPostReviewApiRequest, EventPostReviewApiResponse> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventPostReviewRepository eventPostReviewRepository;





    @Override
    public Header<EventPostReviewApiResponse> create(Header<EventPostReviewApiRequest> request) {
        EventPostReviewApiRequest eventPostReviewApiRequest = request.getData();

        if (userRepository.findById(eventPostReviewApiRequest.getUserId()) == null) return Header.ERROR("Unknown user");

        EventPostReview eventPostReview = EventPostReview.builder()
                .title(eventPostReviewApiRequest.getTitle())
                .content(eventPostReviewApiRequest.getContent())
                .url(eventPostReviewApiRequest.getUrl())
                .warning(0L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .event(eventRepository.findById(eventPostReviewApiRequest.getEventId()).get())
                .user(userRepository.findById(eventPostReviewApiRequest.getUserId()).get())
                .build();

        EventPostReview newEventPostReview = eventPostReviewRepository.save(eventPostReview);

        return response(newEventPostReview);
    }

    @Override
    public Header<EventPostReviewApiResponse> read(Long id) {
        return eventPostReviewRepository.findById(id)
                .map(eventReview ->  response(eventReview))
                .orElseGet(
                        ()-> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<EventPostReviewApiResponse> update(Header<EventPostReviewApiRequest> request) {
        EventPostReviewApiRequest eventPostReviewApiRequest = request.getData();
        Optional<User> optionalWriter = userRepository.findById(eventPostReviewApiRequest.getUserId());
        User writer;
        if (optionalWriter.isPresent()) writer = optionalWriter.get();
        else return Header.ERROR("Wrong User");
        EventPostReview eventPostReview = eventPostReviewRepository.findById(eventPostReviewApiRequest.getId()).get();
        EventPostReview eventPostReview1 = EventPostReview.builder()
                .id(eventPostReviewApiRequest.getId())
                .title(eventPostReviewApiRequest.getTitle())
                .content(eventPostReviewApiRequest.getContent())
                .url(eventPostReviewApiRequest.getUrl())
                .warning(eventPostReview.getWarning())
                .createdAt(eventPostReview.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .event(eventRepository.findById(eventPostReviewApiRequest.getEventId()).get())
                .user(userRepository.findById(eventPostReviewApiRequest.getUserId()).get())
                .build();

        EventPostReview newEventPostReview = eventPostReviewRepository.save(eventPostReview1);
        return response(newEventPostReview);
    }

    @Override
    public Header<EventPostReviewApiResponse> delete(Long id) {

        eventPostReviewRepository.deleteById(id);

        return Header.OK();
    }

    private Header<EventPostReviewApiResponse> response(EventPostReview eventPostReview) {

        EventPostReviewApiResponse eventPostReviewApiResponse = EventPostReviewApiResponse.builder()
                .id(eventPostReview.getId())
                .title(eventPostReview.getTitle())
                .type(eventPostReview.getType())
                .content(eventPostReview.getContent())
                .url(eventPostReview.getUrl())
                .warning(eventPostReview.getWarning())
                .createdAt(eventPostReview.getCreatedAt())
                .updatedAt(eventPostReview.getUpdatedAt())
                .eventId(eventPostReview.getEvent().getId())
                .userId(eventPostReview.getUser().getId())
                .build();

        return Header.OK(eventPostReviewApiResponse);
    }
}
