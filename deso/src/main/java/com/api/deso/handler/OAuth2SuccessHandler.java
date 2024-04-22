package com.api.deso.handler;

import com.api.deso.config.mapper.UserDto;
import com.api.deso.config.mapper.UserRequestMapper;
import com.api.deso.model.entity.User;
import com.api.deso.repository.UserRepository;
import com.api.deso.handler.service.Token;
import com.api.deso.handler.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);
        Boolean check = true;

        User user = userRepository.findByEmail(userDto.getEmail());
        if(ObjectUtils.isEmpty(user)) {
            check = false; // 가입 여부 체크
        }

        Token token = tokenService.generateToken(userDto.getEmail(), "USER");

        writeTokenResponse(response, token, check);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token, Boolean check) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.setHeader("Join", String.valueOf(check));
        response.addHeader("Bearer ", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
