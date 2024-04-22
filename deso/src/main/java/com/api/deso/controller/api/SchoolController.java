package com.api.deso.controller.api;

import com.api.deso.dto.UserDtoClass;
import com.api.deso.handler.service.EmailService;
import com.api.deso.model.entity.Auth;
import com.api.deso.model.network.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/school")
public class SchoolController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public Header memberPhoneSend(@RequestBody Header<Auth> auth) throws Exception {

        if(auth == null) return Header.ERROR("NULL");

        emailService.sendEmailMessage(auth.getData().getCheckStr());

        return Header.OK();
    }

    @GetMapping("/check/{path}")
    public String memberPhoneCheck(@PathVariable(name = "path") String path) throws Exception {


        return emailService.verifyEmailMessage(path);

    }

}
