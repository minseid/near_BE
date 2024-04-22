package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.EventOpen;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventOpenApiRequest;
import com.api.deso.model.network.response.EventOpenApiResponse;
import com.api.deso.repository.EventOpenRepository;
import com.api.deso.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventOpenApiLogicService implements CrudInterface<EventOpenApiRequest, EventOpenApiResponse> {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventOpenRepository eventOpenRepository;

    @Override
    public Header<EventOpenApiResponse> create(Header<EventOpenApiRequest> request){
        EventOpenApiRequest eventOpenApiRequest = request.getData();

        EventOpen eventOpen = EventOpen.builder()
                .day(eventOpenApiRequest.getDay())
                .startAt(eventOpenApiRequest.getStartAt())
                .closeAt(eventOpenApiRequest.getCloseAt())
                .event(eventRepository.findById(eventOpenApiRequest.getId()).get())
                .build();

        EventOpen newEventOpen = eventOpenRepository.save(eventOpen);

        return response(newEventOpen);
    }

    @Override
    public Header<EventOpenApiResponse> read(Long id) {
        return eventOpenRepository.findById(id)
                .map(eventOpen -> response(eventOpen))
                .orElseGet(
                        ()-> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<EventOpenApiResponse> update(Header<EventOpenApiRequest> request) {
        EventOpenApiRequest eventOpenApiRequest = request.getData();

        EventOpen eventOpen = eventOpenRepository.findById(eventOpenApiRequest.getId()).get();
        EventOpen eventOpen1 = EventOpen.builder()
                .id(eventOpen.getId())
                .day(eventOpen.getDay())
                .startAt(eventOpen.getStartAt())
                .closeAt(eventOpen.getCloseAt())
                .event(eventRepository.findById(eventOpen.getId()).get())
                .build();

        EventOpen newEventOpen = eventOpenRepository.save(eventOpen1);
        return response(newEventOpen);
    }

    @Override
    public Header<EventOpenApiResponse> delete(Long id) {
        eventOpenRepository.deleteById(id);
        return Header.OK();
    }

    private Header<EventOpenApiResponse> response(EventOpen eventOpen){

        EventOpenApiResponse eventOpenApiResponse = EventOpenApiResponse.builder()
                .id(eventOpen.getId())
                .day(eventOpen.getDay())
                .startAt(eventOpen.getStartAt())
                .closeAt(eventOpen.getCloseAt())
                .eventId(eventOpen.getEvent().getId())
                .build();

        return Header.OK(eventOpenApiResponse);
    }
}
