package com.api.deso.controller.api;

import com.api.deso.handler.service.EventPriceApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventPriceApiRequest;
import com.api.deso.model.network.response.EventPriceApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/event/price")
public class EventPriceController implements CrudInterface<EventPriceApiRequest, EventPriceApiResponse> {

    @Autowired
    private EventPriceApiLogicService eventPriceApiLogicService;

    @Override
    @PostMapping(path="")
    public Header<EventPriceApiResponse> create(@RequestBody Header<EventPriceApiRequest> request) {
        return eventPriceApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id")
    public Header<EventPriceApiResponse> read(@PathVariable(name = "id") Long id) {
        return eventPriceApiLogicService.read(id);
    }

    @Override
    @PostMapping(path="update")
    public Header<EventPriceApiResponse> update(@RequestBody Header<EventPriceApiRequest> request) {
        return eventPriceApiLogicService.update(request);
    }

    @Override
    @DeleteMapping(path="delete/{id}")
    public Header<EventPriceApiResponse> delete(@PathVariable(name="id") Long id) {
        return eventPriceApiLogicService.delete(id);
    }


}
