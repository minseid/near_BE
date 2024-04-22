package com.api.deso.controller.api;

import com.api.deso.handler.service.NoticeApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Banner;
import com.api.deso.model.entity.Notice;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.NoticeApiRequest;
import com.api.deso.model.network.response.NoticeApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notice")
public class NoticeController implements CrudInterface<NoticeApiRequest, NoticeApiResponse> {

    @Autowired
    NoticeApiLogicService noticeApiLogicService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<NoticeApiResponse> create(@RequestBody Header<NoticeApiRequest> request) {
        return null;
    }

    @PostMapping(path = "", headers = "Authorization")
    public Header<NoticeApiResponse> safeCreate(@RequestBody Header<NoticeApiRequest> request,
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
            if(user.getLevel()==3)
                return noticeApiLogicService.create(request);
        }
        return Header.ERROR("Permission dined");
    }

    @Override
    @GetMapping("{id}")
    public Header<NoticeApiResponse> read(@PathVariable Long id) {
        return noticeApiLogicService.read(id);
    }

    @Override
    public Header<NoticeApiResponse> update(Header<NoticeApiRequest> request) {
        return null;
    }

    @GetMapping("/all")
    public List<Notice> all() {
        return noticeApiLogicService.all();
    }

    @Override
    public Header<NoticeApiResponse> delete(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping(path = "delete/{id}", headers = "Authorization")
    public Header<NoticeApiResponse> safeDelete(@PathVariable Long id,
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
            if(user.getLevel()==3)
                return noticeApiLogicService.delete(id);
        }
        return Header.ERROR("Permission dined");
    }
}
