package com.api.deso.controller.api;

import com.api.deso.handler.service.EventPlaceApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventPlaceApiRequest;
import com.api.deso.model.network.response.EventPlaceApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/event/place")
public class EventPlaceController implements CrudInterface<EventPlaceApiRequest, EventPlaceApiResponse> {

    @Autowired
    private EventPlaceApiLogicService eventPlaceApiLogicService;

    @Override
    @PostMapping(path =  "")
    public Header<EventPlaceApiResponse> create(@RequestBody Header<EventPlaceApiRequest> request) {
        return eventPlaceApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<EventPlaceApiResponse> read(@PathVariable(name = "id") Long id) {
        return eventPlaceApiLogicService.read(id);
    }

    @Override
    @PatchMapping(path = "update")
    public Header<EventPlaceApiResponse> update(@RequestBody Header<EventPlaceApiRequest> request) {
        return eventPlaceApiLogicService.update(request);
    }

    @Override
    @DeleteMapping(path = "delete/{id}")
    public Header<EventPlaceApiResponse> delete(@PathVariable(name = "id") Long id) {
        return eventPlaceApiLogicService.delete(id);
    }
}
