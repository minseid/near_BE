package com.api.deso.controller.api;

import com.api.deso.handler.service.ReportApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Report;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.ReportApiRequest;
import com.api.deso.model.network.response.ReportApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/report")
public class ReportController implements CrudInterface<ReportApiRequest, ReportApiResponse> {

    @Autowired
    ReportApiLogicService reportApiLogicService;

    @Autowired
    UserRepository userRepository;

    @Override
    public Header<ReportApiResponse> create(Header<ReportApiRequest> request) {
        return null;
    }

    @PostMapping(path = "", headers = "Authorization")
    public Header<ReportApiResponse> safeCreate(@RequestBody Header<ReportApiRequest> request,
                                                   @RequestHeader("Authorization") String jwtHeader) {

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();
        if (email != null) {
            User user = userRepository.findByEmail(email);
            ReportApiRequest newData = request.getData();
            newData.setUserId(user.getId());
            request.setData(newData);
            log.info("create gathering: {}", request);
            return reportApiLogicService.create(request);
        } else return Header.ERROR("Wrong User Info");
    }

    @Override
    @GetMapping("{id}")
    public Header<ReportApiResponse> read(@PathVariable(name = "id") Long id) {
        return reportApiLogicService.read(id);
    }

    @Override
    public Header<ReportApiResponse> update(Header<ReportApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping(path = "delete/{id}")
    public Header<ReportApiResponse> delete(@PathVariable(name = "id") Long id) {

        return reportApiLogicService.delete(id);
    }

    @GetMapping("all")
    public List<Report> all() {
        return reportApiLogicService.all();
    }
}
