package com.api.deso.controller.api;


import com.api.deso.dto.UserDtoClass;
import com.api.deso.handler.service.BlacklistService;
import com.api.deso.handler.service.FriendApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Blacklist;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.BlacklistApiRequest;
import com.api.deso.model.network.request.FriendApiRequest;
import com.api.deso.model.network.response.BlacklistApiResponse;
import com.api.deso.model.network.response.FriendApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/blacklist")
public class BlacklistController implements CrudInterface<BlacklistApiRequest, BlacklistApiResponse> {

    @Autowired
    private BlacklistService blacklistService;

    @Override
    @PostMapping("")
    public Header<BlacklistApiResponse> create(@RequestBody Header<BlacklistApiRequest> request) {
        log.info("create blacklist: {}", request);
        return blacklistService.create(request);
    }

    @Override
    public Header<BlacklistApiResponse> read(@PathVariable(name = "id") Long id) {
        log.info("read id: {}", id);
        return blacklistService.read(id);
    }

    @GetMapping("{id}")
    public List<UserDtoClass.blacklistDto> user_read(@PathVariable(name = "id") Long id) {
        log.info("read id: {}", id);
        return blacklistService.user_read(id);
    }

    @Override
    public Header<BlacklistApiResponse> update(@RequestBody Header<BlacklistApiRequest> request) {
        return null;
    }

    @Override
    public Header<BlacklistApiResponse> delete(@PathVariable Long id) {
        return blacklistService.delete(id);
    }

    @DeleteMapping("delete")
    public Header<BlacklistApiResponse> body_delete(@RequestBody Header<BlacklistApiRequest> request) {
        return blacklistService.body_delete(request);
    }

}
