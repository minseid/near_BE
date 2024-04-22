package com.api.deso.controller.api;

import com.api.deso.handler.service.ThemeEventApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.ThemeEventApiRequest;
import com.api.deso.model.network.response.ThemeEventApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/themeevent")
public class ThemeEventController implements CrudInterface<ThemeEventApiRequest, ThemeEventApiResponse> {

    @Autowired
    private ThemeEventApiLogicService themeEventApiLogicService;

    @Override
    @PostMapping("")
    public Header<ThemeEventApiResponse> create(@RequestBody Header<ThemeEventApiRequest> request) {
        return themeEventApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<ThemeEventApiResponse> read(@PathVariable Long id) {
        return themeEventApiLogicService.read(id);
    }

    @Override
    public Header<ThemeEventApiResponse> update(@RequestBody Header<ThemeEventApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("delete/{id}")
    public Header<ThemeEventApiResponse> delete(@PathVariable Long id) {
        return themeEventApiLogicService.delete(id);
    }
}
