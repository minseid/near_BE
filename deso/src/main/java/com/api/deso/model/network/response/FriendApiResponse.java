package com.api.deso.model.network.response;

import com.api.deso.dto.UserDtoClass;
import com.api.deso.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendApiResponse {

    private Long id;

    private Long userId2;

    private LocalDateTime createdAt;

    private String status;

    //Friend N : 1 User
    @ManyToOne
    private UserDtoClass.SimpleInfo user;
}
