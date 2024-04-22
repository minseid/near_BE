package com.api.deso.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiRequest {
    private Long id;

    private String email;

    private String password;

    private String phoneNumber;

    private String nickname;

    private String profileImage;

    private String description;

    private String profileOpen;

    private String role;

    private Long level;

    private String status;

    private String authKakao;

    private String authApple;

    private Boolean adPush;

    private Boolean adKakao;

}
