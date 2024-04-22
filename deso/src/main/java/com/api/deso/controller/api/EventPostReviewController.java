package com.api.deso.controller.api;

import com.api.deso.handler.service.EventPostReviewApiLogicService;
import com.api.deso.handler.service.EventReviewApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventPostReviewApiRequest;
import com.api.deso.model.network.request.EventReviewApiRequest;
import com.api.deso.model.network.response.EventPostReviewApiResponse;
import com.api.deso.model.network.response.EventReviewApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/event/post-review")
public class EventPostReviewController implements CrudInterface<EventPostReviewApiRequest, EventPostReviewApiResponse> {

    @Autowired
    private EventPostReviewApiLogicService eventPostReviewApiLogicService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<EventPostReviewApiResponse> create(Header<EventPostReviewApiRequest> request) {
        return null;
    }

    @PostMapping(path = "", headers = "Authorization")
    public Header<EventPostReviewApiResponse> safeCreate(@RequestBody Header<EventPostReviewApiRequest> request,
                                                          @RequestHeader("Authorization") String jwtHeader) {

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();

        if (email != null) {
            User user = userRepository.findByEmail(email);
            EventPostReviewApiRequest newData = request.getData();
            System.out.println(user.getId());
            newData.setUserId(user.getId());
            request.setData(newData);
            log.info("create gathering comment: {}", request);
            return eventPostReviewApiLogicService.create(request);
        } else return Header.ERROR("Wrong User Info");
    }

    @Override
    @GetMapping("{id}")
    public Header<EventPostReviewApiResponse> read(@PathVariable(name = "id") Long id) {
        return  eventPostReviewApiLogicService.read(id);
    }

    @Override
    public Header<EventPostReviewApiResponse> update(Header<EventPostReviewApiRequest> request) {
        return null;
    }

    @PatchMapping(path = "update", headers = "Authorization")
    public Header<EventPostReviewApiResponse> safeUpdate(@RequestBody Header<EventPostReviewApiRequest> request,
                                                          @RequestHeader("Authorization") String jwtHeader) {

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();
        if (email != null) {
            User user = userRepository.findByEmail(email);
            EventPostReviewApiRequest newData = request.getData();
            newData.setUserId(user.getId());
            request.setData(newData);
            log.info("create gathering: {}", request);
            return eventPostReviewApiLogicService.update(request);
        } else return Header.ERROR("Wrong User Info");
    }

    @Override
    @DeleteMapping(path = "delete/{id}")
    public Header<EventPostReviewApiResponse> delete(@PathVariable(name = "id") Long id) {
        return eventPostReviewApiLogicService.delete(id);
    }
}
