package com.api.deso.controller.api;

import com.api.deso.handler.service.UserApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.UserApiRequest;
import com.api.deso.model.network.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/userLog")
public class UserLogController implements CrudInterface<UserApiRequest, UserApiResponse> {
    @Autowired
    private UserApiLogicService userApiLogicService;
    @Override
    @PostMapping("")
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        return userApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<UserApiResponse> read(@PathVariable  Long id) {
        return userApiLogicService.read(id);
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        return userApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public Header<UserApiResponse> delete(Long id) {
        return userApiLogicService.delete(id);
    }
}
