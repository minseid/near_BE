package com.api.deso.controller.api;

import com.api.deso.handler.service.BannerApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Banner;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.BannerApiRequest;
import com.api.deso.model.network.request.BlacklistApiRequest;
import com.api.deso.model.network.response.BannerApiResponse;
import com.api.deso.model.network.response.BlacklistApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/banner")
public class BannerController implements CrudInterface<BannerApiRequest, BannerApiResponse> {

    @Autowired
    BannerApiLogicService bannerApiLogicService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<BannerApiResponse> create(@RequestBody Header<BannerApiRequest> request){
        return null;
    }

    @PostMapping(path = "", headers = "Authorization")
    public Header<BannerApiResponse> safeCreate(@RequestBody Header<BannerApiRequest> request,
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
                return bannerApiLogicService.create(request);
        }
        return Header.ERROR("Permission dined");
    }

    @Override
    @GetMapping("{id}")
    public Header<BannerApiResponse> read(@PathVariable Long id) {
        return bannerApiLogicService.read(id);
    }

    @Override
    public Header<BannerApiResponse> update(Header<BannerApiRequest> request) {
        return null;
    }

    @GetMapping("/all")
    public List<Banner> all() {
        return bannerApiLogicService.all();
    }

    @Override
    public Header<BannerApiResponse> delete(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping(path = "delete/{id}", headers = "Authorization")
    public Header<BannerApiResponse> safeDelete(@PathVariable Long id,
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
                return bannerApiLogicService.delete(id);
        }
        return Header.ERROR("Permission dined");
    }
}
