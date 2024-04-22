package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.EventHashtag;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.HashtagApiRequest;
import com.api.deso.model.network.response.HashtagApiResponse;
import com.api.deso.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventHashTagLogicService implements CrudInterface<HashtagApiRequest, HashtagApiResponse> {


    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventHashtagRepository eventHashtagRepository;



    @Override
    public Header<HashtagApiResponse> create(Header<HashtagApiRequest> request) {
        HashtagApiRequest eventHashTagApiRequest = request.getData();

        EventHashtag eventHashtag = EventHashtag.builder()
                .content(eventHashTagApiRequest.getContent())
                .event(eventRepository.findById(eventHashTagApiRequest.getEventId()).get())
                .build();

        EventHashtag newEventHashTag = eventHashtagRepository.save(eventHashtag);

        return response(newEventHashTag);
    }

    @Override
    public Header<HashtagApiResponse> read(Long id) {
        return eventHashtagRepository.findById(id)
                .map(this::response)
                .orElseGet(
                        ()-> Header.ERROR("NULL")
                );
    }

    public  Header<List<HashtagApiResponse>> readAll() {

        List<EventHashtag> eventHashtagList =  eventHashtagRepository.findAll();
        List<HashtagApiResponse> hashtagApiResponseList = new ArrayList<>();
        for (EventHashtag eventHashtag : eventHashtagList) {

            HashtagApiResponse hashTagApiResponse = HashtagApiResponse.builder()
                    .id(eventHashtag.getId())
                    .content(eventHashtag.getContent())
                    .eventId(eventHashtag.getEvent().getId())
                    .build();

            hashtagApiResponseList.add(hashTagApiResponse);
        }

        return Header.OK(hashtagApiResponseList);
    }

    @Override
    public Header<HashtagApiResponse> update(Header<HashtagApiRequest> request) {
        HashtagApiRequest HashTagApiRequest = request.getData();
        EventHashtag eventHashtag = eventHashtagRepository.findById(HashTagApiRequest.getId()).get();
        EventHashtag eventHashtag1 = EventHashtag.builder()
                .id(HashTagApiRequest.getId())
                .content(HashTagApiRequest.getContent())
                .event(eventHashtag.getEvent())
                .build();

        EventHashtag newEventHashtag = eventHashtagRepository.save( eventHashtag1);
        return response(newEventHashtag);
    }

    @Override
    public Header<HashtagApiResponse> delete(Long id) {

        eventHashtagRepository.deleteById(id);
        return Header.OK();
    }

    private Header<HashtagApiResponse> response(EventHashtag eventHashtag) {

        HashtagApiResponse hashTagApiResponse = HashtagApiResponse.builder()
                .id(eventHashtag.getId())
                .content(eventHashtag.getContent())
                .eventId(eventHashtag.getEvent().getId())
                .build();

        return Header.OK(hashTagApiResponse);
    }
}