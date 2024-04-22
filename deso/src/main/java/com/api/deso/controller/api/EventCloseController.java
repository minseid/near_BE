package com.api.deso.controller.api;

import com.api.deso.handler.service.EventCloseApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventCloseApiRequest;
import com.api.deso.model.network.response.EventCloseApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/event/close")
public class EventCloseController implements CrudInterface<EventCloseApiRequest, EventCloseApiResponse> {

    @Autowired
    private EventCloseApiLogicService eventCloseApiLogicService;

    @Override
    @PostMapping(path =  "")
    public Header<EventCloseApiResponse> create(@RequestBody Header<EventCloseApiRequest> request) {
        return eventCloseApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<EventCloseApiResponse> read(@PathVariable(name = "id") Long id) {
        return eventCloseApiLogicService.read(id);
    }

    @Override
    @PatchMapping(path = "update")
    public Header<EventCloseApiResponse> update(@RequestBody Header<EventCloseApiRequest> request) {
        return eventCloseApiLogicService.update(request);
    }

    @Override
    @DeleteMapping(path = "delete/{id}")
    public Header<EventCloseApiResponse> delete(@PathVariable(name = "id") Long id) {
        return eventCloseApiLogicService.delete(id);
    }
}
