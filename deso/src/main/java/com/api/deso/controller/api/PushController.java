package com.api.deso.controller.api;

import com.api.deso.dto.UserDtoClass;
import com.api.deso.handler.service.PushService;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/push")
public class PushController {

    private final PushService pushService;

    public PushController(PushService pushService) {
        this.pushService = pushService;
    }

    @PostMapping("/send")
    public @ResponseBody
    String memberPhoneSend(@RequestBody UserDtoClass.pushDto info) throws CoolsmsException {

        if(info == null) return "NULL Data";

        pushService.push(info.getToken(), info.getTitle(), info.getMessage(), info.getLink(), info.getDevice());

        return "Success";
    }
}
