package com.api.deso.controller.api;

import com.api.deso.handler.service.EventReviewApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventReviewApiRequest;
import com.api.deso.model.network.response.EventReviewApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/event/review")
public class EventReviewController implements CrudInterface<EventReviewApiRequest, EventReviewApiResponse> {

    @Autowired
    private EventReviewApiLogicService eventReviewApiLogicService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<EventReviewApiResponse> create(Header<EventReviewApiRequest> request) {
        return null;
    }

    @PostMapping(path = "", headers = "Authorization")
    public Header<EventReviewApiResponse> safeCreate(@RequestBody Header<EventReviewApiRequest> request,
                                                          @RequestHeader("Authorization") String jwtHeader) {

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();

        if (email != null) {
            User user = userRepository.findByEmail(email);
            EventReviewApiRequest newData = request.getData();
            System.out.println(user.getId());
            newData.setUserId(user.getId());
            request.setData(newData);
            log.info("create gathering comment: {}", request);
            return eventReviewApiLogicService.create(request);
        } else return Header.ERROR("Wrong User Info");
    }

    @Override
    @GetMapping("{id}")
    public Header<EventReviewApiResponse> read(@PathVariable(name = "id") Long id) {
        return  eventReviewApiLogicService.read(id);
    }

    @Override
    public Header<EventReviewApiResponse> update(Header<EventReviewApiRequest> request) {
        return null;
    }

    @PatchMapping(path = "update", headers = "Authorization")
    public Header<EventReviewApiResponse> safeUpdate(@RequestBody Header<EventReviewApiRequest> request,
                                                          @RequestHeader("Authorization") String jwtHeader) {

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();
        if (email != null) {
            User user = userRepository.findByEmail(email);
            EventReviewApiRequest newData = request.getData();
            newData.setUserId(user.getId());
            request.setData(newData);
            log.info("create gathering: {}", request);
            return eventReviewApiLogicService.update(request);
        } else return Header.ERROR("Wrong User Info");
    }

    @Override
    @DeleteMapping(path = "delete/{id}")
    public Header<EventReviewApiResponse> delete(@PathVariable(name = "id") Long id) {
        return eventReviewApiLogicService.delete(id);
    }
}
