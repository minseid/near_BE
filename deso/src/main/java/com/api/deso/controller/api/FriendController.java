package com.api.deso.controller.api;

import com.api.deso.dto.UserDtoClass;
import com.api.deso.handler.service.FriendApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Friend;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.FriendApiRequest;
import com.api.deso.model.network.request.UserApiRequest;
import com.api.deso.model.network.response.FriendApiResponse;
import com.api.deso.model.network.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/friend")
public class FriendController implements CrudInterface<FriendApiRequest, FriendApiResponse> {

    @Autowired
    private FriendApiLogicService friendApiLogicService;

    @Override
    @PostMapping("") // api/v1/friend/
    public Header<FriendApiResponse> create(@RequestBody Header<FriendApiRequest> request) {
        log.info("create friend: {}", request);
        return friendApiLogicService.create(request);
    }

    @Override
    public Header<FriendApiResponse> read(@PathVariable(name = "id") Long id) {
        log.info("read id: {}", id);
        return friendApiLogicService.read(id);
    }

    @GetMapping("{id}") // api/v1/friend/{id}
    public List<UserDtoClass.friendDto> user_read(@PathVariable(name = "id") Long id) {
        log.info("read id: {}", id);
        return friendApiLogicService.user_read(id);
    }

    @Override
    public Header<FriendApiResponse> update(@RequestBody Header<FriendApiRequest> request) {
        return friendApiLogicService.update(request);
    }

    @PatchMapping("update") // api/v1/friend/update
    public Header<FriendApiResponse> permit(@RequestBody Header<FriendApiRequest> request) {
        return friendApiLogicService.permit(request);
    }

    @Override
    public Header<FriendApiResponse> delete(@PathVariable Long id) {
        return friendApiLogicService.delete(id);
    }

    @DeleteMapping("delete") // api/v1/friend/delete/{id}
    public Header<FriendApiResponse> body_delete(@RequestBody Header<FriendApiRequest> request) {
        return friendApiLogicService.body_delete(request);
    }
}
