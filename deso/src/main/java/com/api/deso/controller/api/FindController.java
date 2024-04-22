package com.api.deso.controller.api;

import com.api.deso.dto.UserDtoClass;
import com.api.deso.handler.service.PhoneAuthService;
import com.api.deso.model.entity.Auth;
import com.api.deso.model.network.Header;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/find")
public class FindController {

    @Autowired
    private PhoneAuthService phoneAuthService;

    @PostMapping("/send")
    public @ResponseBody String memberPhoneSend(@RequestBody UserDtoClass.phoneType auth) throws CoolsmsException {

        if(auth == null) return "NULL Data";

        return phoneAuthService.PhoneNumberInput(auth.getType(), auth.getPhone());
    }

    @PostMapping("/check")
    public @ResponseBody String memberPhoneCheck(@RequestBody Auth auth) {

        if(auth == null) return "NULL Data";

        return phoneAuthService.PhoneCheck(auth.getType(), auth.getPhone(), auth.getCheckStr());
    }

//    @PostMapping("/phone")
//    public @ResponseBody String memberPhoneSend(@RequestBody Auth auth, @RequestParam(value="to") String to) throws CoolsmsException {
//
//        return phoneAuthService.PhoneNumberInput(to);
//    }

}
