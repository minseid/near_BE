package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.EventPlace;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventPlaceApiRequest;
import com.api.deso.model.network.response.EventPlaceApiResponse;
import com.api.deso.repository.EventPlaceRepository;
import com.api.deso.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPlaceApiLogicService implements CrudInterface<EventPlaceApiRequest, EventPlaceApiResponse> {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventPlaceRepository eventPlaceRepository;

    @Override
    public Header<EventPlaceApiResponse> create(Header<EventPlaceApiRequest> request){
        EventPlaceApiRequest eventPlaceApiRequest = request.getData();

        EventPlace eventPlace = EventPlace.builder()
                .location(eventPlaceApiRequest.getLocation())
                .placeName(eventPlaceApiRequest.getPlaceName())
                .homepage(eventPlaceApiRequest.getHomepage())
                .locationX(eventPlaceApiRequest.getLocationX())
                .locationY(eventPlaceApiRequest.getLocationY())
                .event(eventRepository.findById(eventPlaceApiRequest.getEventId()).get())
                .build();

        EventPlace newEventPlace = eventPlaceRepository.save(eventPlace);

        return response(newEventPlace);
    }

    @Override
    public Header<EventPlaceApiResponse> read(Long id) {
        return eventPlaceRepository.findById(id)
                .map(eventPlace -> response(eventPlace))
                .orElseGet(
                        ()-> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<EventPlaceApiResponse> update(Header<EventPlaceApiRequest> request) {
        EventPlaceApiRequest eventPlaceApiRequest = request.getData();

        EventPlace eventPlace = eventPlaceRepository.findById(eventPlaceApiRequest.getId()).get();
        EventPlace eventPlace1 = EventPlace.builder()
                .id(eventPlace.getId())
                .location(eventPlace.getLocation())
                .placeName(eventPlace.getPlaceName())
                .homepage(eventPlace.getHomepage())
                .locationX(eventPlace.getLocationX())
                .locationY(eventPlace.getLocationY())
                .event(eventRepository.findById(eventPlace.getId()).get())
                .build();

        EventPlace newEventPlace = eventPlaceRepository.save(eventPlace1);
        return response(newEventPlace);
    }

    @Override
    public Header<EventPlaceApiResponse> delete(Long id) {
        eventPlaceRepository.deleteById(id);
        return Header.OK();
    }

    private Header<EventPlaceApiResponse> response(EventPlace eventPlace){

        EventPlaceApiResponse eventPlaceApiResponse = EventPlaceApiResponse.builder()
                .id(eventPlace.getId())
                .location(eventPlace.getLocation())
                .placeName(eventPlace.getPlaceName())
                .homepage(eventPlace.getHomepage())
                .locationX(eventPlace.getLocationX())
                .locationY(eventPlace.getLocationY())
                .eventId(eventPlace.getEvent().getId())
                .build();

        return Header.OK(eventPlaceApiResponse);
    }
}
