package com.api.deso.controller.api;

import com.api.deso.handler.service.AppleLoginUtil;
import com.api.deso.handler.service.OauthTokenService;
import com.api.deso.model.entity.KakaoProfile;
import com.api.deso.model.entity.OauthToken;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.UserApiRequest;
import com.api.deso.model.network.response.AppleResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
//import sun.security.ec.ECPrivateKeyImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {

    @Autowired
    private OauthTokenService oAuthService;

    @Autowired
    private AppleLoginUtil appleLoginUtil;

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static final String TEAM_ID = "HL4W5PJA88";
    public static final String REDIRECT_URL = "https://main.dvp8yr6clgch.amplifyapp.com/?apple";
    public static final String CLIENT_ID = "deso";
    public static final String KEY_ID = "S2QY5RNJM6";
    public static final String AUTH_URL = "https://appleid.apple.com";
    public static final String KEY_PATH = "static/AuthKey_S2QY5RNJM6.p8";

    @Value(KEY_PATH)
    private String APPLE_KEY;

    @GetMapping("/kakaologin")
    public User view(@RequestParam("code") String code) {

        OauthToken oauthToken = oAuthService.getAccessToken(code);

        User user = oAuthService.saveUser(oauthToken.getAccess_token());

        return user;

    }

    @RequestMapping(value = "/getAppleAuthUrl")
    public @ResponseBody String getAppleAuthUrl(HttpServletRequest request) throws Exception {

        String reqUrl = AUTH_URL + "/auth/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL
                + "&response_type=code id_token&response_mode=form_post";

        return reqUrl;
    }

    @RequestMapping(value = "/oauth_apple")
    public Header<AppleResponse> oauth_apple(HttpServletRequest request, @RequestParam(value = "code") String code, HttpServletResponse response) throws Exception {

        String client_id = CLIENT_ID;
        String client_secret = appleLoginUtil.createClientSecret(TEAM_ID, CLIENT_ID, KEY_ID, APPLE_KEY, AUTH_URL);

        String reqUrl = AUTH_URL + "/auth/token";

        Map<String, String> tokenRequest = new HashMap<>();

        tokenRequest.put("client_id", client_id);
        tokenRequest.put("client_secret", client_secret);
        tokenRequest.put("code", code);
        tokenRequest.put("grant_type", "authorization_code");

        String apiResponse = appleLoginUtil.doPost(reqUrl, tokenRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject tokenResponse = objectMapper.readValue(apiResponse, JSONObject.class);

        // 애플 정보조회 성공
        if (tokenResponse.get("error") == null ) {

            JSONObject payload = appleLoginUtil.decodeFromIdToken((String) tokenResponse.get("id_token"));
            //  회원 고유 식별자
            String appleUniqueNo = (String) payload.get("sub");

           User user = userRepository.findByAuthApple(appleUniqueNo);
           if(user == null) {

               User user1 = User.builder()
                       .email(appleUniqueNo)
                       .password(bCryptPasswordEncoder.encode("apple"))
                       .authApple(appleUniqueNo)
                       .nickname("nearby user")
                       .role("ROLE_USER")
                       .status("REGISTERED")
                       .createdAt(LocalDateTime.now())

                       .build();

               User newUser = userRepository.save(user1);

               AppleResponse appleResponse = AppleResponse.builder()
                       .type("NEW")
                       .code(appleUniqueNo)
                       .userId(newUser.getId())
                       .build();

               return Header.OK(appleResponse);
           }
           else {


               AppleResponse appleResponse = AppleResponse.builder()
                       .type("LOGIN")
                       .code(appleUniqueNo)
                       .userId(user.getId())
                       .build();
               return Header.OK(appleResponse);
           }
        } else {
            return  Header.ERROR("애플 정보조회 실패");
        }
    }


}
