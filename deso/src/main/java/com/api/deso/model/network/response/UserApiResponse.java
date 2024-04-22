package com.api.deso.model.network.response;

import com.api.deso.model.entity.Blacklist;
import com.api.deso.model.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiResponse {
    private Long id;

    private String email;

    private String phoneNumber;

    private String nickname;

    private String profileImage;

    private String description;

    private String profileOpen;

    private String role;

    private Long level;

    private String status;

    private Boolean adPush;

    private Boolean adKakao;

    private LocalDateTime createdAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime updatedAt;

    private Long warning;

    private List<Friend> friendList;

    private  List<Blacklist> blacklistList;

    private List<AlimApiResponse> alimList;

    private List<BookMarkApiResponse> bookMarkList;

    private List<UserApiResponse> User_logList;
    private List<PurchaseLogApiReaponse> purchase_logList;
}
