package com.api.deso.config.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String email;

    @Builder
    public UserDto(String email) {
        this.email = email;
    }
}