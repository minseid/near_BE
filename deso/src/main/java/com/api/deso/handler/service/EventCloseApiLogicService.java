package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.EventClose;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventCloseApiRequest;
import com.api.deso.model.network.response.EventCloseApiResponse;
import com.api.deso.repository.EventCloseRepository;
import com.api.deso.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventCloseApiLogicService implements CrudInterface<EventCloseApiRequest, EventCloseApiResponse> {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCloseRepository eventCloseRepository;

    @Override
    public Header<EventCloseApiResponse> create(Header<EventCloseApiRequest> request){
        EventCloseApiRequest eventCloseApiRequest = request.getData();

        EventClose eventClose = EventClose.builder()
                .type(eventCloseApiRequest.isType())
                .date(eventCloseApiRequest.getDate())
                .startAt(eventCloseApiRequest.getStartAt())
                .closeAt(eventCloseApiRequest.getCloseAt())
                .event(eventRepository.findById(eventCloseApiRequest.getId()).get())
                .build();

        EventClose newEventClose = eventCloseRepository.save(eventClose);

        return response(newEventClose);
    }

    @Override
    public Header<EventCloseApiResponse> read(Long id) {
        return eventCloseRepository.findById(id)
                .map(eventClose -> response(eventClose))
                .orElseGet(
                        ()-> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<EventCloseApiResponse> update(Header<EventCloseApiRequest> request) {
        EventCloseApiRequest eventCloseApiRequest = request.getData();

        EventClose eventClose = eventCloseRepository.findById(eventCloseApiRequest.getId()).get();
        EventClose eventClose1 = EventClose.builder()
                .id(eventClose.getId())
                .date(eventClose.getDate())
                .startAt(eventClose.getStartAt())
                .closeAt(eventClose.getCloseAt())
                .event(eventRepository.findById(eventClose.getId()).get())
                .build();

        EventClose newEventClose = eventCloseRepository.save(eventClose1);
        return response(newEventClose);
    }

    @Override
    public Header<EventCloseApiResponse> delete(Long id) {
        eventCloseRepository.deleteById(id);
        return Header.OK();
    }

    private Header<EventCloseApiResponse> response(EventClose eventClose){

        EventCloseApiResponse eventCloseApiResponse = EventCloseApiResponse.builder()
                .id(eventClose.getId())
                .date(eventClose.getDate())
                .startAt(eventClose.getStartAt())
                .closeAt(eventClose.getCloseAt())
                .eventId(eventClose.getEvent().getId())
                .build();

        return Header.OK(eventCloseApiResponse);
    }
}
