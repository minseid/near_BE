package com.api.deso.controller.api;

import com.api.deso.handler.service.ThemeApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Theme;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.ThemeApiRequest;
import com.api.deso.model.network.response.ThemeApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/theme")
public class ThemeController implements CrudInterface<ThemeApiRequest, ThemeApiResponse> {

    @Autowired
    private ThemeApiLogicService themeApiLogicService;

    @Override
    @PostMapping("")
    public Header<ThemeApiResponse> create(@RequestBody Header<ThemeApiRequest> request) {
        return themeApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<ThemeApiResponse> read(@PathVariable Long id) {
        return themeApiLogicService.read(id);
    }

    @Override
    @PatchMapping("update")
    public Header<ThemeApiResponse> update(@RequestBody Header<ThemeApiRequest> request) {
        System.out.println(request.toString());
        return themeApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public Header<ThemeApiResponse> delete(@PathVariable Long id) {
        return themeApiLogicService.delete(id);
    }
}
