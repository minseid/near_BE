package com.api.deso.controller.api;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.handler.service.EventApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventApiRequest;
import com.api.deso.model.network.response.EventApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/event")
public class EventController implements CrudInterface<EventApiRequest, EventApiResponse> {

    @Autowired
    private EventApiLogicService eventApiLogicService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<EventApiResponse> create(@RequestBody Header<EventApiRequest> request) {
        return null;
    }

    @PostMapping(path = "", headers = "Authorization")
    public Header<EventApiResponse> safeCreate(@RequestBody Header<EventApiRequest> request,
                                                   @RequestHeader("Authorization") String jwtHeader) {

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }
        
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();

        if(request.getData() == null) {
            return Header.ERROR("Can't find data");
        }

        if (email != null) {
            User user = userRepository.findByEmail(email);
            EventApiRequest newData = request.getData();
            newData.setUserId(user.getId());
            request.setData(newData);
            log.info("create gathering: {}", request);
            return eventApiLogicService.create(request);
        } else return Header.ERROR("Wrong User Info");
    }

    @Override
    @GetMapping("{id}")
    public Header<EventApiResponse> read(@PathVariable(name = "id") Long id) {
        log.info("read gathering: {}", id);
        return eventApiLogicService.read(id);
    }

    @Override
    public Header<EventApiResponse> update(Header<EventApiRequest> request) {
        return null;
    }

    @PatchMapping(path = "update", headers = "Authorization")
    public Header<EventApiResponse> safeUpdate(@RequestBody Header<EventApiRequest> request,
                                                   @RequestHeader("Authorization") String jwtHeader) {
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();
        if (email != null) {
            User user = userRepository.findByEmail(email);
            EventApiRequest newData = request.getData();
            newData.setUserId(user.getId());
            request.setData(newData);
            return eventApiLogicService.update(request);
        } else return Header.ERROR("Wrong User Info");
    }

    @Override
    @DeleteMapping(path = "delete/{id}")
    public Header<EventApiResponse> delete(@PathVariable(name = "id") Long id) {
        return eventApiLogicService.delete(id);
    }

}
