package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.EventPrice;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventPriceApiRequest;
import com.api.deso.model.network.response.EventPriceApiResponse;
import com.api.deso.repository.EventPriceRepository;
import com.api.deso.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPriceApiLogicService  implements CrudInterface<EventPriceApiRequest, EventPriceApiResponse> {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventPriceRepository eventPriceRepository;

    @Override
    public Header<EventPriceApiResponse> create(Header<EventPriceApiRequest> request) {
        EventPriceApiRequest eventPriceApiRequest = request.getData();

        EventPrice eventPrice = EventPrice.builder()
                .target(eventPriceApiRequest.getTarget())
                .price(eventPriceApiRequest.getPrice())
                .event(eventRepository.findById(eventPriceApiRequest.getId()).get())
                .build();

        EventPrice newEventPrice = eventPriceRepository.save(eventPrice);

        return response(newEventPrice);
    }

    @Override
    public Header<EventPriceApiResponse> read(Long id) {
        return eventPriceRepository.findById(id)
                .map(eventPrice -> response(eventPrice))
                .orElseGet(
                        ()-> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<EventPriceApiResponse> update(Header<EventPriceApiRequest> request) {
        EventPriceApiRequest eventPriceApiRequest = request.getData();

        EventPrice eventPrice = eventPriceRepository.findById(eventPriceApiRequest.getId()).get();
        EventPrice eventPrice1 = EventPrice.builder()
                .id(eventPrice.getId())
                .target(eventPrice.getTarget())
                .price(eventPrice.getPrice())
                .event(eventRepository.findById(eventPrice.getId()).get())
                .build();

        EventPrice newEventPrice = eventPriceRepository.save(eventPrice1);
        return response(newEventPrice);
    }

    @Override
    public Header<EventPriceApiResponse> delete(Long id) {
        eventPriceRepository.deleteById(id);
        return Header.OK();
    }

    private Header<EventPriceApiResponse> response(EventPrice eventPrice){

        EventPriceApiResponse eventPriceApiResponse = EventPriceApiResponse.builder()
                .id(eventPrice.getId())
                .target(eventPrice.getTarget())
                .price(eventPrice.getPrice())
                .eventId(eventPrice.getEvent().getId())
                .build();

        return Header.OK(eventPriceApiResponse);
    }
}
