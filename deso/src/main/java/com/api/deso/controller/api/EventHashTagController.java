package com.api.deso.controller.api;

import com.api.deso.handler.service.EventHashTagLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.HashtagApiRequest;
import com.api.deso.model.network.response.HashtagApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/event/hashtag")
public class EventHashTagController implements CrudInterface<HashtagApiRequest, HashtagApiResponse> {

    @Autowired
    private EventHashTagLogicService eventHashTagLogicService;

    @Override
    @PostMapping(path = "")
    public Header<HashtagApiResponse> create(@RequestBody Header<HashtagApiRequest> request){

            return eventHashTagLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<HashtagApiResponse> read(@PathVariable(name = "id") Long id) {
        return  eventHashTagLogicService.read(id);
    }
    @GetMapping("/all")
    public Header<List<HashtagApiResponse>> readAll() {
        return  eventHashTagLogicService.readAll();
    }

    @Override
    @PatchMapping(path = "update")
    public Header<HashtagApiResponse> update(@RequestBody Header<HashtagApiRequest> request) {
        return eventHashTagLogicService.update(request);
    }

    @Override
    @DeleteMapping(path = "delete/{id}")
    public Header<HashtagApiResponse> delete(@PathVariable(name = "id") Long id) {
        return eventHashTagLogicService.delete(id);
    }
}
