package com.api.deso.controller.api;

import com.api.deso.handler.service.BannerApiLogicService;
import com.api.deso.handler.service.RecommendApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Banner;
import com.api.deso.model.entity.Recommend;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.BannerApiRequest;
import com.api.deso.model.network.request.RecommendApiRequest;
import com.api.deso.model.network.response.BannerApiResponse;
import com.api.deso.model.network.response.RecommendApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/recommend")
public class RecommendController implements CrudInterface<RecommendApiRequest, RecommendApiResponse> {

    @Autowired
    RecommendApiLogicService recommendApiLogicService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<RecommendApiResponse> create(@RequestBody Header<RecommendApiRequest> request) {
        return null;
    }

    @PostMapping(path = "", headers = "Authorization")
    public Header<RecommendApiResponse> safeCreate(@RequestBody Header<RecommendApiRequest> request,
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
                return recommendApiLogicService.create(request);
        }
        return Header.ERROR("Permission dined");
    }

    @Override
    @GetMapping("{id}")
    public Header<RecommendApiResponse> read(@PathVariable Long id) {
        return recommendApiLogicService.read(id);
    }

    @Override
    @PatchMapping("update")
    public Header<RecommendApiResponse> update(Header<RecommendApiRequest> request) {

        return recommendApiLogicService.update(request);

    }

    @GetMapping("/all")
    public List<RecommendApiResponse> all() {

        return recommendApiLogicService.all();
    }

    @Override
    public Header<RecommendApiResponse> delete(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping(path = "delete/{id}", headers = "Autorization")
    public Header<RecommendApiResponse> safeDelete(@PathVariable Long id,
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
                return recommendApiLogicService.delete(id);
        }
        return Header.ERROR("Permission dined");
    }
}
