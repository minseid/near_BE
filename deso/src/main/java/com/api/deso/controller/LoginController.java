package com.api.deso.controller;

import com.api.deso.config.auth.PrincipalDetails;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home() {
        return "<h1>Home</h1>";
    }

//    @PostMapping("join")
//    public String join(@RequestBody User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRole("ROLE_USER");
//        user.setCreatedAt(LocalDateTime.now());
//        user.setCreatedBy("SELF");
//        userRepository.save(user);
//        return "회원가입완료";
//    }

    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return "user";
    }

    @GetMapping("header")
    public Header getHeader() {
        return Header.builder().resultCode("OK").description("OK").build();
    }

    @GetMapping("admin")
    public String admin() {
        return "admin";
    }
}
