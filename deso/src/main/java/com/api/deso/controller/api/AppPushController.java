package com.api.deso.controller.api;

import com.api.deso.handler.service.FCMService;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.AppPushApiRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/apppush")
@RequiredArgsConstructor
public class AppPushController {

    private final FCMService fcmService;

    @PostMapping("")
    public Header send(@RequestBody Header<AppPushApiRequest> request) throws IOException {
        log.info("app push create");

        AppPushApiRequest appPushApiRequest = request.getData();

        return fcmService.sendMessageTo(appPushApiRequest.getToken(), appPushApiRequest.getTitle(), appPushApiRequest.getBody(), appPushApiRequest.getType());
    }

}
