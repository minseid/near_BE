package com.api.deso.controller.api;

import com.api.deso.handler.service.EventOpenApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventOpenApiRequest;
import com.api.deso.model.network.response.EventOpenApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/event/open")
public class EventOpenController implements CrudInterface<EventOpenApiRequest, EventOpenApiResponse> {

    @Autowired
    private EventOpenApiLogicService eventOpenApiLogicService;

    @Override
    @PostMapping(path =  "")
    public Header<EventOpenApiResponse> create(@RequestBody Header<EventOpenApiRequest> request) {
        return eventOpenApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<EventOpenApiResponse> read(@PathVariable(name = "id") Long id) {
        return eventOpenApiLogicService.read(id);
    }

    @Override
    @PatchMapping(path = "update")
    public Header<EventOpenApiResponse> update(@RequestBody Header<EventOpenApiRequest> request) {
        return eventOpenApiLogicService.update(request);
    }

    @Override
    @DeleteMapping(path = "delete/{id}")
    public Header<EventOpenApiResponse> delete(@PathVariable(name = "id") Long id) {
        return eventOpenApiLogicService.delete(id);
    }
}
