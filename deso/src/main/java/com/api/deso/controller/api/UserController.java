package com.api.deso.controller.api;

import com.api.deso.handler.service.UserApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.UserApiRequest;
import com.api.deso.model.network.response.UserApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class UserController implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @PostMapping("") // api/v1/account/
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request) {
        log.info("create user: {}", request);
        return userApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // api/v1/account/{id}
    public Header<UserApiResponse> read(@PathVariable(name = "id") Long id) {
        log.info("read user: {}", id);
        return userApiLogicService.read(id);
    }

    @Override
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {
        return null;
    }

    @PatchMapping(path = "update", headers = "Authorization") // api/v1/account/update
    public Header<UserApiResponse> safeUpdate(@RequestBody Header<UserApiRequest> request,
                                              @RequestHeader("Authorization") String jwtHeader) {
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }

        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();

        if (email == null) {
            return Header.ERROR("Wrong User Info");
        } else {
            User user = userRepository.findByEmail(email);
            if(user.getId() == request.getData().getId() || user.getLevel()==3){
                log.info("update user: {}", request);//본인 || level==3
                return userApiLogicService.update(request);
            }
        }
        return Header.ERROR("Permission dined");
    }

    @PatchMapping("password/update") // api/v1/account/update
    public Header<UserApiResponse> passwordUpdate(@RequestBody Header<UserApiRequest> request) {
        return userApiLogicService.passwordUpdate(request);
    }
    @Override
    @DeleteMapping("delete/{id}") // api/v1/account/delete/{id}
    public Header delete(@PathVariable Long id) {
        return userApiLogicService.delete(id);
    }

    @DeleteMapping("unregister/{id}") // api/v1/account/unregister/{id}
    public Header unregister(@PathVariable Long id) {
        return userApiLogicService.unregister(id);
    }

    @GetMapping(path="my", headers = "Authorization")
    public String my(@RequestHeader("Authorization") String jwtHeader) {
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return "Can't find token";
        }
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String id = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("id").asString();
        return id;
    }
}
