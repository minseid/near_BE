package com.api.deso.controller.api;

import com.api.deso.handler.service.AlimApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Alim;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.AlimApiRequest;
import com.api.deso.model.network.response.AlimApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/alim")
@RequiredArgsConstructor
public class AlimController implements CrudInterface<AlimApiRequest, AlimApiResponse> {

    private final AlimApiLogicService alimApiLogicService;

    @Override
    @PostMapping("")
    public Header<AlimApiResponse> create(@RequestBody Header<AlimApiRequest> request) {
        return alimApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<AlimApiResponse> read(@PathVariable Long id) {
        return alimApiLogicService.read(id);
    }

    @GetMapping("/send/{id}")
    public List<AlimApiResponse> sendLog(@PathVariable Long id) {
        return alimApiLogicService.readLog(id,"from");
    }
    @GetMapping("/receive/{id}")
    public List<AlimApiResponse> receiveLog (@PathVariable Long id) {
        return alimApiLogicService.readLog(id,"to");
    }

    @Override
    public Header<AlimApiResponse> update(Header<AlimApiRequest> request) {
        return null;
    }

    @PatchMapping("/update")
    public Header<AlimApiResponse> updateShow(@RequestBody Header<AlimApiRequest> request) {
        return alimApiLogicService.updateShow(request);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public Header<AlimApiResponse> delete(@PathVariable Long id) {
        return alimApiLogicService.delete(id);
    }
}
